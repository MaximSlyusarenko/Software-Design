package ctddev.slyusarenko.sd.lab2.tests;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import ru.ifmo.ctdded.slyusarenko.sd.rule.HostReachableRule;
import ru.ifmo.ctddev.slyusarenko.sd.lab2.TwitterService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

/**
 * @author Maxim Slyusarenko
 * @since 07.10.16
 */
@HostReachableRule.HostReachable(TwitterServiceIntegrationTest.HOST)
public class TwitterServiceIntegrationTest {

    public static final String HOST = "api.twitter.com";

    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    @Test
    public void simpleTest() {
        TwitterService service = new TwitterService();
        try {
            int[] result = service.getHashTagStatistics("football", 2);
            Assert.assertEquals(result.length, 2);
            Assert.assertTrue(result[0] > 0);
            Assert.assertTrue(result[1] > 0);
        } catch (IOException | NoSuchAlgorithmException | ParseException | InvalidKeyException e) {
            Assert.fail();
        }
    }
}
