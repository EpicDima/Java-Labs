package storage.xml;

import banking.Client;
import banking.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class StaxXmlPaymentSystem extends SaxXmlPaymentSystem {
    private static final Logger logger = LoggerFactory.getLogger(StaxXmlPaymentSystem.class);

    @Override
    protected void loadClients() {
        Client client = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(FILENAME));
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        switch (qName) {
                            case CLIENT_ELEMENT:
                                try {
                                    int id = Integer.parseInt(startElement.getAttributeByName(QName.valueOf(ID_ATTRIBUTE)).getValue());
                                    String login = startElement.getAttributeByName(QName.valueOf(LOGIN_ATTRIBUTE)).getValue();
                                    String password = startElement.getAttributeByName(QName.valueOf(PASSWORD_ATTRIBUTE)).getValue();
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
                                    int id = Integer.parseInt(startElement.getAttributeByName(QName.valueOf(ID_ATTRIBUTE)).getValue());
                                    long account = Long.parseLong(startElement.getAttributeByName(QName.valueOf(ACCOUNT_ATTRIBUTE)).getValue());
                                    long balance = Long.parseLong(startElement.getAttributeByName(QName.valueOf(BALANCE_ATTRIBUTE)).getValue());
                                    boolean freezed = Boolean.parseBoolean(startElement.getAttributeByName(QName.valueOf(FREEZED_ATTRIBUTE)).getValue());
                                    if (client != null) {
                                        client.addCreditCard(new CreditCard(id, account, balance, freezed));
                                    }
                                } catch (NumberFormatException e) {
                                    logger.warn(e.getMessage());
                                }
                                break;
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if (CLIENT_ELEMENT.equals(endElement.getName().getLocalPart())) {
                            if (client != null) {
                                clients.add(client);
                                client = null;
                            }
                        }
                        break;
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            logger.error(e.getMessage());
        }
    }
}
