package com.dev1d3.sdk;

/**
 * Class for communicate with our
 */
public class Gate
{
    /**
     * com.dev1d3.sdk.SignatureHandler instance for check signature
     */
    private SignatureHandler signatureHandler;

    /**
     * com.dev1d3.sdk.CheckoutPage instance for build payment URL
     */
    private CheckoutPage checkoutPageUrlBuilder;

    /**
     * com.dev1d3.sdk.Gate constructor
     * @param secret site salt
     */
    public Gate(String secret) {
        signatureHandler = new SignatureHandler(secret);
        checkoutPageUrlBuilder = new CheckoutPage(signatureHandler);
    }

    /**
     * Method for set base checkout page URL
     * @param url checkout page URL
     * @return self for fluent interface
     */
    public Gate setBaseUrl(String url) {
        checkoutPageUrlBuilder.setBaseUrl(url);

        return this;
    }

    /**
     * Method build payment URL
     * @param payment com.dev1d3.sdk.Payment instance with payment params
     * @return string URL that you can use for redirect on checkout page
     */
    public String getPurchaseCheckoutPageUrl(Payment payment) {
        return checkoutPageUrlBuilder.getUrl(payment);
    }

    /**
     * Method for handling callback
     * @param data raw callback data in JSON format
     * @return com.dev1d3.sdk.Callback instance
     * @throws ProcessException throws when signature is invalid
     */
    public Callback handleCallback(String data) throws ProcessException {
        return new Callback(data, signatureHandler);
    }
}
