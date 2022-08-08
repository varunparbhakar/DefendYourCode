import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefendYourCode {
    
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		// Disregard this, team member is doing this
		System.out.println("Please enter your name (LastName FirstName MI: ");
		if (collectName(s.nextLine())) {
			// Does something
		}
		// Password thing goes here
		
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
    /**
     * This method is used for validating names for class roster.
     * Input format: LastName FirstName MI
     * EX: Parbhakar Varun GI
     * @param theText
     * @return
     */
    public static boolean collectName(String theText) {
        String regex = "[a-zA-Z]{2,25} [a-zA-Z]{2,25}( [a-zA-Z\\.])?";
        return regexEngine(regex, theText);
    }
}
