package edu.sjsu.cmpe272.graphqlcli.Entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Course {

    private String name;
    private  Term term;
    private AssignmentsConnection assignmentsConnection;

    @Override
    public String toString() {
        return name;
    }

}
