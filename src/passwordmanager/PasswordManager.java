package passwordmanager;

import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.UIManager;

public class PasswordManager extends JFrame {
	
    public static final String databaseFile = "src/accounts.xml";
    public static final String rockYouFile = "src/rockyou.txt";
    public static final String imageFile = "src/icon.png";
    public static final String masterPasswordFile = "src/masterpassword.txt";
    
    public static String masterPassword;
    private int loginAttempts = 0;
    private JLabel label;
    private JPasswordField passwordField;
    
    public static void main(String[] args) {
    	try {
    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	PasswordManager prompt = new PasswordManager();
    	prompt.setTitle("Password Manager");
    	prompt.setSize(500, 300);
    	prompt.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	prompt.setLocationRelativeTo(null);
    	prompt.setLayout(new GridBagLayout());
    	prompt.setVisible(true);
    	prompt.setIconImage(new ImageIcon(imageFile).getImage());
    	
    	try {
    		Path filePath = Path.of(masterPasswordFile);
    		System.out.println(filePath.toAbsolutePath());
            String content = Files.readString(filePath);
            masterPassword = content.replaceAll("\\s", "");
    	} catch (Exception e) {
            e.printStackTrace();
        }
    	
    	if (masterPassword.length() == 0) {
            new CreateMasterPassword();
        }
    }
    
    private PasswordManager() {
        label = new JLabel("Master Password:     ");
        add(label);
        
        passwordField = new JPasswordField(20);
        add(passwordField);
        
        passwordField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                char[] password = passwordField.getPassword();
                String passwordString = new String(password);

                try {
                    Path filePath = Path.of(masterPasswordFile);
                    String content = Files.readString(filePath);
                    masterPassword = content.replaceAll("\\s", "");
                } catch (Exception b) {
                    b.printStackTrace();
                }

                if (masterPassword.length() == 0) {
                    JOptionPane.showOptionDialog(null,
                            "You need to create a master password first!",
                            "No Master Password",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
                    return;
                }

                if (passwordmanager.org.rb.jbcrypt.BCrypt.checkpw(passwordString, masterPassword)) { 
                    AES key = new AES();
                    
                    try {
                        key.setKey(passwordString);
                    } catch (Exception b) {
                        b.printStackTrace();
                    }

                    for (Window window : Window.getWindows()) {
                        window.dispose();
                    }
                    new PasswordManagerMenu(masterPassword);
                } else {
                    loginAttempts++;
                    if (loginAttempts == 3) { 
                        dispose();
                        JOptionPane.showOptionDialog(null,
                                "Whoops! You've had too many failed login attempts.",
                                "Incorrect Master Password",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
                        return;
                    }
                    String attempts = "attempts";
                    if (loginAttempts == 2) {
                        attempts = "attempt";
                    }
                    JOptionPane.showOptionDialog(null,
                            "Incorrect Master Password! You have " + (3 - loginAttempts) + " " + attempts
                                    + " remaining.",
                            "Incorrect Master Password",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
                    passwordField.setText("");
                }

            }
        });
    }

}
