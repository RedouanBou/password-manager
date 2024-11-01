package passwordmanager;

import java.awt.Color;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.plaf.metal.MetalProgressBarUI;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class AddAccount extends javax.swing.JFrame {

     // GUI components
     private javax.swing.JLabel addAccountTitle;
     private javax.swing.JLabel confirmPasswordLabel;
     private javax.swing.JLabel renewalLabel;
     private javax.swing.JLabel notesLabel;
     private javax.swing.JLabel renewalOptionalLabel;
     private javax.swing.JLabel notesOptionalLabel;
     private javax.swing.JLabel accountNameLabel;
     private javax.swing.JLabel urlLabel;
     private javax.swing.JLabel urlOptionalLabel;
     private javax.swing.JLabel emailLabel;
     private javax.swing.JLabel emailOptionalLabel;
     private javax.swing.JLabel usernameLabel;
     private javax.swing.JLabel usernameOptionalLabel;
     private javax.swing.JLabel passwordLabel;
     private javax.swing.JPasswordField confirmPasswordField;
     private javax.swing.JPasswordField passwordField;
     private javax.swing.JProgressBar scoreBar;
     private javax.swing.JButton generateButton;
     private javax.swing.JButton submitButton;
     private javax.swing.JRadioButton renewal3MonthsButton;
     private javax.swing.JRadioButton renewal6MonthsButton;
     private javax.swing.JRadioButton renewal9MonthsButton;
     private javax.swing.JRadioButton renewal12MonthsButton;
     private javax.swing.JTextArea notesTextArea;
     private javax.swing.JTextField accountNameField;
     private javax.swing.JTextField urlField;
     private javax.swing.JTextField emailField;
     private javax.swing.JTextField usernameField;
     private javax.swing.JScrollPane notesTextAreaScrollPane;

     public AddAccount(String key) {

             if (!key.equals(PasswordManager.masterPassword)) {
                     System.out.println("Proper Authentication Not Provided!");
                     return;
             }

             setTitle("Add Account");
             setDefaultCloseOperation(DISPOSE_ON_CLOSE);
             setLayout(null);
             setVisible(true);
             setIconImage(new ImageIcon(PasswordManager.imageFile).getImage());

             initComponents();
             setLocationRelativeTo(null);
     }

     private int CheckStrength(String userPassword) {
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

             scoreBar.setValue(passwordScore);

             if (passwordScore >= 75) {
                     scoreBar.setForeground(Color.GREEN);
             } else if (passwordScore >= 45) {
                     scoreBar.setForeground(Color.ORANGE);
             } else {
                     scoreBar.setForeground(Color.RED);
             }

             return passwordScore;
     }

     private void initComponents() {
             addAccountTitle = new javax.swing.JLabel();
             accountNameField = new javax.swing.JTextField();
             accountNameLabel = new javax.swing.JLabel();
             urlField = new javax.swing.JTextField();
             urlLabel = new javax.swing.JLabel();
             urlOptionalLabel = new javax.swing.JLabel();
             emailLabel = new javax.swing.JLabel();
             emailOptionalLabel = new javax.swing.JLabel();
             emailField = new javax.swing.JTextField();
             usernameLabel = new javax.swing.JLabel();
             usernameOptionalLabel = new javax.swing.JLabel();
             usernameField = new javax.swing.JTextField();
             passwordLabel = new javax.swing.JLabel();
             confirmPasswordLabel = new javax.swing.JLabel();
             confirmPasswordField = new javax.swing.JPasswordField();
             passwordField = new javax.swing.JPasswordField();
             generateButton = new javax.swing.JButton();
             renewalLabel = new javax.swing.JLabel();
             renewal3MonthsButton = new javax.swing.JRadioButton();
             renewal6MonthsButton = new javax.swing.JRadioButton();
             renewal9MonthsButton = new javax.swing.JRadioButton();
             renewal12MonthsButton = new javax.swing.JRadioButton();
             notesLabel = new javax.swing.JLabel();
             notesTextAreaScrollPane = new javax.swing.JScrollPane();
             notesTextArea = new javax.swing.JTextArea();
             submitButton = new javax.swing.JButton();
             renewalOptionalLabel = new javax.swing.JLabel();
             notesOptionalLabel = new javax.swing.JLabel();
             scoreBar = new javax.swing.JProgressBar();

             scoreBar.setUI(new MetalProgressBarUI());
             scoreBar.setMinimum(0);
             scoreBar.setMaximum(100);

             addAccountTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
             addAccountTitle.setText("Add Account");

             accountNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
             accountNameLabel.setText("Account Name/Title");

             urlLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
             urlLabel.setText("URL");

             urlOptionalLabel.setFont(new java.awt.Font("Segoe UI", 0, 10));
             urlOptionalLabel.setText("optional");

             emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
             emailLabel.setText("Email");

             emailOptionalLabel.setFont(new java.awt.Font("Segoe UI", 0, 10));
             emailOptionalLabel.setText("optional");

             usernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
             usernameLabel.setText("Username");

             usernameOptionalLabel.setFont(new java.awt.Font("Segoe UI", 0, 10));
             usernameOptionalLabel.setText("optional");

             passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
             passwordLabel.setText("Password");

             confirmPasswordLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
             confirmPasswordLabel.setText("Confirm Password");

             passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
                     public void keyReleased(java.awt.event.KeyEvent evt) {
                             passwordFieldKeyReleased(evt);
                     }
             });

             generateButton.setText("Generate");
             generateButton.addMouseListener(new java.awt.event.MouseAdapter() {
                     public void mouseClicked(java.awt.event.MouseEvent evt) {
                             generateButtonClicked(evt);
                     }
             });

             submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
                     public void mouseClicked(java.awt.event.MouseEvent evt) {
                             submitButtonClicked(evt);
                     }
             });

             renewalLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
             renewalLabel.setText("Select Renewal Period");

             renewalOptionalLabel.setFont(new java.awt.Font("Segoe UI", 0, 10));
             renewalOptionalLabel.setText("optional");

             renewal3MonthsButton.setText("Every 3 months");
             renewal3MonthsButton.addActionListener(new java.awt.event.ActionListener() {
                     public void actionPerformed(java.awt.event.ActionEvent evt) {
                             renewal3MonthsButtonSelected(evt);
                     }
             });

             renewal6MonthsButton.setText("Every 6 months");
             renewal6MonthsButton.addActionListener(new java.awt.event.ActionListener() {
                     public void actionPerformed(java.awt.event.ActionEvent evt) {
                             renewal6MonthsButtonSelected(evt);
                     }
             });

             renewal9MonthsButton.setText("Every 9 months");
             renewal9MonthsButton.addActionListener(new java.awt.event.ActionListener() {
                     public void actionPerformed(java.awt.event.ActionEvent evt) {
                             renewal9MonthsButtonSelected(evt);
                     }
             });

             renewal12MonthsButton.setText("Every 12 months");
             renewal12MonthsButton.addActionListener(new java.awt.event.ActionListener() {
                     public void actionPerformed(java.awt.event.ActionEvent evt) {
                             renewal12MonthsButtonSelected(evt);
                     }
             });

             notesLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
             notesLabel.setText("Comments/Notes");

             notesOptionalLabel.setFont(new java.awt.Font("Segoe UI", 0, 10));
             notesOptionalLabel.setText("optional");

             notesTextArea.setColumns(20);
             notesTextArea.setRows(5);
             notesTextArea.setLineWrap(true);
             notesTextArea.setWrapStyleWord(true);
             notesTextAreaScrollPane.setViewportView(notesTextArea);

             submitButton.setText("Add Account");

             javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
             getContentPane().setLayout(layout);
             layout.setHorizontalGroup(
                             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                             .addGroup(layout.createSequentialGroup()
                                                             .addGap(19, 19, 19)
                                                             .addGroup(layout.createParallelGroup(
                                                                             javax.swing.GroupLayout.Alignment.LEADING)
                                                                             .addComponent(scoreBar,
                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                             227,
                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                             .addGroup(layout
                                                                                             .createParallelGroup(
                                                                                                             javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                             false)
                                                                                             .addGroup(layout.createSequentialGroup()
                                                                                                             .addComponent(notesLabel)
                                                                                                             .addPreferredGap(
                                                                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                             .addComponent(renewalOptionalLabel))
                                                                                             .addGroup(layout.createSequentialGroup()
                                                                                                             .addComponent(renewal9MonthsButton)
                                                                                                             .addGap(18, 18, 18)
                                                                                                             .addComponent(renewal12MonthsButton))
                                                                                             .addGroup(layout.createSequentialGroup()
                                                                                                             .addComponent(renewal3MonthsButton)
                                                                                                             .addGap(18, 18, 18)
                                                                                                             .addComponent(renewal6MonthsButton))
                                                                                             .addGroup(layout.createSequentialGroup()
                                                                                                             .addComponent(renewalLabel)
                                                                                                             .addPreferredGap(
                                                                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                             .addComponent(notesOptionalLabel))
                                                                                             .addComponent(confirmPasswordField,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                             334,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addComponent(usernameField,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                             334,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addComponent(accountNameField,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                             334,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addComponent(passwordLabel)
                                                                                             .addGroup(layout.createSequentialGroup()
                                                                                                             .addComponent(urlLabel)
                                                                                                             .addPreferredGap(
                                                                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                             .addComponent(urlOptionalLabel))
                                                                                             .addGroup(layout.createSequentialGroup()
                                                                                                             .addComponent(usernameLabel)
                                                                                                             .addPreferredGap(
                                                                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                             .addComponent(usernameOptionalLabel))
                                                                                             .addComponent(confirmPasswordLabel)
                                                                                             .addComponent(addAccountTitle)
                                                                                             .addComponent(accountNameLabel)
                                                                                             .addComponent(urlField,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                             334,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addGroup(layout.createSequentialGroup()
                                                                                                             .addComponent(emailLabel)
                                                                                                             .addPreferredGap(
                                                                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                             .addComponent(emailOptionalLabel))
                                                                                             .addComponent(emailField,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                             334,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                             .addGroup(layout.createSequentialGroup()
                                                                                                             .addComponent(passwordField,
                                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                             227,
                                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                             .addPreferredGap(
                                                                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                             .addComponent(generateButton,
                                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                             101,
                                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                             .addComponent(notesTextAreaScrollPane)
                                                                                             .addComponent(submitButton,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                             334,
                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                             .addContainerGap(24, Short.MAX_VALUE)));
             layout.setVerticalGroup(
                             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                             .addGroup(layout.createSequentialGroup()
                                                             .addGap(19, 19, 19)
                                                             .addComponent(addAccountTitle)
                                                             .addGap(25, 25, 25)
                                                             .addComponent(accountNameLabel)
                                                             .addPreferredGap(
                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                             .addComponent(accountNameField,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                             32,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addGap(18, 18, 18)
                                                             .addGroup(layout.createParallelGroup(
                                                                             javax.swing.GroupLayout.Alignment.BASELINE)
                                                                             .addComponent(urlLabel)
                                                                             .addComponent(urlOptionalLabel))
                                                             .addPreferredGap(
                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                             .addComponent(urlField,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                             32,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addGap(18, 18, 18)
                                                             .addGroup(layout.createParallelGroup(
                                                                             javax.swing.GroupLayout.Alignment.BASELINE)
                                                                             .addComponent(emailLabel)
                                                                             .addComponent(emailOptionalLabel))
                                                             .addPreferredGap(
                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                             .addComponent(emailField,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                             32,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addGap(18, 18, 18)
                                                             .addGroup(layout.createParallelGroup(
                                                                             javax.swing.GroupLayout.Alignment.BASELINE)
                                                                             .addComponent(usernameLabel)
                                                                             .addComponent(usernameOptionalLabel))
                                                             .addPreferredGap(
                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                             .addComponent(usernameField,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                             32,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addGap(15, 15, 15)
                                                             .addComponent(passwordLabel)
                                                             .addPreferredGap(
                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                             .addGroup(layout.createParallelGroup(
                                                                             javax.swing.GroupLayout.Alignment.LEADING,
                                                                             false)
                                                                             .addComponent(generateButton,
                                                                                             javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                             javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                             Short.MAX_VALUE)
                                                                             .addComponent(passwordField,
                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                             32,
                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE))
                                                             .addPreferredGap(
                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                             .addComponent(scoreBar,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                             8,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addGap(18, 18, 18)
                                                             .addComponent(confirmPasswordLabel)
                                                             .addPreferredGap(
                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                             .addComponent(confirmPasswordField,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                             32,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addGap(18, 18, 18)
                                                             .addGroup(layout.createParallelGroup(
                                                                             javax.swing.GroupLayout.Alignment.BASELINE)
                                                                             .addComponent(renewalLabel)
                                                                             .addComponent(notesOptionalLabel))
                                                             .addPreferredGap(
                                                                             javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                             .addGroup(layout.createParallelGroup(
                                                                             javax.swing.GroupLayout.Alignment.BASELINE)
                                                                             .addComponent(renewal3MonthsButton)
                                                                             .addComponent(renewal6MonthsButton))
                                                             .addPreferredGap(
                                                                             javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                             .addGroup(layout.createParallelGroup(
                                                                             javax.swing.GroupLayout.Alignment.BASELINE)
                                                                             .addComponent(renewal9MonthsButton)
                                                                             .addComponent(renewal12MonthsButton))
                                                             .addGap(18, 18, 18)
                                                             .addGroup(layout.createParallelGroup(
                                                                             javax.swing.GroupLayout.Alignment.BASELINE)
                                                                             .addComponent(notesLabel)
                                                                             .addComponent(renewalOptionalLabel))
                                                             .addPreferredGap(
                                                                             javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                             .addComponent(notesTextAreaScrollPane,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                             javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addGap(18, 18, 18)
                                                             .addComponent(submitButton,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                             43,
                                                                             javax.swing.GroupLayout.PREFERRED_SIZE)
                                                             .addContainerGap(19, Short.MAX_VALUE)));

             pack();
     }

     private void generateButtonClicked(java.awt.event.MouseEvent evt) {
             String generatedpw = GeneratePW.Generate(accountNameField.getText());
             if (generatedpw.length() == 0) {
                     return;
             }

             confirmPasswordField.setText(generatedpw);
             passwordField.setText(generatedpw);
             CheckStrength(generatedpw);

             generateButton.setText("Generated!");

             new java.util.Timer().schedule(
                             new java.util.TimerTask() {
                                     @Override
                                     public void run() {
                                             generateButton.setText("Generate");
                                     }
                             },
                             1500);
     }

     private void submitButtonClicked(java.awt.event.MouseEvent evt) {

             try {

                     File xmlFile = new File(PasswordManager.databaseFile);
                     Document database = DocumentBuilderFactory.newInstance()
                                     .newDocumentBuilder().parse(xmlFile);


                     NodeList accountList = database.getElementsByTagName("account");
                     for (int i = 0; i < accountList.getLength(); i++) {
                             Element account = (Element) accountList.item(i);

                             if (account.getAttribute("name").equals(accountNameField.getText())) {
                                     JOptionPane.showOptionDialog(null,
                                                     "An account with that name already exists. Please use a different name/title.",
                                                     "Error Adding Account",
                                                     JOptionPane.DEFAULT_OPTION,
                                                     JOptionPane.ERROR_MESSAGE, null,
                                                     new String[] { "OK" },
                                                     null);
                                     return;
                             }
                     }
             } catch (Exception e) {
                     e.printStackTrace();
             }


             if ((String.valueOf(passwordField.getPassword()))
                             .equals(String.valueOf(confirmPasswordField.getPassword()))) {

                     if (accountNameField.getText().length() > 0) {

                             if ((String.valueOf(confirmPasswordField.getPassword())).length() == 0) {
                                     JOptionPane.showOptionDialog(null,
                                                     "Password field may not be left blank.", "Error Adding Account",
                                                     JOptionPane.DEFAULT_OPTION,
                                                     JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" },
                                                     null);

                             } else {
                                     String newAccountTitle = accountNameField.getText();
                                     String newAccountURL = urlField.getText();
                                     String newAccountEmail = emailField.getText();
                                     String newAccountUsername = usernameField.getText();
                                     String newAccountPassword = String.valueOf(confirmPasswordField.getPassword());
                                     String newAccountRenewal = "0";

                                     if (renewal3MonthsButton.isSelected()) {
                                             newAccountRenewal = "3";
                                     } else if (renewal6MonthsButton.isSelected()) {
                                             newAccountRenewal = "6";
                                     } else if (renewal9MonthsButton.isSelected()) {
                                             newAccountRenewal = "9";
                                     } else if (renewal12MonthsButton.isSelected()) {
                                             newAccountRenewal = "12";
                                     }

                                     String newAccountNote = notesTextArea.getText();
                                     LocalDate dateCreated = LocalDate.now();
                                     LocalDate RenewalDate = dateCreated
                                                     .plus(Period.ofMonths(Integer.parseInt(newAccountRenewal)));

                                     try {
                                             DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                                             DocumentBuilder builder = factory.newDocumentBuilder();
                                             Document document = builder.parse(new File(
                                                             PasswordManager.databaseFile));

                                             Element rootElement = document.getDocumentElement();
                                             Element accountElement = document.createElement("account");
                                             accountElement.setAttribute("name", newAccountTitle);
                                             rootElement.appendChild(accountElement);

                                             Element idElement = document.createElement("id");
                                             idElement.setAttribute("id",
                                                             Integer.toString((int) Math
                                                                             .floor(Math.random() * (10000000))));
                                             accountElement.appendChild(idElement);

                                             Element urlElement = document.createElement("url");
                                             urlElement.setAttribute("link", AES.encrypt(newAccountURL));
                                             accountElement.appendChild(urlElement);

                                             Element emailElement = document.createElement("email");
                                             emailElement.setAttribute("address", AES.encrypt(newAccountEmail));
                                             accountElement.appendChild(emailElement);

                                             Element userElement = document.createElement("user");
                                             userElement.setAttribute("user", AES.encrypt(newAccountUsername));
                                             accountElement.appendChild(userElement);

                                             Element passElement = document.createElement("pass");
                                             passElement.setAttribute("pass", AES.encrypt(newAccountPassword));
                                             accountElement.appendChild(passElement);

                                             Element renewalElement = document.createElement("renewal");
                                             renewalElement.setAttribute("period", newAccountRenewal);
                                             accountElement.appendChild(renewalElement);

                                             Element noteElement = document.createElement("note");
                                             noteElement.setAttribute("note", AES.encrypt(newAccountNote));
                                             accountElement.appendChild(noteElement);

                                             Element creationElement = document.createElement("creation");
                                             creationElement.setAttribute("date", dateCreated.toString());
                                             accountElement.appendChild(creationElement);

                                             Element dateElement = document.createElement("date");
                                             dateElement.setAttribute("renewal", RenewalDate.toString());
                                             accountElement.appendChild(dateElement);

                                             Element usecountElement = document.createElement("usecount");
                                             usecountElement.setAttribute("count", "0");
                                             accountElement.appendChild(usecountElement);

                                             DOMSource source = new DOMSource(document);
                                             StreamResult result = new StreamResult(
                                                             new File(PasswordManager.databaseFile));

                                             TransformerFactory.newInstance().newTransformer().transform(source,
                                                             result);

                                             dispose();

                                     } catch (Exception e) {
                                             e.printStackTrace();
                                     }
                             }
                     } else {
                             JOptionPane.showOptionDialog(null,
                                             "An account name/title is required.", "Error Adding Account",
                                             JOptionPane.DEFAULT_OPTION,
                                             JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
                     }
             } else {
                     JOptionPane.showOptionDialog(null,
                                     "The entered passwords do not match! Please try again.", "Error Adding Account",
                                     JOptionPane.DEFAULT_OPTION,
                                     JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
             }
     }

     private void passwordFieldKeyReleased(java.awt.event.KeyEvent evt) {
             CheckStrength(String.valueOf(passwordField.getPassword()));
     }

     private void renewal3MonthsButtonSelected(java.awt.event.ActionEvent evt) {
             deselectAllRenewalButtonsExcept(renewal3MonthsButton);
     }

     private void renewal6MonthsButtonSelected(java.awt.event.ActionEvent evt) {
             deselectAllRenewalButtonsExcept(renewal6MonthsButton);
     }

     private void renewal9MonthsButtonSelected(java.awt.event.ActionEvent evt) {
             deselectAllRenewalButtonsExcept(renewal9MonthsButton);
     }

     private void renewal12MonthsButtonSelected(java.awt.event.ActionEvent evt) {
             deselectAllRenewalButtonsExcept(renewal12MonthsButton);
     }

     private void deselectAllRenewalButtonsExcept(javax.swing.JRadioButton selectedButton) {
             javax.swing.JRadioButton[] renewalButtons = { renewal3MonthsButton, renewal6MonthsButton,
                             renewal9MonthsButton, renewal12MonthsButton };

             for (javax.swing.JRadioButton renewalButton : renewalButtons) {
                     if (renewalButton != selectedButton) {
                             renewalButton.setSelected(false);
                     }
             }
     }
}
