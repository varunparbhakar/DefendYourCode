import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        printIntoFile(userName, integer1, integer2, inputFileName, outputFileName);
    }

    /**
     * Prints information into output file
     * @param theUserName the users name
     * @param theInt1 the first integer of two typed by the user
     * @param theInt2 the second integer of two typed by the user
     * @param theInFileName name of the input file
     * @param theOutFileName name of the output file
     */
    public static void printIntoFile(String theUserName, int theInt1, int theInt2,
                                     String theInFileName, String theOutFileName){
        try{
            FileWriter myWriter = new FileWriter(theOutFileName);
            String[] theNames = theUserName.split(" ");
            myWriter.write("First Name: " + theNames[0] + "\n");
            myWriter.write("Last Name: " + theNames[1] + "\n");
            myWriter.write("First integer: " + theInt1 + "\nSecond integer: " + theInt2 + "\n");
            int theSum = theInt1 + theInt2;
            int theProduct = theInt1 * theInt2;
            myWriter.write("The Sum: " + theSum + "\nThe Product: " + theProduct + "\n");
            myWriter.write("Input File Name: " + theInFileName + "\n");
            myWriter.write("The Contents of the input file are below:\n");
            myWriter.write(getInputContents(theInFileName));
        } catch (IOException e){
            System.out.println("An Error occured.");
            e.printStackTrace();
        }
    }

    /**
     * Method to retrive the contents of the Input file
     * @param theInFileName name of the input file.
     * @return the contents of the input file.
     */
    public static String getInputContents(String theInFileName) {
        StringBuilder theResult = new StringBuilder();
        try {
            FileReader fr=new FileReader(theInFileName);
            int r=0;
            while((r=fr.read())!=-1)
            {
                theResult.append((char) r);  //prints the content of the file
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return theResult.toString();
    }

    /**
     * This method validates the user input where each should be at
     * most 50 characters, special characters allowed
     * -- decide what is legitimate for characters for first and last name
     * EX: Varun Parbhakar
     * @param theText text representing name
     * @return true if the name is valid
     */
    public static boolean validateName(String theText) {
        String regex = "[a-zA-Z]{2,50} [a-zA-Z]{2,50}";
        return (regexEngine(regex, theText));
    }

    /**
     * This method collects the user's name. Prompts for and reads the user's first name, then last name.
     * @param theInput scanner for the input by user
     * @return The name collected
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
     * @param theText Integer to be validated
     * @return true if integer is valid
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
     * @param theInput scanner to gather input
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
     * @param theInput scanner for input
     * @return String for filename
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
     * @param theFileName filename to be validated
     * @return true if the filename is valid
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
