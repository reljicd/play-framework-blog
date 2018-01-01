import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit testing does not require Play application start up.
 * <p>
 * https://www.playframework.com/documentation/latest/JavaTest
 */
public class UnitTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

}
