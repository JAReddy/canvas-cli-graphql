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

@Component
@Slf4j
@CommandLine.Command(name = "list-courses", mixinStandardHelpOptions = true, version = "1.0",
        description = "Prints the list of courses to STDOUT.", exitCodeOnExecutionException = 10)
public class ListCourses implements Callable<Integer> {

    @Option(names = {"--active"}, description = "Use this to fetch only active courses")
    private boolean active = true;

    @Option(names = {"--no-active"}, description = "Use this to fetch all the courses including non-active")
    private boolean nonActive;

    @Autowired
    private SJSUCanvasManager manager;

    @Autowired
    private Helper helper;

    @Value("${graphql.query.list-courses}")
    private String graphQLQuery;

    @Value("${sjsu.course.active.term.name}")
    private String activeTermName;
    @CommandLine.ParentCommand
    private CanvasCommand myCommand;

    @Override
    public Integer call(){
        List<Course> courseList = manager.graphQLQueryResponse(myCommand.getBearerToken(), graphQLQuery);
        String response = helper.filterCoursesByTermName(courseList, activeTermName, !nonActive);
        System.out.println(response);
        return 9;
    }
}
