import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import ru.ifmo.ctddev.slyusarenko.sd.core.HttpBasedProcessor;
import ru.ifmo.ctddev.slyusarenko.sd.lab2.TwitterService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.mockito.Matchers.any;

/**
 * @author Maxim Slyusarenko
 * @since 07.10.16
 */
public class TwitterServiceUnitTests {

    private TwitterService service;

    @Mock
    private HttpBasedProcessor processor;

    @Before
    public void setUp() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        ZonedDateTime now = ZonedDateTime.now(ZoneId.ofOffset("", ZoneOffset.UTC));
        String firstProcessorResult = "{statuses:[{\"created_at\":\"" + now.format(formatter) + "\",\"id\":1" + "}]}";
        String secondProcessorResult = "{statuses:[]}";
        MockitoAnnotations.initMocks(this);
        service = new TwitterService();
        ReflectionTestUtils.setField(service, "processor", processor);
        Mockito.when(processor.execute(any()))
                .thenReturn(firstProcessorResult)
                .thenReturn(secondProcessorResult);
    }

    @Test
    public void simpleTest() {
        try {
            int[] result = service.getHashTagStatistics("nothing", 2);
            int[] expected = new int[2];
            expected[0] = 1;
            expected[1] = 0;
            Assert.assertArrayEquals(result, expected);
        } catch (IOException | NoSuchAlgorithmException | ParseException | InvalidKeyException e) {
            Assert.fail();
        }
    }
}
