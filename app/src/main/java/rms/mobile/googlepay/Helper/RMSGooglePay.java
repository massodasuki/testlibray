package rms.mobile.googlepay.Helper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import rms.mobile.googlepay.Service.ApiRequestService;

public class RMSGooglePay {

    final Pattern ORDERID = Pattern.compile("^[A-Za-z, ]++$");
    final Pattern AMOUNT = Pattern.compile("^[A-Za-z, ]++$");
    final Pattern CURRENCY = Pattern.compile("^[A-Za-z, ]++$");
    final Pattern BILLNAME = Pattern.compile("^[A-Za-z, ]++$");
    final Pattern BILLEMAIL = Pattern.compile("^[A-Za-z, ]++$");
    final Pattern BILLPHONE = Pattern.compile("^[A-Za-z, ]++$");
    final Pattern BILLDESC = Pattern.compile("^[A-Za-z, ]++$");
    final Pattern MERCHANTID = Pattern.compile("^[A-Za-z, ]++$");
    final Pattern VERFICATIONKEY = Pattern.compile("^[A-Za-z, ]++$");
    final Pattern ENV = Pattern.compile("^[A-Za-z, ]++$");


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Object requestPayment(JSONObject paymentInput, String paymentInfo) {

        try {

            //INPUT VALIDATION
            String orderId = paymentInput.getString("orderId");
            String amount = paymentInput.getString("amount");
            String currency = paymentInput.getString("currency");
            String billName = paymentInput.getString("billName");
            String billEmail = paymentInput.getString("billEmail");
            String billPhone = paymentInput.getString("billPhone");
            String billDesc = paymentInput.getString("billDesc");
            String merchantId = paymentInput.getString("merchantId");
            String verificationKey = paymentInput.getString("verificationKey");
            String isSandbox = paymentInput.getString("isSandbox");

            JSONObject error = new JSONObject();
            error.put("error", "400");
            error.put("message", "Invalid Input");
            
            if (!ORDERID.matcher(orderId).matches()) {
                //throw new IllegalArgumentException("Invalid String");
                return error;
            }
            else if (!AMOUNT.matcher(amount).matches()) {
                //throw new IllegalArgumentException("Invalid String");
                return error;
            }
            else if (!CURRENCY.matcher(currency).matches()) {
                //throw new IllegalArgumentException("Invalid String");
                return error;
            }
            else if (!BILLNAME.matcher(billName).matches()) {
                //throw new IllegalArgumentException("Invalid String");
                return error;
            }
            else if (!BILLEMAIL.matcher(billEmail).matches()) {
                //throw new IllegalArgumentException("Invalid String");
                return error;
            }
            else if (!BILLPHONE.matcher(billPhone).matches()) {
                //throw new IllegalArgumentException("Invalid String");
                return error;
            }
            else if (!BILLDESC.matcher(billDesc).matches()) {
                //throw new IllegalArgumentException("Invalid String");
                return error;
            }
            else if (!MERCHANTID.matcher(merchantId).matches()) {
                //throw new IllegalArgumentException("Invalid String");
                return error;
            }
            else if (!VERFICATIONKEY.matcher(verificationKey).matches()) {
                //throw new IllegalArgumentException("Invalid String");
                return error;
            }
            else if (!ENV.matcher(isSandbox).matches()) {
                //throw new IllegalArgumentException("Invalid String");
                return error;
            } else {
                ApiRequestService pay = new ApiRequestService();
                return pay.GetPaymentRequest(paymentInput, paymentInfo);
            }

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }
}
