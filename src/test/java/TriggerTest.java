import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TriggerTest {

    @Test
    public void whenTrigger() {
        int original = new Trigger().multiByTwo(2);
        assertThat(original, is(4));
    }

}