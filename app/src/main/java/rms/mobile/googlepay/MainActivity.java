package rms.mobile.googlepay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}