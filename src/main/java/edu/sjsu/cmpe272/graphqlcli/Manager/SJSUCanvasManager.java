package edu.sjsu.cmpe272.graphqlcli.Manager;
import edu.sjsu.cmpe272.graphqlcli.Command.CanvasCommand;
import edu.sjsu.cmpe272.graphqlcli.Entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import picocli.CommandLine;
import reactor.core.publisher.Mono;

import java.util.List;


@Slf4j
@Component
public class SJSUCanvasManager {

    @Value("${SJSU_API_GRAPHQL}")
    private String url;

    @CommandLine.ParentCommand
    private CanvasCommand myCommand;

    public List<Course> graphQLQueryResponse(String bearerToken, String query) {
        Mono<List<Course>> clientResponse = null;
        List<Course> response;

        try{
            log.debug("Setting Http Headers for the GraphQL API");
            WebClient webClient = WebClient.builder()
                    .baseUrl(url) // Set the base URL for requests
                    .defaultHeader("Authorization", "Bearer " + bearerToken)
                    .defaultHeader("Content-Type", "application/json")
                    .build();

            HttpGraphQlClient graphQlClient = HttpGraphQlClient.builder(webClient).build();
            log.debug("Sending a POST request to SJSU Graphql endpoint");
            clientResponse = graphQlClient.document(query).retrieve("allCourses").toEntity(new ParameterizedTypeReference<>() {});
            response = clientResponse.block();
        } catch (Exception e) {
            log.debug("Error while framing and calling the GraphQL HTTP response");
            return null;
        }
        return response;
    }
}
