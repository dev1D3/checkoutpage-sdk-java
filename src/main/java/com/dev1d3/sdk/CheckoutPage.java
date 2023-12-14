package com.dev1d3.sdk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.stream.Collectors;

/**
 * Class for build payment URL
 */
public class CheckoutPage
{
    /**
     * Encoding charset
     */
    private String CHARSET = "UTF-8";

    /**
     * payment domain with path
     */
    private String baseUrl = "https://checkout.1d3.com/payment";

    /**
     * Signature handler for generate signature
     */
    private SignatureHandler signatureHandler;

    /**
     * com.dev1d3.sdk.CheckoutPage constructor
     * @param signHandler signature handler for generate signature
     */
    public CheckoutPage(SignatureHandler signHandler) {
        signatureHandler = signHandler;
    }

    /**
     * Method for set base checkout page URL
     * @param url
     * @return
     */
    public CheckoutPage setBaseUrl(String url) {
        baseUrl = url;

        return this;
    }

    /**
     * Method build payment URL
     * @param payment com.dev1d3.sdk.Payment instance with payment params
     * @return string URL that you can use for redirect on checkout page
     */
    public String getUrl(Payment payment) {
        String signature = "&signature=".concat(encode(signatureHandler.sign(payment.getParams())));
        String query = payment.getParams().entrySet().stream()
            .map(e -> e.getKey() + "=" + encode(e.getValue()))
            .collect(Collectors.joining("&"));

        return
            baseUrl
                .concat("?")
                .concat(query)
                .concat(signature);

    }

    /**
     * Method for URL encoding payment params
     * @param param payment param value
     * @return URL encoded param
     */
    private String encode(Object param) {
        try{
            return URLEncoder.encode(param.toString(), CHARSET);
        } catch(UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }
}
