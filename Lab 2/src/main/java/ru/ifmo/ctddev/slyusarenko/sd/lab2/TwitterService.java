package ru.ifmo.ctddev.slyusarenko.sd.lab2;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

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
    private static final String STATUSES_RESPONSE = "statuses";
    private static final String MAX_ID = "max_id";
    private static final String COUNT = "count";
    private static final String RESULT_TYPE = "result_type";
    private static final String RESULT_TYPE_VALUE = "recent";
    private static final String ID_RESPONSE = "id";
    private static final String CREATED_AT_RESPONSE = "created_at";

    private static final String OAUTH_CONSUMER_KEY_VALUE = "Nl3KQHRLfCvVI1nKY66XKR884";
    private static final String OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1";
    private static final String OAUTH_TOKEN_VALUE = "425210268-KNg66hRGLstog0P6ZIAuIrgJMZ37bK3TuxfxpWrq";
    private static final String OAUTH_VERSION_VALUE = "1.0";

    private static final String CONSUMER_SECRET = "36gdoWP3E0UM2Rh2x7pjGMa82NgAPalw6BG14vog1kUvizgjL4";
    private static final String OAUTH_TOKEN_SECRET = "UWwquxcLJ2ccqnsm3mYngbu7dfmk6VC9brWy9Q6w37C8n";

    private static final int TWEETS_PER_PAGE = 100;

    private final HttpBasedProcessor processor;

    private Long maxId;

    public TwitterService() {
        processor = new HttpBasedProcessor();
    }

    public int[] getHashTagStatistics(String hashTag, int hours) throws IOException, NoSuchAlgorithmException, InvalidKeyException, ParseException {
        return executeWhileNeed(hashTag, hours, new int[hours], LocalDateTime.now(ZoneId.ofOffset("", ZoneOffset.UTC)));
    }

    private int[] executeWhileNeed(String hashTag, int hours, int[] result, LocalDateTime now) throws IOException, NoSuchAlgorithmException, InvalidKeyException, ParseException {
        String url = SEARCH_URL;
        url += "?q=%23" + hashTag + "&" + COUNT + "=" + TWEETS_PER_PAGE + "&" + RESULT_TYPE + "=" + RESULT_TYPE_VALUE;
        if (maxId != null) {
            url += "&" + MAX_ID + "=" + maxId;
        }
        HttpUriRequest get = RequestBuilder.get().setUri(url).addHeader("Authorization", generateAuthorizationHeader(hashTag)).build();
        if (!parseAnswer(processor.execute(get), hours, result, now)) {
            return result;
        } else {
            return executeWhileNeed(hashTag, hours, result, now);
        }
    }

    private boolean parseAnswer(String answer, int hours, int[] currentResult, LocalDateTime now) throws ParseException {
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (answer, JsonElement.class);
        JsonArray tweets = element.getAsJsonObject().get(STATUSES_RESPONSE).getAsJsonArray();
        int added = 0;
        for (int i = 0; i < tweets.size(); i++) {
            if (!parseTweet(tweets.get(i), hours, currentResult, now)) {
                break;
            } else {
                added++;
                maxId = tweets.get(i).getAsJsonObject().get(ID_RESPONSE).getAsLong() - 1;
            }
        }
        return (added == tweets.size() && tweets.size() != 0);
    }

    /**
     *
     * @param tweet Current tweet
     * @param hours Number of hours for which we are monitoring
     * @param currentResult Current result array
     * @return True if tweet was added to result, false otherwise
     */
    private boolean parseTweet(JsonElement tweet, int hours, int[] currentResult, LocalDateTime now) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse(tweet.getAsJsonObject().get(CREATED_AT_RESPONSE).getAsString(), formatter);
        int hoursBetween = (int) ChronoUnit.HOURS.between(date, now);
        if (hoursBetween >= hours) {
            return false;
        } else {
            currentResult[hoursBetween]++;
            return true;
        }
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
        String parameterString = "";
        parameterString += OAuth.percentEncode(COUNT) + "=" + OAuth.percentEncode(Integer.valueOf(TWEETS_PER_PAGE).toString()) + "&";
        if (maxId != null) {
            parameterString += OAuth.percentEncode(MAX_ID) + "=" + OAuth.percentEncode(maxId.toString()) + "&";
        }
        parameterString += OAuth.percentEncode(OAUTH_CONSUMER_KEY) + "=" + OAuth.percentEncode(OAUTH_CONSUMER_KEY_VALUE) + "&" +
                OAuth.percentEncode(OAUTH_NONCE) + "=" + OAuth.percentEncode(nonce) + "&" +
                OAuth.percentEncode(OAUTH_SIGNATURE_METHOD) + "=" + OAuth.percentEncode(OAUTH_SIGNATURE_METHOD_VALUE) + "&" +
                OAuth.percentEncode(OAUTH_TIMESTAMP) + "=" + OAuth.percentEncode(Long.valueOf(timestamp).toString()) + "&" +
                OAuth.percentEncode(OAUTH_TOKEN) + "=" + OAuth.percentEncode(OAUTH_TOKEN_VALUE) + "&" +
                OAuth.percentEncode(OAUTH_VERSION) + "=" + OAuth.percentEncode(OAUTH_VERSION_VALUE) + "&" +
                OAuth.percentEncode("q") + "=" + OAuth.percentEncode("#" + hashTag) + "&" +
                OAuth.percentEncode(RESULT_TYPE) + "=" + OAuth.percentEncode(RESULT_TYPE_VALUE);
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
