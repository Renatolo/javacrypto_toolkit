package crypto.toolkit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void testHelpCommand() {
        String[] args = {"help"};
        App.main(args);
        // Since the help command just prints to console, we can check if it runs without exceptions
        assertTrue(true);
    }
}
