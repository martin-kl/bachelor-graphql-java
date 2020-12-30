package graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import javax.servlet.annotation.WebServlet;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

/**
 * Created by Martin Klampfer in 2018
 * Technical University Vienna, Bachelor: Software & Information Engineering
 *
 * This class is the entry point for the GraphQL implementation.
 * The database driver is loaded and the resolver functions are specified (Root types).
 *
 * To start this endpoint, start jetty with maven: mvn jetty:run or start it in your IDE.
 */

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private final static Driver driver;

    static {
        //in our case we start a connection
        driver = GraphDatabase.driver("bolt://localhost");
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(
                        new QueryRootType(driver),
                        new MutationRootType(driver))
                .build()
                .makeExecutableSchema();
    }
}
