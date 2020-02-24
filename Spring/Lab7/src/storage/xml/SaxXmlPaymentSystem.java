package storage.xml;

import banking.Client;
import banking.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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

public class SaxXmlPaymentSystem extends XmlPaymentSystem {
    private static final Logger logger = LoggerFactory.getLogger(SaxXmlPaymentSystem.class);

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
            writer.writeStartElement(CLIENT_LIST_ELEMENT);
            for (Client client : clients) {
                writer.writeStartElement(CLIENT_ELEMENT);
                writer.writeAttribute(ID_ATTRIBUTE, String.valueOf(client.getId()));
                writer.writeAttribute(LOGIN_ATTRIBUTE, client.getLogin());
                writer.writeAttribute(PASSWORD_ATTRIBUTE, client.getPassword());
                writer.writeStartElement(CREDIT_CARD_LIST_ELEMENT);
                for (CreditCard creditCard : client.getCreditCards()) {
                    writer.writeStartElement(CREDIT_CARD_ELEMENT);
                    writer.writeAttribute(ID_ATTRIBUTE, String.valueOf(creditCard.getId()));
                    writer.writeAttribute(ACCOUNT_ATTRIBUTE, String.valueOf(creditCard.getAccount()));
                    writer.writeAttribute(BALANCE_ATTRIBUTE, String.valueOf(creditCard.getBalance()));
                    writer.writeAttribute(FREEZED_ATTRIBUTE, String.valueOf(creditCard.isFreezed()));
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
                case CLIENT_ELEMENT:
                    try {
                        int id = Integer.parseInt(attributes.getValue(ID_ATTRIBUTE));
                        String login = attributes.getValue(LOGIN_ATTRIBUTE);
                        String password = attributes.getValue(PASSWORD_ATTRIBUTE);
                        if (login == null || password == null) {
                            return;
                        }
                        client = new Client(id, login, password);
                    } catch (NumberFormatException e) {
                        logger.warn(e.getMessage());
                    }
                    break;
                case CREDIT_CARD_ELEMENT:
                    try {
                        int id = Integer.parseInt(attributes.getValue(ID_ATTRIBUTE));
                        long account = Long.parseLong(attributes.getValue(ACCOUNT_ATTRIBUTE));
                        long balance = Long.parseLong(attributes.getValue(BALANCE_ATTRIBUTE));
                        boolean freezed = Boolean.parseBoolean(attributes.getValue(FREEZED_ATTRIBUTE));
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
            if (CLIENT_ELEMENT.equals(qName)) {
                if (client != null) {
                    clients.add(client);
                    client = null;
                }
            }
        }
    }
}
