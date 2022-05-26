
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author peter john
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String firstWord;
        String secondWord;
        
        String sub;
        try {
            System.out.print("Enter the First Word : ");
            firstWord = scanner.nextLine();
            System.out.print("Enter the Second Word: ");
            secondWord = scanner.nextLine();

            sub = firstWord.substring(firstWord.length() - 2, firstWord.length());
            
            Matcher matcher = Pattern.compile("[a-zA-Z]{1,2}" + sub + " ").matcher(secondWord);

            System.out.println(matcher.matches() ? String.format("%s rhymes with %s", secondWord, firstWord) : "I'm not sure!Sorry!");
        } catch(RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
        }
    }
}
