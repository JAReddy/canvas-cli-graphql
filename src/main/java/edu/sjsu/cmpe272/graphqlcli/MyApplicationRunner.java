package edu.sjsu.cmpe272.graphqlcli;

import edu.sjsu.cmpe272.graphqlcli.Command.CanvasCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class MyApplicationRunner implements CommandLineRunner, ExitCodeGenerator {

    @Autowired
    private CommandLine.IFactory factory;

    @Autowired
    private CanvasCommand myCommand;

    private int exitCode = 5;

    @Override
    public void run(String... args) throws Exception {
        exitCode = new CommandLine(myCommand, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
