package pl.michal.projectB.filetools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.michal.projectB.model.Contact;
import pl.michal.projectB.model.Customer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

@Component
public class FileUtils {

    static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public void mappingRecords(String filePath) {
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                Customer customer = new Customer(data);
                //TODO save to DB
                createContactsForCustomer(customer, Arrays.copyOfRange(data, 3, data.length));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createContactsForCustomer(Customer customer, String[] contactData) {
        for (String singleContact : contactData) {
            Contact contact = new Contact(customer.getId(), singleContact);
            //TODO save do DB
        }
    }
}
