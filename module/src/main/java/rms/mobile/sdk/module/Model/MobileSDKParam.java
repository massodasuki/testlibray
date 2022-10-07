package rms.mobile.sdk.module.Model;

public class MobileSDKParam {
    // ##### Billing parameter
    private final String orderId;
    private final String amount;
    private final String currency;
    private final String countryCode;
    private final String billName;
    private final String billEmail;
    private final String billPhone;
    private final String billDesc;
    private final String channel;

    // ##### Card parameter
    private String cardNo;
    private String cardMonth;
    private String cardYear;
    private String cardCVV;
    private String cardToken;
    private Status cardTokenStatus;

    // ##### Credential parameter
    private final String merchantId;
    private final String verificationKey;
    private final String secretKey;

    // ##### Parameter settings
    private final boolean allowEditBillName;
    private final boolean allowEditBillEmail;
    private final boolean allowEditBillPhone;
    private final boolean allowPhoneValidation;
    private final boolean allowEmailValidation;

    // ##### SDK Settings
    private final EnvironmentMode environmentMode;
    private final boolean expressMode;
    private final long timeOut;

    // ##### Payment Setting
    private final TxnType txnType;

    private MobileSDKParam(Builder builder) {
        this.orderId = builder.orderId;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.countryCode = builder.countryCode;
        this.billName = builder.billName;
        this.billEmail = builder.billEmail;
        this.billPhone = builder.billPhone;
        this.billDesc = builder.billDesc;
        this.channel = builder.channel;

        this.cardNo = builder.cardNo;
        this.cardMonth = builder.cardMonth;
        this.cardYear = builder.cardYear;
        this.cardCVV = builder.cardCVV;
        this.cardToken = builder.cardToken;
        this.cardTokenStatus = builder.cardTokenStatus;

        this.merchantId = builder.merchantId;
        this.verificationKey = builder.verificationKey;
        this.secretKey = builder.secretKey;

        this.allowEditBillName = builder.allowEditBillName;
        this.allowEditBillEmail = builder.allowEditBillEmail;
        this.allowEditBillPhone = builder.allowEditBillPhone;
        this.allowPhoneValidation = builder.allowPhoneValidation;
        this.allowEmailValidation = builder.allowEmailValidation;

        this.environmentMode = builder.environmentMode;
        this.expressMode = builder.expressMode;
        this.timeOut = builder.timeOut;

        this.txnType = builder.txnType;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getBillName() {
        return this.billName;
    }

    public String getBillEmail() {
        return this.billEmail;
    }

    public String getBillPhone() {
        return this.billPhone;
    }

    public String getBillDesc() {
        return this.billDesc;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardMonth() {
        return this.cardMonth;
    }

    public void setCardMonth(String cardMonth) {
        this.cardMonth = cardMonth;
    }

    public String getCardYear() {
        return this.cardYear;
    }

    public void setCardYear(String cardYear) {
        this.cardYear = cardYear;
    }

    public String getCardCVV() {
        return this.cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getCardToken() {
        return this.cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public Status getCardTokenStatus() {
        return this.cardTokenStatus;
    }

    public void setCardTokenStatus(Status cardTokenStatus) {
        this.cardTokenStatus = cardTokenStatus;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public String getVerificationKey() {
        return this.verificationKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public boolean getAllowEditBillName() {
        return this.allowEditBillName;
    }

    public boolean getAllowEditBillEmail() {
        return this.allowEditBillEmail;
    }

    public boolean getAllowEditBillPhone() {
        return this.allowEditBillPhone;
    }

    public boolean getAllowPhoneValidation() {
        return this.allowPhoneValidation;
    }

    public boolean getAllowEmailValidation() {
        return this.allowEmailValidation;
    }

    public EnvironmentMode getEnvironmentMode() {
        return this.environmentMode;
    }

    public boolean getExpressMode() {
        return  this.expressMode;
    }

    public long getTimeOut() {
        return  this.timeOut;
    }

    public TxnType getTxnType() {
        return  this.txnType;
    }

    @Override
    public String toString() {
        return String.format("{ " +
                        "orderId:%s, " +
                        "amount:%s, " +
                        "currency:%s, " +
                        "countryCode:%s, " +
                        "billName:%s, " +
                        "billEmail:%s, " +
                        "billPhone:%s, " +
                        "billDesc:%s, " +
                        "channel:%s, " +
                        "cardNo:%s, " +
                        "cardMonth:%s, " +
                        "cardYear:%s, " +
                        "cardCVV:%s, " +
                        "cardToken:%s, " +
                        "cardTokenStatus:%s, " +
                        "merchantId:%s, " +
                        "verificationKey:%s, " +
                        "secretKey:%s, " +
                        "allowEditBillName:%s, " +
                        "allowEditBillEmail:%s, " +
                        "allowEditBillPhone:%s, " +
                        "allowPhoneValidation:%s, " +
                        "allowEmailValidation:%s, " +
                        "environmentMode:%s, " +
                        "expressMode:%s, " +
                        "timeOut:%s, " +
                        "txnType:%s " +
                        "}",
                orderId,
                amount,
                currency,
                countryCode,
                billName,
                billEmail,
                billPhone,
                billDesc,
                channel,
                cardNo,
                cardMonth,
                cardYear,
                cardCVV,
                cardToken,
                cardTokenStatus.getStatus(),
                merchantId,
                verificationKey,
                secretKey,
                allowEditBillName,
                allowEditBillEmail,
                allowEditBillPhone,
                allowPhoneValidation,
                allowEmailValidation,
                environmentMode,
                expressMode,
                timeOut,
                txnType
        );
    }

    public static class Builder {
        private String orderId;
        private String amount;
        private String currency;
        private String countryCode;
        private String billName;
        private String billEmail;
        private String billPhone;
        private String billDesc;
        private String channel = "multi";

        private String cardNo;
        private String cardMonth;
        private String cardYear;
        private String cardCVV;
        private String cardToken;
        private Status cardTokenStatus;

        private String merchantId;
        private String verificationKey;
        private String secretKey;

        private boolean allowEditBillName = false;
        private boolean allowEditBillEmail = false;
        private boolean allowEditBillPhone = false;
        private boolean allowPhoneValidation = false;
        private boolean allowEmailValidation = false;

        private EnvironmentMode environmentMode = EnvironmentMode.PRODUCTION;
        private boolean expressMode = false;
        private long timeOut = 350000;

        private TxnType txnType = TxnType.SALS;

        public Builder() {
        }

        public MobileSDKParam.Builder setOrderId(final String value) {
            this.orderId = value;
            return this;
        }

        public MobileSDKParam.Builder setAmount(final String value) {
            this.amount = value;
            return this;
        }

        public MobileSDKParam.Builder setCurrency(final String value) {
            this.currency = value;
            return this;
        }

        public MobileSDKParam.Builder setCountryCode(final String value) {
            this.countryCode = value;
            return this;
        }

        public MobileSDKParam.Builder setBillName(final String value) {
            this.billName = value;
            return this;
        }

        public MobileSDKParam.Builder setBillEmail(final String value) {
            this.billEmail = value;
            return this;
        }

        public MobileSDKParam.Builder setBillPhone(final String value) {
            this.billPhone = value;
            return this;
        }

        public MobileSDKParam.Builder setBillDesc(final String value) {
            this.billDesc = value;
            return this;
        }

        public MobileSDKParam.Builder setChannel(final String value) {
            this.channel = value;
            return this;
        }

        public MobileSDKParam.Builder setCardNo(final String value) {
            this.cardNo = value;
            return this;
        }

        public MobileSDKParam.Builder setCardMonth(final String value) {
            this.cardMonth = value;
            return this;
        }

        public MobileSDKParam.Builder setCardYear(final String value) {
            this.cardYear = value;
            return this;
        }

        public MobileSDKParam.Builder setCardCVV(final String value) {
            this.cardCVV = value;
            return this;
        }

        public MobileSDKParam.Builder setCardToken(final String value) {
            this.cardToken = value;
            return this;
        }

        public MobileSDKParam.Builder setCardTokenStatus(final Status value) {
            this.cardTokenStatus = value;
            return this;
        }

        public MobileSDKParam.Builder setMerchantId(final String value) {
            this.merchantId = value;
            return this;
        }

        public MobileSDKParam.Builder setVerificationKey(final String value) {
            this.verificationKey = value;
            return this;
        }

        public MobileSDKParam.Builder setSecretKey(final String value) {
            this.secretKey = value;
            return this;
        }

        public MobileSDKParam.Builder setAllowEditBillName(final boolean value) {
            this.allowEditBillName = value;
            return this;
        }

        public MobileSDKParam.Builder setAllowEditBillEmail(final boolean value) {
            this.allowEditBillEmail = value;
            return this;
        }

        public MobileSDKParam.Builder setAllowEditBillPhone(final boolean value) {
            this.allowEditBillPhone = value;
            return this;
        }

        public MobileSDKParam.Builder setAllowPhoneValidation(final boolean value) {
            this.allowPhoneValidation = value;
            return this;
        }

        public MobileSDKParam.Builder setAllowEmailValidation(final boolean value) {
            this.allowEmailValidation = value;
            return this;
        }

        public MobileSDKParam.Builder setEnvironmentMode(final EnvironmentMode value) {
            this.environmentMode = value;
            return this;
        }

        public MobileSDKParam.Builder setExpressMode(final boolean value) {
            this.expressMode = value;
            return this;
        }

        public MobileSDKParam.Builder setExpressMode(final long value) {
            this.timeOut = value;
            return this;
        }

        public MobileSDKParam.Builder setTxnType(final TxnType value) {
            this.txnType = value;
            return this;
        }

        public MobileSDKParam create() {
            return new MobileSDKParam(this);
        }

    }

    public enum EnvironmentMode {
        DEVELOPMENT,
        PRODUCTION
    }

    public enum TxnType {
        AUTS,
        SALS
    }

    public enum Status {
        REQ_NEW_TOKEN ("1"),
        USE_THIS_TOKEN ("2");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getStatus() {
            return this.value;
        }
    }
}