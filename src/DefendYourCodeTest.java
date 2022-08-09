import static org.junit.jupiter.api.Assertions.*;
import java.security.SecureRandom;
import org.junit.jupiter.api.Test;

class DefendYourCodeTest {

	@Test
	void testPasswordConsecutiveCharactersBegining() {
		assertFalse(DefendYourCode.validatePassword("thePAssWorD1!"));
	}

	@Test
	void testPasswordConsecutiveCharactersMidlle() {
		assertFalse(DefendYourCode.validatePassword("PAsswOrD1!"));
	}

	@Test
	void testPasswordConsecutiveCharactersEnd() {
		assertFalse(DefendYourCode.validatePassword("ThEPA33.Word"));
	}
	
	@Test
	void testPasswordNoPunctuation() {
		assertFalse(DefendYourCode.validatePassword("PAssWorD12"));
	}

	@Test
	void testPasswordNoDigits() {
		assertFalse(DefendYourCode.validatePassword("PAsSWorD!."));
	}

	@Test
	void testPasswordNoUpperCase() {
		assertFalse(DefendYourCode.validatePassword("password!1"));
	}
	
	@Test
	void testPasswordNoLowerCase() {
		assertFalse(DefendYourCode.validatePassword("PASSWORD1!"));
	}
	
	@Test
	void testPasswordTooSmall() {
		assertFalse(DefendYourCode.validatePassword("TesT1!2"));
	}
	
	@Test
	void testPasswordVerificationPass() {
		SecureRandom rand = new SecureRandom();
    	byte [] salt = new byte[16];
    	rand.nextBytes(salt);
		String in = "PaSsWorD1!";
		String test = "PaSsWorD1!";
		byte[] pass1 = DefendYourCode.hashPassword(in, salt);
		DefendYourCode.createPasswordFile(pass1);
		byte[] pass2 = DefendYourCode.hashPassword(test, salt);
		assertTrue(DefendYourCode.verifyPassword(pass2));
	}
	
	@Test
	void testPasswordVerificationFail() {
		SecureRandom rand = new SecureRandom();
    	byte [] salt = new byte[16];
    	rand.nextBytes(salt);
		String in = "PaSsWorD1!";
		String test = "PaSsWorD123!";
		byte[] pass1 = DefendYourCode.hashPassword(in, salt);
		DefendYourCode.createPasswordFile(pass1);
		byte[] pass2 = DefendYourCode.hashPassword(test, salt);
		assertFalse(DefendYourCode.verifyPassword(pass2));
	}
}
