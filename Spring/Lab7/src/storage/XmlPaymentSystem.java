package storage;

import banking.Client;
import banking.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import storage.base.AbstractPaymentSystem;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class XmlPaymentSystem extends AbstractPaymentSystem {

    private static final String CLIENT_LIST_NAME = "clients";
    private static final String CLIENT_NAME = "client";
    private static final String CREDIT_CARD_LIST_NAME = "creditCards";
    private static final String CREDIT_CARD_NAME = "creditCard";

    private static final Logger logger = LoggerFactory.getLogger(XmlPaymentSystem.class);

    private static final String FILENAME = "storage.xml";

    @Override
    protected void loadClients() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            XmlHandler handler = new XmlHandler();
            parser.parse(new File(FILENAME), handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void saveClients() {
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = null;
        try {
            writer = xmlOutputFactory.createXMLStreamWriter(new FileOutputStream(FILENAME));
            writer.writeStartDocument();
            writer.writeStartElement(CLIENT_LIST_NAME);
            for (Client client : clients) {
                writer.writeStartElement(CLIENT_NAME);
                writer.writeAttribute("id", String.valueOf(client.getId()));
                writer.writeAttribute("login", client.getLogin());
                writer.writeAttribute("password", client.getPassword());
                writer.writeStartElement(CREDIT_CARD_LIST_NAME);
                for (CreditCard creditCard : client.getCreditCards()) {
                    writer.writeStartElement(CREDIT_CARD_NAME);
                    writer.writeAttribute("id", String.valueOf(creditCard.getId()));
                    writer.writeAttribute("account", String.valueOf(creditCard.getAccount()));
                    writer.writeAttribute("balance", String.valueOf(creditCard.getBalance()));
                    writer.writeAttribute("freezed", String.valueOf(creditCard.isFreezed()));
                    writer.writeEndElement();
                }
                writer.writeEndElement();
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
        } catch (FileNotFoundException | XMLStreamException e) {
            logger.error(e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (XMLStreamException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }


    private class XmlHandler extends DefaultHandler {

        private Client client = null;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            switch (qName) {
                case CLIENT_NAME:
                    try {
                        int id = Integer.parseInt(attributes.getValue("id"));
                        String login = attributes.getValue("login");
                        String password = attributes.getValue("password");
                        if (login == null || password == null) {
                            return;
                        }
                        client = new Client(id, login, password);
                    } catch (NumberFormatException e) {
                        logger.warn(e.getMessage());
                    }
                    break;
                case CREDIT_CARD_NAME:
                    try {
                        int id = Integer.parseInt(attributes.getValue("id"));
                        long account = Long.parseLong(attributes.getValue("account"));
                        long balance = Long.parseLong(attributes.getValue("balance"));
                        boolean freezed = Boolean.parseBoolean(attributes.getValue("freezed"));
                        if (client != null) {
                            client.addCreditCard(new CreditCard(id, account, balance, freezed));
                        }
                    } catch (NumberFormatException e) {
                        logger.warn(e.getMessage());
                    }
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (CLIENT_NAME.equals(qName)) {
                if (client != null) {
                    clients.add(client);
                    client = null;
                }
            }
        }
    }
}
