import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * This program validates and collects user input to write it to an output file.
 *
 * @author Varun Parbhakar, Roland Hanson, Johnny Heridea
 */
public class DefendYourCode {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String userName = collectName(scan);

        //Collecting integer
        // The user has the ability to enter the max integer
        // number and the min integer number, run a check when computing the
        // addition and the multiplication, if overflow occurs
        // then you can just convert it to Big Int and do the math or just write
        // the result as "overflow".
        System.out.println("Collecting Integer 1");
        int integer1 = collectInteger(scan);
        System.out.println("Collecting Integer 2");
        int integer2 = collectInteger(scan);

        //Collecting File Name
        //Make sure the input and output file names are not the same when collected
        System.out.println("Collecting input file");
        String inputFileName = collectFileName(scan);
        System.out.println("Collecting output file");
        String outputFileName = collectFileName(scan);
        
        //Collecting password
        System.out.println("Collecting password");
        collectPassword(scan);
    }

    /**
     * This method validates the user input where each should be at
     * most 50 characters, special characters allowed
     * -- decide what is legitimate for characters for first and last name
     * EX: Varun Parbhakar
     * @param theText
     * @return
     */
    public static boolean validateName(String theText) {
        String regex = "[a-zA-Z]{2,50} [a-zA-Z]{2,50}";
        return (regexEngine(regex, theText));
    }

    /**
     * This method collects the user's name. Prompts for and reads the user's first name, then last name.
     * @param theInput
     * @return
     */
    public static String collectName(Scanner theInput) {
        String userResponse = "";

        while(true) {
            System.out.print("Please enter in your name: ");
            userResponse = theInput.nextLine().strip();

            if(validateName(userResponse)) {
                return userResponse;
            } else {
                System.out.println("Error: Please enter a name EX \"Varun Parbhakar\"");
            }
        }

    }


    /**
     * Validates the input is an integer and also confirms that the integer is in
     * the range of the INT datatype.
     * @param theText
     * @return
     */
    public static boolean validateInteger(String theText) {
        String regex = "(-)?\\d{1,10}";
        if(regexEngine(regex, theText)) {
            try {
                Integer.parseInt(theText);
            } catch (Exception e) {
                return false;
            }
            return true;

        }
        return false;
    }

    /**
     * prompts for and reads in two int values from the user (range of values are those of a 4 byte int)
     * Max and Minimum integers are allowed, anything that is not an integer will be
     * considered false.
     * @param theInput
     * @return integer
     */
    public static int collectInteger(Scanner theInput) {
        String userResponse = "";

        while(true) {
            System.out.print("Please enter your integer: ");
            userResponse = theInput.nextLine().strip();

            if(validateInteger(userResponse)) {
                return Integer.parseInt(userResponse);
            } else {
                System.out.println("Error: Please enter an integer in the range of 4 bit integer");
            }
        }

    }

    /**
     * The file being collected must already be created and must be a .txt file.
     * @param theInput
     * @return
     */
    public static String collectFileName(Scanner theInput) {
        String userResponse = "";

        while(true) {
            System.out.print("Please enter file name: ");
            userResponse = theInput.nextLine().strip();

            if(validateFileName(userResponse)) {
                return userResponse;
            } else {
                System.out.println("Error: Please enter a valid file name EX: input.txt");
            }
        }
    }


    /**
     * This method validates the file name, also confirms that the file
     * passed in exists, if the file doesn't exist then the method will not create
     * a new file.
     * @param theFileName
     * @return
     */
    public static boolean validateFileName(String theFileName) {
        //[^#|%|&|{|}|\|<|>|\*|\?|\\| |$|!|`|"|:|@|\+|'\|=][a-zA-Z0-9-]{1,31}.txt
        String regex = "[a-zA-Z0-9()\\[\\]\\/\\?\\-]{1,31}.txt";
        if(regexEngine(regex, theFileName)) {
            try {
                File test = new File(theFileName);
                if(!test.exists()) {
                    System.out.println("File does not exist");
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
            return true;

        } else {
            return false;
        }
    }
    
    /**
     * Collects the password from the user
     * @param theInput
     * @return
     */
    private static void collectPassword(Scanner theInput) {
    	String userResponse = "";
		SecureRandom rand = new SecureRandom();
    	byte [] salt = new byte[16];
    	rand.nextBytes(salt);
    	boolean pass = false;
    	while(pass == false) {
    		System.out.println("Please enter a password that:\n"
    				+ "- Is at least 10 characters long\n"
    				+ "- Contains at least 1 uppercase character\n"
    				+ "- Contains at least 1 lowercase character\n"
    				+ "- Contains at least 1 digit\n"
    				+ "- Contains at least 1 puncuation mark\n"
    				+ "- Does not have 3 consecutive lowercase characters");
    		userResponse = theInput.nextLine().strip();
    		if (validatePassword(userResponse)) {
    			byte[] password = hashPassword(userResponse, salt);
    			createPasswordFile(password);
    			System.out.println("Please reenter password for verification");
    			userResponse = theInput.nextLine().strip();
    			byte[] passCheck = hashPassword(userResponse, salt);
    			if (verifyPassword(passCheck)) {
        			pass = true;
    			} else {
    				System.out.println("Error: Passwords do not match");
    			}
    		} else {
    			System.out.println("Error: Please enter a valid password (EX: PasSWoRd1!)");
    		}
    	}
    }

    private static boolean verifyPassword(byte[] thePassword) {
    	File file = new File("Password.txt");
    	Path p = file.toPath();
    	
    	try {
			byte [] passCheck = Files.readAllBytes(p);
			if (Arrays.equals(passCheck, thePassword)) {
				return true;
			}
		} catch (IOException e) {
			System.out.println("Error: No file found");
		}
    	return false;
    }
    
    /**
     * Validates that the password matches its requirements
     * Does not validate password re-entry 
     * @param thePassword
     * @return
     */
    private static boolean validatePassword(String thePassword) {
    	String regex = "^(?=.+[a-z])(?=.+[A-Z])(?=.+[!?.,()\\]\\[{}])(?=.+\\d)(?!.+[a-z][a-z][a-z])[a-zA-z!?.,()\\]\\[{}\\d]{10,}$";
    	return regexEngine(regex, thePassword);
    }
  
    /**
     * Hashes the password with a salt
     * Code referenced/used from: https://www.baeldung.com/java-password-hashing
     * @param thePassword
     * @return
     * @throws InvalidKeySpecException 
     */
    private static byte[] hashPassword(String thePassword, byte[] salt) {
    	KeySpec spec = new PBEKeySpec(thePassword.toCharArray(), salt, 65536, 128);
    	try {
			SecretKeyFactory fact = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = fact.generateSecret(spec).getEncoded();
			return hash;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.out.println("Error: No such algorithm exists");
		}
    	byte[] fail = null;
    	return fail;
    }
    
    /**
     * Creates the password file and writes the password into it 
     * @param thePassword
     */
    private static void createPasswordFile(byte[] thePassword) {
    	File pass = new File("Password.txt");
    	try {
			Path file = pass.toPath();
			Files.write(file, thePassword);
		} catch (IOException e) {
			System.out.println("Error: File not found");
		}
    }
    
    /**
     * This method is used for testing a string with a regex.
     * @param theRegex
     * @param theUserInput
     * @return
     */
    public static boolean regexEngine(String theRegex, String theUserInput) {

        Pattern pt = Pattern.compile(theRegex);
        Matcher mt = pt.matcher(theUserInput);

        return mt.matches();
    }

}
