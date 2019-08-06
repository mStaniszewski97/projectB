package pl.michal.projectB.filetools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.michal.projectB.db.DbService;
import pl.michal.projectB.model.Contact;
import pl.michal.projectB.model.Customer;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static pl.michal.projectB.filetools.FileConstants.*;

@Component
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    @Autowired
    private DbService dbService;

    public void mappingRecordsCSV(String filePath) {
        logger.info("Start mapping records from csv file");
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            Scanner scanner = new Scanner(inputStream, UTF_8);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(COMMA);
                Customer customer = new Customer(data);
                logger.info(customer.toString());
                dbService.saveCustomer(customer);
                createContactsForCustomer(customer, Arrays.copyOfRange(data, CITY_INDEX, data.length));
            }
        } catch (FileNotFoundException exception) {
            logger.error("File was not found " + exception.getMessage());
        }
        logger.info("End mapping records from csv file");
    }

    private void createContactsForCustomer(Customer customer, String[] contactData) {
        for (String singleContact : contactData) {
            Contact contact = new Contact(customer.getId(), singleContact);
            logger.info(contact.toString());
            dbService.saveContact(contact);
        }
    }

    public void mappingRecordsXML(String filePath) {
        logger.info("Start mapping records from xml file");
        try {
            FileInputStream inputStream = new FileInputStream(filePath);

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);

            Customer customer = null;
            Set<Contact> customerContacts = new HashSet<>();
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    String startElementName = startElement.getName().toString();
                    if (startElementName.equals(XML_PERSON_TAG)) {
                        customer = new Customer();
                        customer.setAge(null);
                    }
                    if (startElementName.equals(XML_NAME_TAG) && customer != null) {
                        xmlEvent = xmlEventReader.nextEvent();
                        String name = xmlEvent.asCharacters().toString();
                        customer.setName(name);
                    }
                    if (startElementName.equals(XML_SURNAME_TAG) && customer != null) {
                        xmlEvent = xmlEventReader.nextEvent();
                        String surname = xmlEvent.asCharacters().toString();
                        customer.setSurname(surname);
                    }
                    if (startElementName.equals(XML_AGE_TAG) && customer != null) {
                        xmlEvent = xmlEventReader.nextEvent();
                        String age = xmlEvent.asCharacters().toString();
                        customer.setAge(age);
                    }
                    if (startElementName.equals(XML_CONTACT_TAG) && customer != null) {
                        Iterator iterator = startElement.getAttributes();
                        xmlEvent = xmlEventReader.nextEvent();
                        String contactValue = xmlEvent.asCharacters().toString();
                        Contact contact = new Contact(customer.getId(), contactValue);
                        customerContacts.add(contact);
                    }
                }

                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    String endElementName = endElement.getName().toString();

                    if (endElementName.equals(XML_PERSON_TAG) && customer != null) {
                        dbService.saveCustomer(customer);
                        UUID customerID = customer.getId();
                        customerContacts.forEach(contact -> contact.setCustomerId(customerID));
                        customerContacts.forEach(contact -> dbService.saveContact(contact));
                        logger.info("Clearing customer and his contacts");
                        customer = null;
                        customerContacts.clear();
                    }
                }
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
        logger.info("End mapping records from xml file");
    }
}
