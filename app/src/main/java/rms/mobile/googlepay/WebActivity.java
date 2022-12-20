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

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import rms.mobile.googlepay.Helper.RMSGooglePay;

public class WebActivity extends AppCompatActivity {

    void processValue(String myValue) {
        //handle value
        //Update GUI, show toast, etc..
        Toast.makeText(
                this, getString(R.string.payments_show_name, myValue),
                Toast.LENGTH_LONG).show();

        WebView webview = new WebView(this);
        setContentView(webview);

        String url = "http://www.example.com";

        String postData = null;
        try {
            postData = "username=" + URLEncoder.encode("my_username", "UTF-8") + "&password=" + URLEncoder.encode("my_password", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        webview.postUrl(url,postData.getBytes());
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

    private static class PaymentTaskRunner extends AsyncTask<String, String, String> {

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
//            processValue(result);
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