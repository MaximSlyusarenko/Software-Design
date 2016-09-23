import org.junit.Assert;
import org.junit.Test;

/**
 * @author Maxim Slyusarenko
 * @since 23.09.16
 */
public class LRUCacheTest {

    @Test
    public void constructorTest() {
        try {
            new LRUCache<>(0);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException ignored) {

        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testSmallCache() {
        LRUCache<Integer, String> cache = new LRUCache<>(1);
        cache.set(0, "0");
        cache.set(1, "1");
        Assert.assertNull(cache.get(0).orElse(null));
        Assert.assertEquals(cache.get(1).orElse(null), "1");
    }

    @Test
    public void testLargeCache() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(5000);
        for (int i = 0; i < 100; i++) {
            cache.set(i, i);
        }
        for (int i = 0; i < 100; i++) {
            Assert.assertEquals((long) cache.get(i).orElse(null), (long) i);
        }
    }

    @Test
    public void fullCache() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.set(0, "0");
        cache.set(1, "1");
        Assert.assertEquals(cache.get(0).orElse(null), "0");
        Assert.assertEquals(cache.get(1).orElse(null), "1");
    }
}
