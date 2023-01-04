package rms.mobile.googlepay;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import rms.mobile.googlepay.Helper.RMSGooglePay;
import rms.mobile.googlepay.model.Transaction;

public class WebActivity extends AppCompatActivity {

    private WebView wvGateway;
    private CountDownTimer countDownTimer;

    public Transaction transaction = new Transaction();
    private String _transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        String paymentInput = intent.getStringExtra("paymentInput");
        String paymentInfo = intent.getStringExtra("paymentInfo");
        Log.d(TAG, String.format("paymentInput: %s - paymentInfo - %s", paymentInput, paymentInfo));


        // Transcation model from paymentInput

        JSONObject paymentInputObj = null;
        try {
            paymentInputObj = new JSONObject(paymentInput);
//            transaction.setTxID(paymentInputObj.getString("orderId"));
//            transaction.setDomain(paymentInputObj.getString("merchantId"));
            transaction.setVkey(paymentInputObj.getString("verificationKey"));
//            transaction.setAmount(paymentInputObj.getString("amount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        wvGateway = findViewById(R.id.webView);
        wvGateway.setBackgroundColor(Color.WHITE);
        wvGateway.getSettings().setDomStorageEnabled(true);
        wvGateway.getSettings().setJavaScriptEnabled(true);
        wvGateway.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wvGateway.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvGateway.getSettings().setSupportMultipleWindows(true);


        PaymentThread paymentThread = new PaymentThread();
        paymentThread.runThread(paymentInput, paymentInfo); // set value
        Thread thread = new Thread(paymentThread);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        PaymentTaskRunner runner = new PaymentTaskRunner();
//        runner.execute(paymentInput, paymentInfo);
    }

    private void onStartTimOut() {
        long minTimeOut = 3;
        long interval = 5000;
        final String[] result = {null};
        countDownTimer = new CountDownTimer(minTimeOut, interval) {

            public void onTick(long millisUntilFinished) {
                Log.d(TAG, String.format("onTick: %d", millisUntilFinished / interval));
                QueryResultThread queryResultThread = new QueryResultThread();
                queryResultThread.runThread(_transaction); // set value
                Thread thread = new Thread(queryResultThread);
                thread.start();
                try {
                    thread.join();
                    result[0] = queryResultThread.getValue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            public void onFinish() {
                Log.d(TAG, "onFinish");
//                mMobileSDKResult.onResult(AppData.getInstance().getTxnResult());
                Toast.makeText(getApplicationContext(), result[0],Toast.LENGTH_LONG).show();
                finish();
            }
        };
        countDownTimer.start();
    }

    private void onLoadHtmlWebView(String plainHtml) {
        String encodedHtml = Base64.encodeToString(plainHtml.getBytes(), Base64.NO_PADDING);
        Log.d(TAG, String.format("onLoadHtmlWebView: %s", encodedHtml));

        wvGateway.setVisibility(View.VISIBLE);
        wvGateway.loadData(encodedHtml, "text/html", "base64");
    }

    public void onRequestData(JSONObject response) {
        Log.d(TAG, "onGetPaymentRequestForm onComplete invoked");

        try {
            if (response.has("error_code") && response.has("error_desc")) {
//                mMobileSDKResult.onResult(String.format("{ code:%s, message:%s }", response.getString("error_code"), response.getString("error_desc")));
                finish();
                return;
            }
            if (response.has("TxnID")) {
//                AppData.getInstance().setTxnID(response.getString("TxnID"));
                try {
                    transaction.setTxID(response.getString("TxnID"));
                    transaction.setDomain(response.getString("MerchantID"));
                    transaction.setAmount(response.getString("TxnAmount"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (response.has("TxnData") && !response.has("pInstruction")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onStartTimOut();
                        }
                    });

                    JSONObject txnData = response.getJSONObject("TxnData");
                    Log.d(TAG, "TxnData: " + txnData);
                    StringBuilder html = new StringBuilder();
                    html.append(String.format("<form id='prForm' action='%s' method='%s'>\n",
                            txnData.getString("RequestURL"),
                            txnData.getString("RequestMethod"))
                    );
                    if (txnData.has("AppDeepLinkURL")) {
//                        AppData.getInstance().setRedirectAppUrl(txnData.getString("AppDeepLinkURL"));
                    }
                    if (txnData.has("RequestData")) {

                        if (txnData.get("RequestData") instanceof JSONObject) {
                            JSONObject requestData = txnData.getJSONObject("RequestData");
                            Log.d(TAG, "RequestData: " + requestData);

                            Iterator<String> keys = requestData.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                Log.d(TAG, "Key: " + key);
                                if (requestData.get(key) instanceof JSONObject) {
                                    Log.d(TAG, "param : " + requestData.get(key));
                                } else {
                                    if (requestData.has("checkoutUrl")) {
//                                        AppData.getInstance().setRedirectAppUrl(requestData.getString("checkoutUrl"));
                                    }
                                    html.append(String.format("<input type='hidden' name='%s' value='%s'>\n", key, requestData.getString(key)));
                                }
                            }
                        }
                    }

                    html.append("</form>");
                    html.append("<script> document.getElementById('prForm').submit();</script>");
                    Log.d(TAG, "HTML: " + html);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            onLoadHtmlWebView(html.toString());
                        }
                    });
                } else {
                    Log.d(TAG, "parameter_not_found : ");
                    finish();
                }
            } else {
                Log.d(TAG, "parameter_not_found : ");
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void processValue(String result) throws JSONException {
        //Update GUI, show toast, etc..
        JSONObject responseBodyObj = new JSONObject(new JSONObject(result).getString("responseBody"));
        onRequestData(responseBodyObj);
    }


    public class PaymentThread implements Runnable {
        private volatile String resp;
        private String paymentInput;
        private String  paymentInfo;

        public String getValue() {
            return resp;
        }

        public void runThread(String paymentInput, String  paymentInfo) {
            this.paymentInput = paymentInput;
            this.paymentInfo = paymentInfo;
            run();
        }

        @Override
        public void run() {
            RMSGooglePay pay = new RMSGooglePay();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                JSONObject result = (JSONObject) pay.requestPayment(paymentInput, paymentInfo);
                resp = result.toString();
            }

            // Redirection
            JSONObject responseBodyObj = null;
            try {
                responseBodyObj = new JSONObject(new JSONObject(resp).getString("responseBody"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onRequestData(responseBodyObj);

        }
    }

    public static class QueryResultThread implements Runnable {
        private volatile String resp;
        private String transaction;


        public String getValue() {
            return resp;
        }

        public void runThread(String transaction) {
            this.transaction = transaction;
            run();
        }

        @Override
        public void run() {
            RMSGooglePay pay = new RMSGooglePay();
            JSONObject result = (JSONObject) pay.queryPaymentResult(transaction);
            resp = result.toString();
        }


    }



    //DEPRECATED
    @SuppressLint("StaticFieldLeak")
    private class PaymentTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... params) {
            try {
                RMSGooglePay pay = new RMSGooglePay();
                JSONObject result = (JSONObject) pay.requestPayment(
                        params[0],
                        params[1]
                );
                resp = result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            Log.i("PaymentTaskRunner doInBackground", resp);
            return resp;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("PaymentTaskRunner onPostExecute", result);
            try {
                processValue(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void onPreExecute() {
            Log.i("PaymentTaskRunner onPreExecute", "preExecute");
        }
        @Override
        protected void onProgressUpdate(String... text) {
            Log.e("PaymentTaskRunner onProgressUpdate", "progressUpdate");
        }
    }

    private class QueryResultTaskRunner extends AsyncTask<String, String, String> {
        private String resp;
        @Override
        protected String doInBackground(String... params) {
            try {
                RMSGooglePay pay = new RMSGooglePay();
                JSONObject result = (JSONObject) pay.queryPaymentResult(
                        params[0]
                );

                resp = result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            Log.i("PaymentTaskRunner doInBackground", resp);
            return resp;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("PaymentTaskRunner onPostExecute", result);
            try {
                processValue(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void onPreExecute() {
            Log.i("PaymentTaskRunner onPreExecute", "preExecute");
        }
        @Override
        protected void onProgressUpdate(String... text) {
            Log.e("PaymentTaskRunner onProgressUpdate", "progressUpdate");
        }
    }





}