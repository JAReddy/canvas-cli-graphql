package edu.sjsu.cmpe272.graphqlcli.Command;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Getter
@Component
@Slf4j
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = {ListCourses.class, ListAssignments.class})
public class CanvasCommand {

    @CommandLine.Option(names = {"--token"}, description = "API authorization token from canvas", required = true)
    private String bearerToken;

}
