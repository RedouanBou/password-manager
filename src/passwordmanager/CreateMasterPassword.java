package passwordmanager;

import java.awt.Color;
import java.io.PrintWriter;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.plaf.metal.MetalProgressBarUI;

import javax.swing.JFrame;

public class CreateMasterPassword extends JFrame {
	private javax.swing.JButton updatePasswordButton;
    private javax.swing.JLabel masterPasswordLabel;
    private javax.swing.JLabel confirmLabel;
    private javax.swing.JLabel warningLabel1;
    private javax.swing.JLabel warningLabel2;
    private javax.swing.JLabel newLabel;
    private javax.swing.JPasswordField confirmPasswordField;
    private javax.swing.JPasswordField newPasswordField;
    private javax.swing.JProgressBar scoreBar;

    public CreateMasterPassword() {
        setTitle("Create Master Password");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setIconImage(new ImageIcon(PasswordManager.imageFile).getImage());

        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        scoreBar = new javax.swing.JProgressBar();
        newLabel = new javax.swing.JLabel();
        confirmLabel = new javax.swing.JLabel();
        masterPasswordLabel = new javax.swing.JLabel();
        warningLabel1 = new javax.swing.JLabel();
        warningLabel2 = new javax.swing.JLabel();
        updatePasswordButton = new javax.swing.JButton();
        confirmPasswordField = new javax.swing.JPasswordField();
        newPasswordField = new javax.swing.JPasswordField();

        scoreBar.setUI(new MetalProgressBarUI());
        scoreBar.setMinimum(0);
        scoreBar.setMaximum(100);

        masterPasswordLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
        masterPasswordLabel.setText("Create Master Password");

        newLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        newLabel.setText("New Password");
        newPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                newPasswordFieldKeyReleased(evt);
            }
        });

        confirmLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        confirmLabel.setText("Confirm New Password");

        warningLabel1.setText("If you forget this password, you will lose access to your accounts.");
        warningLabel2.setText("Choose a secure password that you will remember.");

        updatePasswordButton.setText("Update Password");
        updatePasswordButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updatePasswordButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scoreBar, javax.swing.GroupLayout.PREFERRED_SIZE, 334,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(masterPasswordLabel)
                                        .addComponent(warningLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 345,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(warningLabel2)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(updatePasswordButton,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 334,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(confirmPasswordField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                334,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(newLabel)
                                                        .addComponent(confirmLabel)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(newPasswordField,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 334,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(masterPasswordLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(warningLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(warningLabel2)
                                .addGap(30, 30, 30)
                                .addComponent(newLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(newPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scoreBar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(confirmLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(confirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(updatePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(27, Short.MAX_VALUE)));
        pack();
    }

    private int CheckStrength(String userPassword) {
        int passwordScore = 0;

        for (int p = 0; p < userPassword.length(); p++) {
            if (passwordScore > 48) { 
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

    private void updatePasswordButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if ((String.valueOf(newPasswordField.getPassword()))
                .equals(String.valueOf(confirmPasswordField.getPassword()))) {
            if ((String.valueOf(confirmPasswordField.getPassword())).length() == 0) {
                JOptionPane.showOptionDialog(null,
                        "Password field may not be left blank.", "Error Adding Password",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" },
                        null);
            } else {
                String newMasterPassword = String.valueOf(confirmPasswordField.getPassword());

                try {
                    String pw_hash = passwordmanager.org.rb.jbcrypt.BCrypt.hashpw(newMasterPassword,
                    		 passwordmanager.org.rb.jbcrypt.BCrypt.gensalt());

                    PrintWriter masterPasswordFile = new PrintWriter(PasswordManager.masterPasswordFile);
                    masterPasswordFile.println(pw_hash);
                    masterPasswordFile.close();

                    dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showOptionDialog(null,
                    "The entered passwords do not match! Please try again.", "Error Updating Password",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
        }
    }

    private void newPasswordFieldKeyReleased(java.awt.event.KeyEvent evt) {
        CheckStrength(String.valueOf(newPasswordField.getPassword()));
    }
}
