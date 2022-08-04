import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefendYourCode {
    public static void main(String[] args) {

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
        String regex = "[a-zA-Z]{2,20} [a-zA-Z]{2,20}( [a-zA-Z\\.]*)?";
        return regexEngine(regex, theText);
    }
}
