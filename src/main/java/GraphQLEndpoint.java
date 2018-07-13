import com.coxautodev.graphql.tools.SchemaParser;
import javax.servlet.annotation.WebServlet;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;


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
