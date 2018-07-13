import com.coxautodev.graphql.tools.GraphQLRootResolver;
import graphql.GraphQLException;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import java.util.*;

public class QueryRootType implements GraphQLRootResolver {

    private final Driver driver;

    public QueryRootType(Driver driver) {
        this.driver = driver;
    }


    public List<Person> allPersons() {
        //because some persons have no born value, we have to check these values and set it to 0 in such a case
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (a:Person) RETURN a.name as name, a.born as born");
            List<Person> persons = new LinkedList<>();
            while (result.hasNext()) {
                Record record = result.next();
                persons.add(new Person(record.get("name").asString(),
                        record.get("born").type().name().equals("NULL") ? 0 : record.get("born").asInt()));
            }
            return persons;
        }
    }

    public Person getPerson(String name) {
        //because some persons have no born value, we have to check these values and set it to 0 in such a case
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (a:Person {name:{name}}) RETURN a.name as name, a.born as born LIMIT 1", Collections.singletonMap("name", name));
            if (result.hasNext()) {
                Record record = result.next();
                return new Person(record.get("name").asString(),
                        record.get("born").type().name().equals("NULL") ? 0 : record.get("born").asInt());
            }
        }
        throw new GraphQLException("error while fetching a user with the name:" + name);
    }

    public List<Movie> allMovies() {
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (m:Movie) RETURN m.title as title, " +
                    "m.tagline as tagline, m.released as released");
            List<Movie> movies = new LinkedList<>();
            while (result.hasNext()) {
                Record record = result.next();
                movies.add(new Movie(record.get("title").asString(),
                        record.get("tagline").asString(), record.get("released").asInt()));
            }
            return movies;
        }
    }

    public Movie getMovie(String title) {
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (m:Movie {title:{title}}) RETURN m.title as title, " +
                    "m.tagline as tagline, m.released as released LIMIT 1", Collections.singletonMap("title", title));
            if (result.hasNext()) {
                Record record = result.next();
                return new Movie(record.get("title").asString(),
                        record.get("tagline").asString(), record.get("released").asInt());
            }
        }
        throw new GraphQLException("error while fetching the movie with the title:" + title);
    }
}
