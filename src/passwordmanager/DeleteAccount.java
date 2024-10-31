package passwordmanager;

import java.io.File;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DeleteAccount {
    static void Delete(String selected, String key) {

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

        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure want to delete '" + selected + "'?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirmation != JOptionPane.YES_OPTION) {
            return; 
        }

        try {
            File xmlFile = new File(PasswordManager.databaseFile);
            Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

            NodeList accountList = database.getElementsByTagName("account");
            for (int i = 0; i < accountList.getLength(); i++) {
                Element account = (Element) accountList.item(i);
                if (account.getAttribute("name").equals(selected)) {
                    account.getParentNode().removeChild(account);
                    break; 
                }
            }

            TransformerFactory fileWrite = TransformerFactory.newInstance();
            Transformer newDatabase = fileWrite.newTransformer();
            newDatabase.transform(new DOMSource(database),
                    new StreamResult(new File(PasswordManager.databaseFile)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}