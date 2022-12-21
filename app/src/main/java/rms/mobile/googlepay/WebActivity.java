package rms.mobile.googlepay;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import rms.mobile.googlepay.Helper.RMSGooglePay;

public class WebActivity extends AppCompatActivity {

    void processValue(String result) throws JSONException {
        //handle value
        //Update GUI, show toast, etc..
        Toast.makeText(
                this, getString(rms.mobile.googlepay.R.string.payments_show_name, result),
                Toast.LENGTH_LONG).show();

        Log.d(TAG, String.format("responseBody: %s ", result));

//        JSONObject resultObj = new JSONObject(result);
        JSONObject responseBodyObj = new JSONObject(new JSONObject(result).getString("responseBody"));
        Log.d(TAG, String.format("masso 2: %s ", responseBodyObj));

        String key = "error_code";
        boolean isKeyExists= responseBodyObj.has(key);
        Log.d(TAG, String.format("isKeyExists 2: %s ", isKeyExists));

        //Error
        if (isKeyExists) {
            //Checking address Key Present or not
            Log.e(TAG, String.format("error_code: %s - error_desc: %s ", responseBodyObj.getString("error_code"), responseBodyObj.getString("error_desc")));
        }
        else {
            
            // redirect
            try {
//              String txnData = responseBodyObj.getString("TxnData");
////            JSONObject txnDataObj = new JSONObject(txnData);

                JSONObject txnDataObj = new JSONObject(responseBodyObj.getString("TxnData"));
                String requestURL = txnDataObj.getString("RequestURL");
                String requestData = txnDataObj.getString("RequestData");

                Log.d(TAG, String.format(" Redirect URL: %s ", requestURL));
                Log.d(TAG, String.format(" Request Data requestData: %s ", requestData));


                WebView webview = new WebView(this);
                setContentView(webview);
                //        String requestURL = "https://www.onlinepayment.com.my/RMS/CardScheme/loading.php";
                String url = requestURL;
                String postData = requestData;

                webview.postUrl(url, postData.getBytes());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // res.body = {
            //     MerchantID: 'razerparcelplus_Dev',
            //     ReferenceNo: 'DDA-887fc953-faaf-4371-9142-dcbd3599c6981557197125028a46555',
            //     TxnID: '87430775',
            //     TxnType: 'SALS',
            //     TxnCurrency: 'MYR',
            //     TxnAmount: '1.00',
            //     TxnChannel: 'FPX_B2B_AMB',
            //     TxnData: {
            //         RequestURL: 'https://www.mepsfpx.com.my/FPXMain/seller2DReceiver.jsp',
            //         RequestMethod: 'POST',
            //         RequestType: 'REDIRECT',
            //         RequestData: {
            //             fpx_msgType: 'AD',
            //             fpx_msgToken: '01',
            //             fpx_sellerExId: 'EX00002900',
            //             fpx_sellerExOrderNo: '87430775',
            //             fpx_sellerTxnTime: '20190507104525',
            //             fpx_sellerOrderNo: '87430775',
            //             fpx_sellerId: 'SE00031860',
            //             fpx_sellerBankCode: '01',
            //             fpx_txnCurrency: 'MYR',
            //             fpx_txnAmount: '100000.00',
            //             fpx_buyerEmail: 'testmerchant@parcel.molpay.com',
            //             fpx_checkSum: '4490AE695DCF419BC66F52392A902580740255FCA5550F4083F84AE4FB1212765D182EDBC4F49800867419BDA082C56767F7CE5AD42B7243CC0EB2D531CC98D9468E49B723F065B8EE88915FA6E94191CE608F8D1588C90E7DDE2A640B8A63C181826618BACF05714CFAFE3A67ADBF3D735559275E86EC8BDCF27BB6F0CF4BA38634FCA47118DA927D66FA4518A3C41E6913444C26EDF776F3B500D8DEFA7D3DF8D2149E4A58E6FDCE5469FE02814E04D3CE46C23DC0D210026F1728F530D1CB0B7DADE9A5A23F7D802ECEDE5BE39A0AD2C2C985AC0CEC4A54544E03C72FF0AA23BA7D3A4426AB19241B37C2935AFF5E83AF074A311F573FF40A49D32B7458E7',
            //             fpx_buyerName: '',
            //             fpx_buyerBankId: null,
            //             fpx_buyerBankBranch: '',
            //             fpx_buyerAccNo: '',
            //             fpx_buyerId: 'A012345678,1',
            //             fpx_makerName: '',
            //             fpx_buyerIban: '01,,9,DL,070519,',
            //             fpx_version: '7.0',
            //             fpx_productDesc: 'Direct Debit Authorization'
            //         }
            //     }
            // }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        String paymentInput = intent.getStringExtra("paymentInput");
        String paymentInfo = intent.getStringExtra("paymentInfo");
        Log.d(TAG, String.format("paymentInput: %s - paymentInfo - %s", paymentInput, paymentInfo));

        PaymentTaskRunner runner = new PaymentTaskRunner();
        runner.execute(paymentInput, paymentInfo);

    }

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
                Log.i("What is in here", "String.valueOf(result)");
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