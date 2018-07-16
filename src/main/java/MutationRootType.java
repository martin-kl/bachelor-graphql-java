import com.coxautodev.graphql.tools.GraphQLRootResolver;
import graphql.GraphQLException;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class MutationRootType implements GraphQLRootResolver {
    private final Driver driver;

    public MutationRootType(Driver driver) {
        this.driver = driver;
    }

    public Person createPerson(String name, int born) {
        if (born < 1000) {
            throw new IllegalArgumentException("Value for born field cannot be smaller than 1000");
        }
        try (Session session = driver.session()) {
            StatementResult result = session.run("CREATE (p:Person { name: \"" + name + "\", born: " + born + " } ) RETURN p.name as name, p.born as born");

            if (result.hasNext()) {
                Record record = result.next();
                return new Person(record.get("name").asString(), record.get("born").asInt());
            }
        }
        throw new GraphQLException("Exception occurred while creating a new node for person with name =" + name);
    }

    public Movie createMovie(String title, String tagline, int released) {
        try (Session session = driver.session()) {
            StatementResult result = session.run("CREATE (m:Movie { title: \"" + title + "\", tagline: \"" + tagline + "\", released: " + released + " } )" +
                    "RETURN m.title as title, m.tagline as tagline, m.released as released");

            if (result.hasNext()) {
                Record record = result.next();
                return new Movie(record.get("title").asString(), record.get("tagline").asString(), record.get("released").asInt());
            }
        }
        throw new GraphQLException("Exception occurred while creating a new node for the movie with title =" + title);
    }
}