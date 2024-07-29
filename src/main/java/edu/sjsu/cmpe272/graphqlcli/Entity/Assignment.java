package edu.sjsu.cmpe272.graphqlcli.Entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Assignment {
    private String name;
    private Timestamp dueAt;

    @Override
    public String toString() {
        return name + " due at " + dueAt;
    }
}
