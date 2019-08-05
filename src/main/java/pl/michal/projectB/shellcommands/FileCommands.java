package pl.michal.projectB.shellcommands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class FileCommands {

    static final Logger logger = LoggerFactory.getLogger(FileCommands.class);

    @ShellMethod("Choose file location")
    public void file(String filePath) {
    }
}
