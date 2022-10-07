package rms.mobile.googlepay.Helper;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import rms.mobile.googlepay.Service.ApiRequestService;

public class RMSGooglePay {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Object requestPayment(String orderId, String amount, String currency, String billName, String billEmail, String billPhone, String billDesc, String merchantId, String environmentMode, String verificationKey, String gPayToken) {

        try {
            JSONObject assuranceDetails = new JSONObject();
            assuranceDetails.put("cardHolderAuthenticated", "");
            assuranceDetails.put("accountVerified", 1);
            JSONObject info = new JSONObject();
            info.put("cardNetwork", "VISA"); // visa or mastercard
            info.put("cardDetails", 2602); // end of card number
            info.put("assuranceDetails", "PAYMENT_GATEWAY");
            JSONObject tokenizationData = new JSONObject();
            tokenizationData.put("type", "PAYMENT_GATEWAY");
            JSONObject paymentMethodData = new JSONObject();
            paymentMethodData.put("description", 0);
            paymentMethodData.put("tokenizationData", tokenizationData);
            paymentMethodData.put("type", "CARD");
            paymentMethodData.put("info", info);
            JSONObject googlePay = new JSONObject();
            googlePay.put("apiVersionMinor", 0);
            googlePay.put("apiVersion", 2);
            googlePay.put("paymentMethodData", paymentMethodData);
            JSONObject rmsInfo = new JSONObject();
            rmsInfo.put("googlePay", googlePay);
            rmsInfo.put("orderId", orderId);
            rmsInfo.put("amount", amount);
            rmsInfo.put("currency", currency);
            rmsInfo.put("billName", billName);
            rmsInfo.put("billEmail", billEmail);
            rmsInfo.put("billPhone", billPhone);
            rmsInfo.put("billDesc", billDesc);
            rmsInfo.put("merchantId", merchantId);
            rmsInfo.put("verificationKey", verificationKey);
            rmsInfo.put("environmentMode", environmentMode);

            ApiRequestService pay = new ApiRequestService();
            return pay.GetPaymentRequest(gPayToken, rmsInfo);
        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }
}
