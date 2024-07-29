package edu.sjsu.cmpe272.graphqlcli.Command;

import edu.sjsu.cmpe272.graphqlcli.Entity.Course;
import edu.sjsu.cmpe272.graphqlcli.Manager.SJSUCanvasManager;
import edu.sjsu.cmpe272.graphqlcli.Util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@CommandLine.Command(name = "list-assignments", mixinStandardHelpOptions = true, version = "1.0",
        description = "Prints the list of assignments to STDOUT", exitCodeOnExecutionException = 20)
@Component
public class ListAssignments implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "Course name for which the assignments are required")
    private String course;
    @Option(names = {"--active"}, description = "Use this to fetch only active assignments")
    private boolean active = true;

    @Option(names = {"--no-active"}, description = "Use this to fetch all the assignments including non-active")
    private boolean nonActive;

    @Autowired
    private SJSUCanvasManager manager;

    @Autowired
    private Helper helper;

    @Value("${graphql.query.list-assignments}")
    private String graphQLQuery;

    @CommandLine.ParentCommand
    private CanvasCommand myCommand;

    @Override
    public Integer call(){
        List<Course> output = manager.graphQLQueryResponse(myCommand.getBearerToken(), graphQLQuery);
        String response = helper.filterAssignmentsByCourseName(output, course, !nonActive);
        System.out.println(response);
        return 19;
    }

}
