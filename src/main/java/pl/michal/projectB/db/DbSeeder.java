package pl.michal.projectB.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DbSeeder {

    private static final Logger logger = LoggerFactory.getLogger(DbSeeder.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public void databaseInitiation() {
        logger.info("Database is initialization");
        String sqlCreateCustomers = "CREATE TABLE IF NOT EXISTS `CUSTOMERS` (\n" +
                "  ID VARCHAR(36) NOT NULL,\n" +
                "  NAME VARCHAR(255) NOT NULL,\n" +
                "  SURNAME VARCHAR(255) NOT NULL,\n" +
                "  AGE int,\n" +
                "  PRIMARY KEY (ID)\n" +
                ")";
        String sqlCreateContacts = "CREATE TABLE IF NOT EXISTS `CONTACTS` (\n" +
                "  ID VARCHAR(36) NOT NULL,\n" +
                "  ID_CUSTOMER VARCHAR(36) NOT NULL,\n" +
                "  TYPE tinyint NOT NULL,\n" +
                "  CONTACT VARCHAR(255) NOT NULL,\n" +
                "  PRIMARY KEY (ID),\n" +
                "  FOREIGN KEY (ID_CUSTOMER) REFERENCES CUSTOMERS(ID)\n" +
                ")";
        if (logger.isDebugEnabled()) {
            logger.debug("SQL query: " + sqlCreateCustomers);
            logger.debug("SQL query: " + sqlCreateContacts);
        }
        jdbcTemplate.execute(sqlCreateCustomers);
        jdbcTemplate.execute(sqlCreateContacts);
        logger.info("Initialization finished");
    }
}
