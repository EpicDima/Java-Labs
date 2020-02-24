package storage.xml;

import banking.Client;
import banking.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class DomXmlPaymentSystem extends XmlPaymentSystem {
    private static final Logger logger = LoggerFactory.getLogger(DomXmlPaymentSystem.class);

    @Override
    protected void loadClients() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(FILENAME));
            NodeList clientNodes = document.getElementsByTagName(CLIENT_ELEMENT);
            for (int i = 0; i < clientNodes.getLength(); i++) {
                Node node = clientNodes.item(i);
                NamedNodeMap attributes = node.getAttributes();
                int id = Integer.parseInt(attributes.getNamedItem(ID_ATTRIBUTE).getNodeValue());
                String login = attributes.getNamedItem(LOGIN_ATTRIBUTE).getNodeValue();
                String password = attributes.getNamedItem(PASSWORD_ATTRIBUTE).getNodeValue();
                if (login == null || password == null) {
                    break;
                }
                Client client = new Client(id, login, password);
                NodeList cardsNodes = ((Element) node).getElementsByTagName(CREDIT_CARD_ELEMENT);
                for (int j = 0; j < cardsNodes.getLength(); j++) {
                    Node node2 = cardsNodes.item(j);
                    NamedNodeMap attributes2 = node2.getAttributes();
                    int id2 = Integer.parseInt(attributes2.getNamedItem(ID_ATTRIBUTE).getNodeValue());
                    long account = Long.parseLong(attributes2.getNamedItem(ACCOUNT_ATTRIBUTE).getNodeValue());
                    long balance = Long.parseLong(attributes2.getNamedItem(BALANCE_ATTRIBUTE).getNodeValue());
                    boolean freezed = Boolean.parseBoolean(attributes2.getNamedItem(FREEZED_ATTRIBUTE).getNodeValue());
                    client.addCreditCard(new CreditCard(id2, account, balance, freezed));
                }
                clients.add(client);
            }
        } catch (ParserConfigurationException | IOException | SAXException | NumberFormatException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void saveClients() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement(CLIENT_LIST_ELEMENT);
            document.appendChild(root);
            for (Client client : clients) {
                Element clientElement = document.createElement(CLIENT_ELEMENT);
                clientElement.setAttribute(ID_ATTRIBUTE, String.valueOf(client.getId()));
                clientElement.setAttribute(LOGIN_ATTRIBUTE, String.valueOf(client.getLogin()));
                clientElement.setAttribute(PASSWORD_ATTRIBUTE, String.valueOf(client.getPassword()));
                Element cardListElement = document.createElement(CREDIT_CARD_LIST_ELEMENT);
                for (CreditCard creditCard : client.getCreditCards()) {
                    Element cardElement = document.createElement(CREDIT_CARD_ELEMENT);
                    cardElement.setAttribute(ID_ATTRIBUTE, String.valueOf(creditCard.getId()));
                    cardElement.setAttribute(ACCOUNT_ATTRIBUTE, String.valueOf(creditCard.getAccount()));
                    cardElement.setAttribute(BALANCE_ATTRIBUTE, String.valueOf(creditCard.getBalance()));
                    cardElement.setAttribute(FREEZED_ATTRIBUTE, String.valueOf(creditCard.isFreezed()));
                    cardListElement.appendChild(cardElement);
                }
                clientElement.appendChild(cardListElement);
                root.appendChild(clientElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(FILENAME));
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException e) {
            logger.error(e.getMessage());
        }
    }
}
