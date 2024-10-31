package passwordmanager;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AddView {

    public static void Add(String selected) {
        try {
            Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new File(PasswordManager.databaseFile));

            NodeList accountList = database.getDocumentElement().getElementsByTagName("account");
            Element accountElement = null;
            for (int i = 0; i < accountList.getLength(); i++) {
                Element element = (Element) accountList.item(i);
                if (element.getAttribute("name").equals(selected)) {
                    accountElement = element;
                    break;
                }
            }

            if (accountElement != null) {
                Element usecountElement = (Element) accountElement.getElementsByTagName("usecount").item(0);
                String countString = usecountElement.getAttribute("count");
                int count = Integer.parseInt(countString) + 1;
                usecountElement.setAttribute("count", Integer.toString(count));
            }

            Transformer updates = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(database);
            StreamResult result = new StreamResult(PasswordManager.databaseFile);
            updates.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}