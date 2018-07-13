import com.coxautodev.graphql.tools.GraphQLRootResolver;
import org.neo4j.driver.v1.Driver;

public class MutationRootType implements GraphQLRootResolver{
    private final Driver driver;

    public MutationRootType(Driver driver) {
        this.driver = driver;
    }

    public Person createPerson(String name, int born) {
        return null;
        /*
        Movie newMovie = new Movie(linkRepository.getId(), url, description, context.getUser().getId());
        linkRepository.saveLink(newMovie);
        return newMovie;
        */
    }

    public Movie createMovie(String title, int released) {
        return null;
        /*
        Person newPerson = new Person(userRepository.getId(), name, auth.getEmail(), auth.getPassword());
        userRepository.saveUser(newPerson);
        return newPerson;
        */
    }
}
