package passwordmanager;

import java.security.SecureRandom;
import javax.swing.JOptionPane;

public class GeneratePW {

	 public static String Generate(String accountName) {
	     if (accountName.isEmpty()) {
	         JOptionPane.showOptionDialog(null,
	                 "Please provide an account name/title to use this feature", "No Account Name Provided",
	                 JOptionPane.DEFAULT_OPTION,
	                 JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
	         return "";
	     }
	
	     SecureRandom random = new SecureRandom();
	     String letters = "abcdefghijklmnopqrstuvwxyz";
	     String numbers = "0123456789";
	     String symbols = "!@#$^*()_-=+~?/.,";
	     String all = letters + letters.toUpperCase() + numbers + symbols;
	
	     // Create random string of characters
	     StringBuilder password = new StringBuilder();
	     while (password.length() < 26) {
	         password.append(all.charAt(random.nextInt(all.length())));
	     }

	     password.insert(random.nextInt(password.length()), letters.charAt(random.nextInt(letters.length())));
	     password.insert(random.nextInt(password.length()), letters.toUpperCase().charAt(random.nextInt(letters.length())));
	     password.insert(random.nextInt(password.length()), numbers.charAt(random.nextInt(numbers.length())));
	     password.insert(random.nextInt(password.length()), symbols.charAt(random.nextInt(symbols.length())));
	
	     return accountName + password.toString();
	 }
}