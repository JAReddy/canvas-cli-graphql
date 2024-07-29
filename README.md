# Starter Canvas GraphQL CLI 
For this assignment, we are going to write a simple Spring Boot command line program using Picocli and GraphQL. The command line you write will have two subcommands: list-courses and list-assignments.

list-courses will have the option --active/--no-active to list (defaulting to --active) to choose whether to list all the courses that are no longer active.

list-assignments will take the course name as an argument and the option --active/--no-active to list (defaulting to --active) to choose whether to list all the assignments that are no longer active. Since course names are long, if there is no exact match for a course name, you should use a course with that substring. If there are multiple matching courses, print a message stating that the matches are not unique, list the matching courses, and exit. When listing the assignments, print the assignment name and due date.

# Setup

1. Run `ssh git@github.com:SJSU-CS/cmpe272-starter-canvas--JAReddy.git>`
2. Setup maven path as mvn
3. Run `mvn clean install` in the project root directory and generate the project jar file viz. GraphQLCLI-0.0.1-SNAPSHOT.jar. This will be generated in the target folder of the project root.

# How to run the commands
1. `java -jar "<Path>/GraphQLCLI-0.0.1-SNAPSHOT.jar" --token <BearerToken> list-courses [-hV] [--active] [--no-active]`
2. `java -jar "<Path>/GraphQLCLI-0.0.1-SNAPSHOT.jar" --token <BearerToken> list-assignments [-hV] [--active] [--no-active] <course>`

### Note: Token is required to run the above commands for Canvas Authentication



