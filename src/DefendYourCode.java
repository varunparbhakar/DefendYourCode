import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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





//        for (int i = 0; i < 10; i++) {
//            System.out.print("Please enter an integer: ");
//            String userInput  = scan.nextLine();
//            if(getInteger(userInput)) {
//                System.out.println(Integer.parseInt(userInput));
//                System.out.println(getInteger(userInput));
//            } else {
//                System.out.println("false");;
//            }
//
//        }
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
