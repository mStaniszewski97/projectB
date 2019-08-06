package pl.michal.projectB.shellcommands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.michal.projectB.db.DbService;
import pl.michal.projectB.filetools.FileUtils;

import javax.annotation.PostConstruct;

import static pl.michal.projectB.filetools.FileConstants.*;

@ShellComponent
public class FileCommands {
    private static final Logger logger = LoggerFactory.getLogger(FileCommands.class);

    @Autowired
    private DbService dbService;

    @Autowired
    private FileUtils fileUtils;

    @PostConstruct
    public void customInit() {
        logger.info("Type 'help' for more information :)");
    }

    @ShellMethod("Choose file location (example: file C:\\\\Users\\\\Admin\\\\Desktop\\\\dane-osoby.xml)")
    public void file(String filePath) {
        if (filePath.endsWith(CSV_SUFFIX) || filePath.endsWith(TXT_SUFFIX)) {
            fileUtils.mappingRecordsCSV(filePath);
        } else if (filePath.endsWith(XML_SUFFIX)) {
            fileUtils.mappingRecordsXML(filePath);
        } else {
            logger.info("Wrong file extension");
        }
    }

    @ShellMethod("Check if database exist (example: check-database testDb)")
    public void checkDatabase(String databaseName) {
        dbService.connection(databaseName);
    }

    @ShellMethod("Number of rows in CONTACTS table")
    public void contactsSize() {
        Integer numberOfRows = dbService.numberOfRows("CONTACTS");
        logger.info("CONTACTS size: " + numberOfRows);
    }

    @ShellMethod("Number of rows in CUSTOMERS table")
    public void customersSize() {
        Integer numberOfRows = dbService.numberOfRows("CUSTOMERS");
        logger.info("CUSTOMERS size: " + numberOfRows);
    }

    @ShellMethod("Clear data in CUSTOMERS and CONTACTS")
    public void clearData() {
        dbService.clearTables();
    }
}
