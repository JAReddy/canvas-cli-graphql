package edu.sjsu.cmpe272.graphqlcli.Util;

import edu.sjsu.cmpe272.graphqlcli.Entity.Assignment;
import edu.sjsu.cmpe272.graphqlcli.Entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class Helper {

    public String filterCoursesByTermName(List<Course> courseList, String termName, Boolean active) {
        List<Course> filteredCourses = new ArrayList<>();
        try {
           // Filter courses based on input termName if active is true
            if (active) {
                for (Course c: courseList) {
                    if (termName.equals(c.getTerm().getName())) {
                        filteredCourses.add(c);
                    }
                }
            }
            else {
                filteredCourses = courseList;
            }
        } catch (Exception e) {
            log.debug("Error in filterByTermName");
            return "Error fetching courses";
        }
        return printCourses(filteredCourses);
    }

    public String filterAssignmentsByCourseName(List<Course> courseList, String inputCourseName, Boolean active) {
        List<Course> filteredCourses = new ArrayList<>();
        List<Assignment> assignmentList = new ArrayList<>();
        List<Assignment> filteredAssignments = new ArrayList<>();
        StringBuilder courseNamesResponse = new StringBuilder();
        try {
            log.debug("Filtering assignments based on the course name {}", inputCourseName);

            for (Course course: courseList) {
                if (course.getName().contains(inputCourseName)) {
                    courseNamesResponse.append(course.getName()).append("\n");
                    filteredCourses.add(course);
                }
            }

            if (filteredCourses.isEmpty()) {
                return "No Assignments for this course";
            }
            else if (filteredCourses.size()>1) {
                courseNamesResponse.insert(0, "There are multiple courses that match the search key: "+ inputCourseName +"\n");
                return courseNamesResponse.toString();
            } else if (active){
                log.debug("Fetching only active assignments (i.e., due date is in the future) for the course {}", inputCourseName);

                assignmentList = filteredCourses.get(0).getAssignmentsConnection().getNodes();

                for (Assignment assignment : assignmentList) {
                    Date currentDate = new Date();
                    Date dueAt = assignment.getDueAt();
                    if (dueAt != null && !assignment.getDueAt().before(currentDate)) {
                        filteredAssignments.add(assignment);
                    }
                }
                return printAssignments(filteredAssignments);
            } else {
                assignmentList = filteredCourses.get(0).getAssignmentsConnection().getNodes();
                return printAssignments(assignmentList);
            }
        } catch (Exception e) {
            log.debug("Error in filterByCourseName");
            return "Error fetching assignments";
        }
    }

    public String printCourses(List<Course> courseList) {
        if (courseList.isEmpty()) {
            return "No courses for the given inputs";
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<courseList.size();i++) {
            sb.append(courseList.get(i).toString());
            if (i<courseList.size()-1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String printAssignments(List<Assignment> assignmentList) {
        if(assignmentList.isEmpty()) {
            return "No Assignments for the given inputs";
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<assignmentList.size();i++) {
            sb.append(assignmentList.get(i).toString());
            if (i<assignmentList.size()-1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
