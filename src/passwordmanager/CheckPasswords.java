package passwordmanager;

//Import Statements
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CheckPasswords extends javax.swing.JFrame {

	 private javax.swing.JButton closeButton;
	 private javax.swing.JLabel passwordScanTitleLabel;
	 private javax.swing.JLabel currentProcessLabel;
	 private javax.swing.JLabel percentLabel;
	 private javax.swing.JProgressBar progressBar;
	 private javax.swing.JScrollPane updatesScrollPane;
	 private javax.swing.JTextArea updates;
	
	 public CheckPasswords(String key) {
	
	     if (!key.equals(PasswordManager.masterPassword)) {
	         System.out.println("Proper Authentication Not Provided!");
	         return;
	     }
	
	     setTitle("Password Scan");
	     setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	     setLayout(null);
	     setVisible(true);
	     setIconImage(new ImageIcon(PasswordManager.imageFile).getImage());
	
	     initComponents();
	     setLocationRelativeTo(null);
	     worker.start();
	 }
	
	 private void initComponents() {
		 passwordScanTitleLabel = new javax.swing.JLabel();
	     currentProcessLabel = new javax.swing.JLabel();
	     progressBar = new javax.swing.JProgressBar();
	     percentLabel = new javax.swing.JLabel();
	     updatesScrollPane = new javax.swing.JScrollPane();
	     updates = new javax.swing.JTextArea();
	     closeButton = new javax.swing.JButton();
	
	     passwordScanTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
	     passwordScanTitleLabel.setText("Password Scan");
	
	     currentProcessLabel.setFont(new java.awt.Font("sansserif", 1, 12));
	     currentProcessLabel.setText("Checking password reuse...");
	
	     progressBar.setValue(0);
	     percentLabel.setText("0% Complete!");
	
	     updates.setEditable(false);
	     updates.setBackground(new java.awt.Color(255, 255, 255));
	     updates.setColumns(20);
	     updates.setLineWrap(true);
	     updates.setRows(5);
	     updates.setWrapStyleWord(true);
	     updatesScrollPane.setViewportView(updates);
	
	     closeButton.setText("Cancel");
	     closeButton.addActionListener(new java.awt.event.ActionListener() {
	         public void actionPerformed(java.awt.event.ActionEvent evt) {
	             closeButtonActionPerformed(evt);
	         }
	     });
	
	     javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	     getContentPane().setLayout(layout);
	     layout.setHorizontalGroup(
	             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                     .addGroup(layout.createSequentialGroup()
	                             .addGap(23, 23, 23)
	                             .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                                     .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102,
	                                             javax.swing.GroupLayout.PREFERRED_SIZE)
	                                     .addGroup(layout
	                                             .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                                             .addComponent(updatesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE,
	                                                     360,
	                                                     Short.MAX_VALUE)
	                                             .addComponent(percentLabel)
	                                             .addComponent(currentProcessLabel)
	                                             .addComponent(passwordScanTitleLabel)
	                                             .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE,
	                                                     javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
	                             .addContainerGap(25, Short.MAX_VALUE)));
	     layout.setVerticalGroup(
	             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                     .addGroup(layout.createSequentialGroup()
	                             .addGap(22, 22, 22)
	                             .addComponent(passwordScanTitleLabel)
	                             .addGap(18, 18, 18)
	                             .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
	                                     javax.swing.GroupLayout.PREFERRED_SIZE)
	                             .addGap(14, 14, 14)
	                             .addComponent(percentLabel)
	                             .addGap(18, 18, 18)
	                             .addComponent(currentProcessLabel)
	                             .addGap(18, 18, 18)
	                             .addComponent(updatesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 210,
	                                     javax.swing.GroupLayout.PREFERRED_SIZE)
	                             .addGap(18, 18, 18)
	                             .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
	                                     javax.swing.GroupLayout.PREFERRED_SIZE)
	                             .addContainerGap(25, Short.MAX_VALUE)));
	     pack();
	 }
	
	 private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
	     dispose();
	 }
	
	 private Thread worker = new Thread(new Runnable() {
	     @Override
	     public void run() {
	         File xmlFile = new File(PasswordManager.databaseFile);
	
	         try {
	             Set<String> accountSet = new HashSet<>();
	             Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
	             NodeList passwordList = database.getElementsByTagName("pass");

	             for (int i = 0; i < passwordList.getLength(); i++) {
	                 String password = passwordList.item(i).getAttributes().getNamedItem("pass").getNodeValue();
	                 accountSet.add(password);
	             }

	             if (accountSet.size() < passwordList.getLength()) { // If no duplicates they will be equal
	                 updates.append(
	                         "You are re-using passwords! Try to use strong and unique passwords for each of your accounts.\n--");
	                 updates.setCaretPosition(updates.getDocument().getLength());
	             }
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	
	         progressBar.setValue(10);
	         percentLabel.setText("10% Complete!");
	         currentProcessLabel.setText("Checking password renewal dates...");
	
	         try {
	             Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
	
	             NodeList renewalDateList = database.getElementsByTagName("date");
	             NodeList renewalPeriodList = database.getElementsByTagName("renewal");
	             NodeList accountList = database.getDocumentElement().getElementsByTagName("account");
	
	             double counter = 0;
	             for (int i = 0; i < accountList.getLength(); i++) {
	                 counter++;
	
	                 int amountIncrease = (int) ((double) (counter / accountList.getLength()) * 10);
	                 int newAmount = 10 + amountIncrease;
	                 progressBar.setValue(newAmount);
	                 percentLabel.setText(newAmount + "% Complete!");
	
	                 Element account = (Element) accountList.item(i);
	                 String accountName = account.getAttribute("name");
	
	                 var period = renewalPeriodList.item(i).getAttributes().getNamedItem("period").getNodeValue();
	                 if (period.equals("0")) {
	                     continue;
	                 }
	
	                 LocalDate dateNow = LocalDate.now();
	                 var renewal = renewalDateList.item(i).getAttributes().getNamedItem("renewal").getNodeValue();
	                 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                 LocalDate dateRenew = LocalDate.parse(renewal, formatter);
	                 long daysBetween = ChronoUnit.DAYS.between(dateNow, dateRenew);

	                 if (dateNow.isAfter(dateRenew)) {
	                     updates.append("\nYou have passed your renewal deadline for '" + accountName
	                             + "'. A password reset is overdue.\n--");
	                     updates.setCaretPosition(updates.getDocument().getLength());
	                 } else if (daysBetween <= 7) {
	                     updates.append("\nIt's time to update your password for '" + accountName + "'.\n--");
	                     updates.setCaretPosition(updates.getDocument().getLength());
	                 }
	
	             }
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	
	         try {
	             Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
	
	             NodeList accountList = database.getElementsByTagName("account");
	
	             double counter = 0;
	             for (int i = 0; i < accountList.getLength(); i++) {
	                 counter++;
	
	                 Node account = accountList.item(i);
	                 String accountName = account.getAttributes().getNamedItem("name").getNodeValue();
	
	                 String password = accountList.item(i).getChildNodes().item(4).getAttributes()
	                         .getNamedItem("pass")
	                         .getNodeValue();
	
	                 try {
	                     currentProcessLabel.setText(accountName + ": Scoring password...");
	
	                     String userPassword = AES.decrypt(password);
	                     int passwordScore = 0;

	                     for (int p = 0; p < userPassword.length(); p++) {
	                         if (passwordScore > 48) { // cap at 48 points
	                         } else {
	                             passwordScore += 2;
	                         }
	                     }

	                     if (userPassword.matches(".*[0-9]{3}.*")) {
	                         passwordScore += 10;
	                     } else if (userPassword.matches(".*[0-9].*")) {
	                         passwordScore += 2;
	                     }

	                     if (userPassword.matches(".*[a-z]{3}.*")) {
	                         passwordScore += 10;
	                     } else if (userPassword.matches(".*[a-z].*")) {
	                         passwordScore += 2;
	                     }
	
	                     if (userPassword.matches(".*[A-Z]{3}.*")) {
	                         passwordScore += 10;
	                     } else if (userPassword.matches(".*[A-Z].*")) {
	                         passwordScore += 2;
	                     }
	
	                     if (userPassword.matches(".*[~!@#$%^&*()_-]{2}.*")) {
	                         passwordScore += 20;
	                     } else if (userPassword.matches(".*[~!@#$%^&*()_-].*")) {
	                         passwordScore += 2;
	                     }
	
	                     if (passwordScore >= 82) {
	                         int amountIncrease = (int) ((double) (counter / accountList.getLength()) * 80);
	                         int newAmount = 20 + amountIncrease;
	                         progressBar.setValue(newAmount);
	                         percentLabel.setText(newAmount + "% Complete!");
	                         continue;
	                     } else if (passwordScore <= 65) {
	                         updates.append("\nThe password for '" + accountName + "' received a password score of "
	                                 + passwordScore + "/100, a low score. We recommend changing this password.\n--");
	                         updates.setCaretPosition(updates.getDocument().getLength());
	                     }
	                 } catch (Exception e) {
	                     e.printStackTrace();
	                 }
	
	                 try (BufferedReader reader = new BufferedReader(
	                         new FileReader(PasswordManager.rockYouFile))) {
	                     currentProcessLabel.setText(accountName + ": Checking rockyou.txt wordlist...");
	                     String line;
	                     String userPassword = AES.decrypt(password);
	                     while ((line = reader.readLine()) != null) {
	                         if (line.trim().equals(userPassword)) {
	                             updates.append("\n'" + accountName + "'"
	                                     + " was found to be using a common password! Hackers could figure it out in seconds.\n--");
	                             updates.setCaretPosition(updates.getDocument().getLength());
	                             break;
	                         }
	                     }
	                 } catch (IOException e) {
	                     e.printStackTrace();
	                 }
	
	                 int amountIncrease = (int) ((double) (counter / accountList.getLength()) * 80);
	                 int newAmount = 20 + amountIncrease;
	                 progressBar.setValue(newAmount);
	                 percentLabel.setText(newAmount + "% Complete!");
	
	             }
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	
	         currentProcessLabel.setText("Results are below.");
	         closeButton.setText("Close");
	
	         if (updates.getText().equals("")) {
	             updates.setText("Your passwords appear to be secure!");
	             progressBar.setValue(100);
	             percentLabel.setText("100% Complete!");
	         }
	     }
	 });
}
