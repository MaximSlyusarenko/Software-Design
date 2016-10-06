package ru.ifmo.ctddev.slyusarenko.sd.lab2;

import net.oauth.OAuth;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import ru.ifmo.ctddev.slyusarenko.sd.core.HttpBasedProcessor;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Maxim Slyusarenko
 * @since 05.10.16
 */
public class TwitterService {

    private static final String SEARCH_URL = "https://api.twitter.com/1.1/search/tweets.json";

    private static final String OAUTH = "OAuth";
    private static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    private static final String OAUTH_NONCE = "oauth_nonce";
    private static final String OAUTH_SIGNATURE = "oauth_signature";
    private static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    private static final String OAUTH_TIMESTAMP = "oauth_timestamp";
    private static final String OAUTH_TOKEN = "oauth_token";
    private static final String OAUTH_VERSION = "oauth_version";

    private static final String OAUTH_CONSUMER_KEY_VALUE = "Nl3KQHRLfCvVI1nKY66XKR884";
    private static final String OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1";
    private static final String OAUTH_TOKEN_VALUE = "425210268-KNg66hRGLstog0P6ZIAuIrgJMZ37bK3TuxfxpWrq";
    private static final String OAUTH_VERSION_VALUE = "1.0";

    private static final String CONSUMER_SECRET = "36gdoWP3E0UM2Rh2x7pjGMa82NgAPalw6BG14vog1kUvizgjL4";
    private static final String OAUTH_TOKEN_SECRET = "UWwquxcLJ2ccqnsm3mYngbu7dfmk6VC9brWy9Q6w37C8n";

    private final HttpBasedProcessor processor;

    public TwitterService() {
        processor = new HttpBasedProcessor();
    }

    public int[] getHashTagStatistics(String hashTag, int hours) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        int[] result = new int[hours];
        String url = SEARCH_URL;
        url += "?q=%23" + hashTag;
        HttpUriRequest get = RequestBuilder.get().setUri(url).addHeader("Authorization", generateAuthorizationHeader(hashTag)).build();
        System.out.println(processor.execute(get));
        // TODO: parse String to find answer
        return result;
    }

    private String generateAuthorizationHeader(String hashTag) throws InvalidKeyException, NoSuchAlgorithmException {
        String nonce = generateNonce();
        long timestamp = getTimestamp();
        return OAUTH + " " + OAuth.percentEncode(OAUTH_CONSUMER_KEY) + "=\"" + OAuth.percentEncode(OAUTH_CONSUMER_KEY_VALUE) + "\", " +
                OAuth.percentEncode(OAUTH_NONCE) + "=\"" + OAuth.percentEncode(nonce) + "\", " +
                OAuth.percentEncode(OAUTH_SIGNATURE) + "=\"" + OAuth.percentEncode(generateSignature(hashTag, nonce, timestamp)) + "\", " +
                OAuth.percentEncode(OAUTH_SIGNATURE_METHOD) + "=\"" + OAuth.percentEncode(OAUTH_SIGNATURE_METHOD_VALUE) + "\", " +
                OAuth.percentEncode(OAUTH_TIMESTAMP) + "=\"" + OAuth.percentEncode(Long.valueOf(timestamp).toString()) + "\", " +
                OAuth.percentEncode(OAUTH_TOKEN) + "=\"" + OAuth.percentEncode(OAUTH_TOKEN_VALUE) + "\", " +
                OAuth.percentEncode(OAUTH_VERSION) + "=\"" + OAuth.percentEncode(OAUTH_VERSION_VALUE) + "\"";
    }

    private String generateNonce() {
        return Base64.encodeBase64String(RandomStringUtils.randomAlphanumeric(32).getBytes());
    }

    private String generateSignature(String hashTag, String nonce, long timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        String parameterString = OAuth.percentEncode(OAUTH_CONSUMER_KEY) + "=" + OAuth.percentEncode(OAUTH_CONSUMER_KEY_VALUE) + "&" +
                OAuth.percentEncode(OAUTH_NONCE) + "=" + OAuth.percentEncode(nonce) + "&" +
                OAuth.percentEncode(OAUTH_SIGNATURE_METHOD) + "=" + OAuth.percentEncode(OAUTH_SIGNATURE_METHOD_VALUE) + "&" +
                OAuth.percentEncode(OAUTH_TIMESTAMP) + "=" + OAuth.percentEncode(Long.valueOf(timestamp).toString()) + "&" +
                OAuth.percentEncode(OAUTH_TOKEN) + "=" + OAuth.percentEncode(OAUTH_TOKEN_VALUE) + "&" +
                OAuth.percentEncode(OAUTH_VERSION) + "=" + OAuth.percentEncode(OAUTH_VERSION_VALUE) + "&" +
                OAuth.percentEncode("q") + "=" + OAuth.percentEncode("#" + hashTag);
        String signatureBaseString = "GET&" + OAuth.percentEncode(SEARCH_URL) + "&" + OAuth.percentEncode(parameterString);
        String signingKey = OAuth.percentEncode(CONSUMER_SECRET) + "&" + OAuth.percentEncode(OAUTH_TOKEN_SECRET);
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(signingKey.getBytes(), "HmacSHA1"));
        return new String(Base64.encodeBase64(mac.doFinal(signatureBaseString.getBytes())));
    }

    private long getTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
