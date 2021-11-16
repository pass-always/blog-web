import blog.util.Passwords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the static Password class
 * Salt getting methods are not tested as they generate random outputs
 * Base64 encode/decode methods aren't tested as they just wrap existing Java methods
 */
public class TestPasswords {

    String password;
    /**
     * password salted with an 8 byte salt, hashed, iterated 100,000 times then encoded into base 64
     */
    String passwordHash;
    /**
     * base 64 encoded 8 byte salted used to create passwordHash
     */
    String saltHash;

    @BeforeEach
    public void setUp() {
        password = "this is a test password";

        saltHash = "Ssc3DTcw/YQ=";
        passwordHash = "5FLfCL0zwfe05h/wLTaTXQ3TXU/Ym7f1HFgU4dMSaXVc9bKosirXTdnLSTsrJ0qe74nszxKvzsdhmnxAX0t3RQ==";
    }

    @Test
    public void testHashMatches() {
        byte[] result = Passwords.hash(password.toCharArray(), Passwords.base64Decode(saltHash), Passwords.DEFAULT_ITERATIONS);

        assertArrayEquals(Passwords.base64Decode(passwordHash), result);
    }

    @Test
    public void testHasSaltAndEncodeMatches() {
        String result = Passwords.hashSaltAndEncode(password, saltHash, Passwords.DEFAULT_ITERATIONS);

        assertEquals(passwordHash, result);
    }

    @Test
    public void testIsExpectedPassWordOverloadedForBase64StringsMatchesPassword() {
        boolean result = Passwords.isExpectedPassword(password, saltHash, Passwords.DEFAULT_ITERATIONS, passwordHash);

        assertTrue(result);
    }

    @Test
    public void testIsExpectedPassWordOverloadedForBase64StringsReturnsFalseIfPasswordNull() {
        //noinspection ConstantConditions   will always return false, but that's what we're verifying
        boolean result = Passwords.isExpectedPassword(null, saltHash, Passwords.DEFAULT_ITERATIONS, passwordHash);

        assertFalse(result);
    }

    @Test
    public void testIsExpectedPassWordOverloadedForBase64StringsReturnsFalseForNonMatchingPassword() {
        boolean completelyWrong = Passwords.isExpectedPassword("salgdbenrglslngdkjwea", saltHash, Passwords.DEFAULT_ITERATIONS, passwordHash);
        boolean addsTrailingSpace = Passwords.isExpectedPassword("this is a test password ", saltHash, Passwords.DEFAULT_ITERATIONS, passwordHash);
        boolean wrongSalt = Passwords.isExpectedPassword("this is a test password ", Passwords.base64Encode(Passwords.getNextSalt()), Passwords.DEFAULT_ITERATIONS, passwordHash);
        boolean wrongIterations = Passwords.isExpectedPassword("this is a test password ", saltHash, Passwords.DEFAULT_ITERATIONS - 10, passwordHash);

        assertFalse(completelyWrong);
        assertFalse(addsTrailingSpace);
        assertFalse(wrongSalt);
        assertFalse(wrongIterations);
    }

    @Test
    public void testIsExpectedPasswordMatchesPassword() {
        byte[] decodedSalt = Passwords.base64Decode(saltHash);
        byte[] decodedPassword = Passwords.base64Decode(passwordHash);

        boolean result = Passwords.isExpectedPassword(password.toCharArray(), decodedSalt, Passwords.DEFAULT_ITERATIONS, decodedPassword);

        assertTrue(result);
    }

    @Test
    public void testIsExpectedPasswordDoesNotMatchNonPassword() {
        byte[] decodedSalt = Passwords.base64Decode(saltHash);
        byte[] decodedPassword = Passwords.base64Decode(passwordHash);

        boolean completelyWrong = Passwords.isExpectedPassword("salgdbenrglslngdkjwea".toCharArray(), decodedSalt, Passwords.DEFAULT_ITERATIONS, decodedPassword);
        boolean addsTrailingSpace = Passwords.isExpectedPassword("this is a test password ".toCharArray(), decodedSalt, Passwords.DEFAULT_ITERATIONS, decodedPassword);
        boolean wrongSalt = Passwords.isExpectedPassword("this is a test password ".toCharArray(), Passwords.getNextSalt(), Passwords.DEFAULT_ITERATIONS, decodedPassword);
        boolean wrongIterations = Passwords.isExpectedPassword("this is a test password ".toCharArray(), decodedSalt, Passwords.DEFAULT_ITERATIONS - 10, decodedPassword);

        assertFalse(completelyWrong);
        assertFalse(addsTrailingSpace);
        assertFalse(wrongSalt);
        assertFalse(wrongIterations);
    }
}
