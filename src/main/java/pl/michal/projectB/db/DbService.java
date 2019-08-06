package pl.michal.projectB.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.michal.projectB.model.Contact;
import pl.michal.projectB.model.Customer;

@Service
public class DbService {

    private static final Logger logger = LoggerFactory.getLogger(DbService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveCustomer(Customer customer) {
        logger.info("Saving customer: " + customer.toString());
        try {
            jdbcTemplate.update(
                    "INSERT INTO CUSTOMERS (ID, NAME, SURNAME, AGE) VALUES (?, ?, ?, ?)",
                    customer.getId().toString(), customer.getName(), customer.getSurname(), customer.getAge()
            );
        } catch (DataAccessException exception) {
            logger.error(exception.getMessage());
        }
    }

    public void saveContact(Contact contact) {
        logger.info("Saving customer: " + contact.toString());
        int typeValue = contact.getContactType().getValue();
        try {
            jdbcTemplate.update(
                    "INSERT INTO CONTACTS (ID, ID_CUSTOMER, TYPE, CONTACT) VALUES (?, ?, ?, ?)",
                    contact.getId().toString(), contact.getCustomerId().toString(), typeValue, contact.getContact()
            );
        } catch (DataAccessException exception) {
            logger.error(exception.getMessage());
        }
    }

    public void connection(String databaseName) {
        logger.info("Checking connection to " + databaseName);
        try {
            jdbcTemplate.execute("USE " + databaseName);
        } catch (DataAccessException exception) {
            System.out.println("Database was not found");
            logger.error(exception.getMessage());
            return;
        }
        logger.info("Database is ready to use!!!");
    }

    public Integer numberOfRows(String table) {
        logger.info("Checking size of " + table);
        Integer result = null;
        try {
            result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM " + table, Integer.class);
        } catch (DataAccessException exception) {
            System.out.println("Table was not found");
            logger.error(exception.getMessage());
        }
        return result;
    }

    public void clearTables() {
        try {
            jdbcTemplate.update("delete from CONTACTS");
            jdbcTemplate.update("delete from CUSTOMERS");
            logger.info("Tables are clear");
        } catch (DataAccessException exception) {
            System.out.println("CONTACTS and CUSTOMERS can't be cleared");
            logger.error(exception.getMessage());
        }
    }
}
