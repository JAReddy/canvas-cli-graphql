package edu.sjsu.cmpe272.graphqlcli.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignmentsConnection {
    private List<Assignment> nodes;
}
