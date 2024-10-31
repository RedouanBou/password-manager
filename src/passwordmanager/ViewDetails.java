package passwordmanager;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ViewDetails extends javax.swing.JFrame {
	
	private javax.swing.JButton openURLButton;
    private javax.swing.JButton showButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton copyEmailButton;
    private javax.swing.JButton copyUsernameButton;
    private javax.swing.JButton copyPasswordButton;
    private javax.swing.JButton copyNotesButton;
    private javax.swing.JLabel accountNameLabel;
    private javax.swing.JLabel notesLabel;
    private javax.swing.JLabel creationLabel;
    private javax.swing.JLabel renewalPeriodLabel;
    private javax.swing.JLabel renewalDeadlineLabel;
    private javax.swing.JLabel renewalStatusLabel;
    private javax.swing.JLabel urlLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPanel expirationPanel;
    private javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane notesScrollPane;
    private javax.swing.JTextArea notesTextArea;
    private javax.swing.JTextField urlField;
    private javax.swing.JTextField usernameField;
    private javax.swing.JTextField passwordField;
    private javax.swing.JTextField emailField;


    public ViewDetails(String selected, String key) {

            if (!key.equals(PasswordManager.masterPassword)) {
                    System.out.println("Proper Authentication Not Provided!");
                    return;
            }

            if (selected == null) {
                    JOptionPane.showOptionDialog(null,
                                    "You need to select an account to perform that action.", "No Account Selected!",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
                    return; 
            }

            setTitle("View Details");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(null);
            setVisible(true);
            setIconImage(new ImageIcon(PasswordManager.imageFile).getImage());

            initComponents(selected);
            setLocationRelativeTo(null);
            AddView.Add(selected);
    }

    private void initComponents(String selected) {

            String url = null;
            String email = null;
            String username = null;
            String password = null;
            String notes = null;
            String creation = null;
            String renewalPeriod = null;
            String renewalDate = null;

            try {
                    File xmlFile = new File(PasswordManager.databaseFile);
                    Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

                    NodeList elements = database.getDocumentElement().getElementsByTagName("account");

                    for (int i = 0; i < elements.getLength(); i++) {
                            Element accountElements = (Element) elements.item(i);
                            String name = accountElements.getAttribute("name");

                            if (name.equals(selected)) {
                                    Element urlElement = (Element) accountElements.getElementsByTagName("url")
                                                    .item(0);
                                    url = AES.decrypt(urlElement.getAttribute("link"));

                                    Element emailElement = (Element) accountElements.getElementsByTagName("email")
                                                    .item(0);
                                    email = AES.decrypt(emailElement.getAttribute("address"));

                                    Element usernameElement = (Element) accountElements.getElementsByTagName("user")
                                                    .item(0);
                                    username = AES.decrypt(usernameElement.getAttribute("user"));

                                    Element passwordElement = (Element) accountElements.getElementsByTagName("pass")
                                                    .item(0);
                                    password = AES.decrypt(passwordElement.getAttribute("pass"));

                                    Element notesElement = (Element) accountElements.getElementsByTagName("note")
                                                    .item(0);
                                    notes = AES.decrypt(notesElement.getAttribute("note"));

                                    Element creationElement = (Element) accountElements
                                                    .getElementsByTagName("creation")
                                                    .item(0);
                                    creation = creationElement.getAttribute("date");

                                    Element periodElement = (Element) accountElements
                                                    .getElementsByTagName("renewal")
                                                    .item(0);
                                    renewalPeriod = periodElement.getAttribute("period");

                                    Element deadlineElement = (Element) accountElements.getElementsByTagName("date")
                                                    .item(0);
                                    renewalDate = deadlineElement.getAttribute("renewal");

                                    break; 
                            }
                    }
            } catch (Exception e) {
                    e.printStackTrace();
            }

            jPanel = new javax.swing.JPanel();
            accountNameLabel = new javax.swing.JLabel();
            urlField = new javax.swing.JTextField();
            openURLButton = new javax.swing.JButton();
            usernameField = new javax.swing.JTextField();
            copyUsernameButton = new javax.swing.JButton();
            passwordField = new javax.swing.JTextField();
            copyPasswordButton = new javax.swing.JButton();
            showButton = new javax.swing.JButton();
            editButton = new javax.swing.JButton();
            copyNotesButton = new javax.swing.JButton();
            notesScrollPane = new javax.swing.JScrollPane();
            notesTextArea = new javax.swing.JTextArea();
            creationLabel = new javax.swing.JLabel();
            emailField = new javax.swing.JTextField();
            copyEmailButton = new javax.swing.JButton();
            expirationPanel = new javax.swing.JPanel();
            renewalPeriodLabel = new javax.swing.JLabel();
            renewalDeadlineLabel = new javax.swing.JLabel();
            renewalStatusLabel = new javax.swing.JLabel();
            urlLabel = new javax.swing.JLabel();
            emailLabel = new javax.swing.JLabel();
            usernameLabel = new javax.swing.JLabel();
            passwordLabel = new javax.swing.JLabel();
            notesLabel = new javax.swing.JLabel();

            if (renewalPeriod.equals("0")) {
                    expirationPanel.setVisible(false);
            }

            accountNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
            accountNameLabel.setText(selected);

            urlField.setEditable(false);
            urlField.setText(url);
            openURLButton.setText("Open");
            openURLButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                            openURLButtonMouseClicked(evt);
                    }
            });

            usernameField.setEditable(false);
            usernameField.setText(username);
            copyUsernameButton.setText("Copy");
            copyUsernameButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                            copyUsernameButtonMouseClicked(evt);
                    }
            });

            passwordField.setEditable(false);
            String asterisks = "*".repeat(password.length());
            passwordField.setText(asterisks);
            String sendPassword = password;
            showButton.setText("Show");
            showButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                            showButtonMouseClicked(sendPassword, evt);
                    }
            });
            copyPasswordButton.setText("Copy");
            copyPasswordButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                            copyPasswordButtonMouseClicked(sendPassword, evt);
                    }
            });

            notesTextArea.setEditable(false);
            notesTextArea.setLineWrap(true);
            notesTextArea.setWrapStyleWord(true);
            notesTextArea.setForeground(new java.awt.Color(66, 66, 66));
            notesTextArea.setColumns(20);
            notesTextArea.setRows(5);
            notesTextArea.setText(notes);
            notesScrollPane.setViewportView(notesTextArea);
            copyNotesButton.setText("Copy");
            copyNotesButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                            copyNotesButtonMouseClicked(evt);
                    }
            });
            editButton.setText("Edit");
            editButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                            editButtonMouseClicked(selected, evt);
                    }
            });

            emailField.setEditable(false);
            emailField.setText(email);
            copyEmailButton.setText("Copy");
            copyEmailButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                            copyEmailButtonMouseClicked(evt);
                    }
            });

            expirationPanel.setBackground(new java.awt.Color(255, 255, 255));

            creationLabel.setForeground(new java.awt.Color(102, 102, 102));
            creationLabel.setText("Account created on " + creation);
            renewalPeriodLabel.setFont(new java.awt.Font("sansserif", 1, 12));
            renewalPeriodLabel.setText("This account is set to renew every " + renewalPeriod + " months.");
            renewalDeadlineLabel.setText("The current renewal deadline is " + renewalDate + ".");
            renewalStatusLabel.setFont(new java.awt.Font("sansserif", 1, 12));
            urlLabel.setFont(new java.awt.Font("sansserif", 1, 12));
            urlLabel.setText("URL");
            emailLabel.setFont(new java.awt.Font("sansserif", 1, 12));
            emailLabel.setText("Email");
            usernameLabel.setFont(new java.awt.Font("sansserif", 1, 12));
            usernameLabel.setText("Username");
            passwordLabel.setFont(new java.awt.Font("sansserif", 1, 12));
            passwordLabel.setText("Password");
            notesLabel.setFont(new java.awt.Font("sansserif", 1, 12));
            notesLabel.setText("Notes");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate DateRenew = LocalDate.parse(renewalDate, formatter);
            LocalDate DateNow = LocalDate.now();

            long daysbetween = ChronoUnit.DAYS.between(DateNow, DateRenew);

            if (DateNow.isAfter(DateRenew)) {
                    renewalStatusLabel.setForeground(new java.awt.Color(255, 0, 0));
                    renewalStatusLabel.setText("You have passed this deadline. Please reset your password.");
            } else {
                    if (daysbetween <= 7) {
                            renewalStatusLabel.setForeground(new java.awt.Color(255, 140, 0));
                            renewalStatusLabel.setText("It's time to update your password.");
                    } else {

                            renewalStatusLabel.setForeground(new java.awt.Color(15, 74, 7));
                            renewalStatusLabel.setText("This deadline is more than a week away.");
                    }
            }

            javax.swing.GroupLayout expirationPanelLayout = new javax.swing.GroupLayout(expirationPanel);
            expirationPanel.setLayout(expirationPanelLayout);
            expirationPanelLayout.setHorizontalGroup(
                            expirationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(expirationPanelLayout.createSequentialGroup()
                                                            .addGap(23, 23, 23)
                                                            .addGroup(expirationPanelLayout.createParallelGroup(
                                                                            javax.swing.GroupLayout.Alignment.LEADING)
                                                                            .addComponent(renewalStatusLabel)
                                                                            .addComponent(renewalDeadlineLabel)
                                                                            .addComponent(renewalPeriodLabel))
                                                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                            Short.MAX_VALUE)));
            expirationPanelLayout.setVerticalGroup(
                            expirationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(expirationPanelLayout.createSequentialGroup()
                                                            .addGap(17, 17, 17)
                                                            .addComponent(renewalPeriodLabel)
                                                            .addPreferredGap(
                                                                            javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addComponent(renewalDeadlineLabel)
                                                            .addPreferredGap(
                                                                            javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addComponent(renewalStatusLabel)
                                                            .addContainerGap(19, Short.MAX_VALUE)));

            javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
            jPanel.setLayout(jPanelLayout);
            jPanelLayout.setHorizontalGroup(
                            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanelLayout.createSequentialGroup()
                                                            .addGap(20, 20, 20)
                                                            .addGroup(jPanelLayout.createParallelGroup(
                                                                            javax.swing.GroupLayout.Alignment.LEADING)
                                                                            .addGroup(jPanelLayout
                                                                                            .createParallelGroup(
                                                                                                            javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                            false)
                                                                                            .addGroup(jPanelLayout
                                                                                                            .createParallelGroup(
                                                                                                                            javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                            .addGroup(jPanelLayout
                                                                                                                            .createSequentialGroup()
                                                                                                                            .addComponent(creationLabel)
                                                                                                                            .addContainerGap(
                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                            Short.MAX_VALUE))
                                                                                                            .addGroup(jPanelLayout
                                                                                                                            .createSequentialGroup()
                                                                                                                            .addGroup(jPanelLayout
                                                                                                                                            .createParallelGroup(
                                                                                                                                                            javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                            .addComponent(expirationPanel,
                                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                            Short.MAX_VALUE)
                                                                                                                                            .addGroup(jPanelLayout
                                                                                                                                                            .createSequentialGroup()
                                                                                                                                                            .addComponent(notesScrollPane,
                                                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                            335,
                                                                                                                                                                            Short.MAX_VALUE)
                                                                                                                                                            .addPreferredGap(
                                                                                                                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                            .addGroup(jPanelLayout
                                                                                                                                                                            .createParallelGroup(
                                                                                                                                                                                            javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                                                            .addComponent(editButton,
                                                                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                            120,
                                                                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                            .addGap(26, 26, 26) ///
                                                                                                                                                                            .addComponent(copyNotesButton,
                                                                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                            120,
                                                                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                                                                            .addGap(31, 31, 31)))
                                                                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                            jPanelLayout
                                                                                                                            .createSequentialGroup()
                                                                                                                            .addComponent(passwordField,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                            209,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                            .addPreferredGap(
                                                                                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                            .addComponent(showButton,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                            120,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                            .addPreferredGap(
                                                                                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                            .addComponent(copyPasswordButton,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                            120,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                            .addGroup(jPanelLayout
                                                                                            .createSequentialGroup()
                                                                                            .addGroup(jPanelLayout
                                                                                                            .createParallelGroup(
                                                                                                                            javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                            .addComponent(notesLabel)
                                                                                                            .addComponent(passwordLabel)
                                                                                                            .addComponent(usernameLabel)
                                                                                                            .addComponent(emailLabel)
                                                                                                            .addComponent(urlLabel)
                                                                                                            .addGroup(jPanelLayout
                                                                                                                            .createSequentialGroup()
                                                                                                                            .addComponent(emailField,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                            335,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                            .addPreferredGap(
                                                                                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                            .addComponent(copyEmailButton,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                            120,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                            .addGroup(jPanelLayout
                                                                                                                            .createSequentialGroup()
                                                                                                                            .addComponent(usernameField,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                            335,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                            .addPreferredGap(
                                                                                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                            .addComponent(copyUsernameButton,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                            120,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                            .addComponent(accountNameLabel)
                                                                                                            .addGroup(jPanelLayout
                                                                                                                            .createSequentialGroup()
                                                                                                                            .addComponent(urlField,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                            335,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                            .addPreferredGap(
                                                                                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                            .addComponent(openURLButton,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                            120,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                            .addContainerGap(31,
                                                                                                            Short.MAX_VALUE)))));
            jPanelLayout.setVerticalGroup(
                            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanelLayout.createSequentialGroup()
                                                            .addGap(23, 23, 23)
                                                            .addComponent(accountNameLabel)
                                                            .addGap(26, 26, 26)
                                                            .addComponent(urlLabel)
                                                            .addPreferredGap(
                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(jPanelLayout
                                                                            .createParallelGroup(
                                                                                            javax.swing.GroupLayout.Alignment.LEADING,
                                                                                            false)
                                                                            .addComponent(openURLButton,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            Short.MAX_VALUE)
                                                                            .addComponent(urlField,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                            30,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGap(18, 18, 18)
                                                            .addComponent(emailLabel)
                                                            .addPreferredGap(
                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(jPanelLayout
                                                                            .createParallelGroup(
                                                                                            javax.swing.GroupLayout.Alignment.LEADING,
                                                                                            false)
                                                                            .addComponent(copyEmailButton,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            Short.MAX_VALUE)
                                                                            .addComponent(emailField,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                            30,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGap(18, 18, 18)
                                                            .addComponent(usernameLabel)
                                                            .addPreferredGap(
                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(jPanelLayout
                                                                            .createParallelGroup(
                                                                                            javax.swing.GroupLayout.Alignment.LEADING,
                                                                                            false)
                                                                            .addComponent(copyUsernameButton,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            Short.MAX_VALUE)
                                                                            .addComponent(usernameField,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                            30,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGap(18, 18, 18)
                                                            .addComponent(passwordLabel)
                                                            .addPreferredGap(
                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(jPanelLayout
                                                                            .createParallelGroup(
                                                                                            javax.swing.GroupLayout.Alignment.LEADING,
                                                                                            false)
                                                                            .addComponent(showButton,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            Short.MAX_VALUE)
                                                                            .addComponent(copyPasswordButton,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            Short.MAX_VALUE)
                                                                            .addComponent(passwordField,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                            30,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGap(18, 18, 18)

                                                            .addComponent(notesLabel)
                                                            .addPreferredGap(
                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(jPanelLayout
                                                                            .createParallelGroup(
                                                                                            javax.swing.GroupLayout.Alignment.LEADING,
                                                                                            false)
                                                                            .addGroup(jPanelLayout
                                                                                            .createSequentialGroup()
                                                                                            .addGroup(jPanelLayout
                                                                                                            .createParallelGroup(
                                                                                                                            javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                            .addComponent(editButton,
                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                            30,
                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                            .addGroup(jPanelLayout
                                                                                                                            .createSequentialGroup()
                                                                                                                            .addGap(35)
                                                                                                                            .addComponent(copyNotesButton,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                            30,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                            .addPreferredGap(
                                                                                                            javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                                            .addComponent(notesScrollPane,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                            105,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGap(18, 18, 18)

                                                            .addComponent(creationLabel)
                                                            .addGap(18, 18, 18)
                                                            .addComponent(expirationPanel,
                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addContainerGap(32, Short.MAX_VALUE)));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGap(0, 518, Short.MAX_VALUE)
                                            .addGroup(layout.createParallelGroup(
                                                            javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                            .addGap(0, 0, Short.MAX_VALUE)
                                                                            .addComponent(jPanel,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                            .addGap(0, 0, Short.MAX_VALUE))));
            layout.setVerticalGroup(
                            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGap(0, 668, Short.MAX_VALUE)
                                            .addGroup(layout.createParallelGroup(
                                                            javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                            .addGap(0, 0, Short.MAX_VALUE)
                                                                            .addComponent(jPanel,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                            .addGap(0, 0, Short.MAX_VALUE))));

            pack();
    }

    private void openURLButtonMouseClicked(java.awt.event.MouseEvent evt) {
            if (urlField.getText().length() == 0) {
                    JOptionPane.showOptionDialog(null,
                                    "Website not found!", "No Url Provided",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
                    return;
            }

            try {
                    if (urlField.getText().startsWith("https://")
                                    || urlField.getText().startsWith("http://")) {
                            Desktop.getDesktop().browse(URI.create(urlField.getText()));
                    } else {
                            Desktop.getDesktop().browse(URI.create("https://" + urlField.getText()));
                    }
            } catch (Exception e) {
                    e.printStackTrace();
            }
    }

    private void copyUsernameButtonMouseClicked(java.awt.event.MouseEvent evt) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(usernameField.getText()), null);

            copyUsernameButton.setText("Copied!");
            new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                            copyUsernameButton.setText("Copy");
                                    }
                            },
                            1500);
    }

    private void copyPasswordButtonMouseClicked(String password, java.awt.event.MouseEvent evt) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(password), null);

            copyPasswordButton.setText("Copied!");
            new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                            copyPasswordButton.setText("Copy");
                                    }
                            },
                            1500);
    }

    private void showButtonMouseClicked(String password, java.awt.event.MouseEvent evt) {
            if (showButton.getText().equals("Show")) {
                    passwordField.setText(password);
                    showButton.setText("Hide");
            } else {
                    String asterisks = "*".repeat(password.length());
                    passwordField.setText(asterisks);
                    showButton.setText("Show");
            }
    }

    private void copyNotesButtonMouseClicked(java.awt.event.MouseEvent evt) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(notesTextArea.getText()), null);

            copyNotesButton.setText("Copied!");

            new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                            copyNotesButton.setText("Copy");
                                    }
                            },
                            1500);
    }

    private void copyEmailButtonMouseClicked(java.awt.event.MouseEvent evt) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(emailField.getText()), null);

            copyEmailButton.setText("Copied!");

            new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                    @Override
                                    public void run() {
                                            copyEmailButton.setText("Copy");
                                    }
                            },
                            1500);
    }

    private void editButtonMouseClicked(String selected, java.awt.event.MouseEvent evt) {
            if (editButton.getText().equals("Edit")) {
                    notesTextArea.setEditable(true);
                    notesTextArea.setForeground(new java.awt.Color(0, 0, 0));
                    notesTextArea.setFocusable(true);
                    notesTextArea.requestFocus();
                    editButton.setText("Update");
            } else {
                    notesTextArea.setEditable(false);
                    notesTextArea.setFocusable(false);
                    try {
                            File xmlFile = new File(PasswordManager.databaseFile);
                            Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                                            .parse(xmlFile);
                            Element root = database.getDocumentElement();
                            NodeList accountList = root.getElementsByTagName("account");
                            Element accountElement = null;

                            for (int i = 0; i < accountList.getLength(); i++) {
                                    Element element = (Element) accountList.item(i);
                                    if (element.getAttribute("name").equals(selected)) {
                                            accountElement = element;
                                            break;
                                    }
                            }

                            Element noteElement = (Element) accountElement.getElementsByTagName("note").item(0);
                            noteElement.setAttribute("note", AES.encrypt(notesTextArea.getText()));

                            Transformer newDatabase = TransformerFactory.newInstance().newTransformer();
                            DOMSource source = new DOMSource(database);
                            StreamResult result = new StreamResult(xmlFile);
                            newDatabase.transform(source, result);
                            editButton.setText("Updated!");

                            new java.util.Timer().schedule(
                                            new java.util.TimerTask() {
                                                    @Override
                                                    public void run() {
                                                            editButton.setText("Edit");
                                                            notesTextArea.setFocusable(false);
                                                    }
                                            },
                                            1500);
                            notesTextArea.setForeground(new java.awt.Color(66, 66, 66));
                    } catch (Exception e) {
                            e.printStackTrace();
                            editButton.setText("ERROR");
                    }
            }
    }

}
