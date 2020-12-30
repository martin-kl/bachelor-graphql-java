package rest;

import models.CastPerson;
import models.Movie;
import models.Person;
import org.neo4j.driver.v1.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Martin Klampfer in 2018
 * Technical University Vienna, Bachelor: Software & Information Engineering
 *
 * This class contains the implementations of the different REST endpoints.
 * Every method implements one endpoint.
 * The driver is loaded and can be changed via the driver variable in the beginning.
 */

@RestController
public class Controller {
    private static final Driver driver = GraphDatabase.driver("bolt://localhost");

    @RequestMapping("/person/{name}")
    public Person getPerson(@PathVariable String name) {
        //because some persons have no born value, we have to check these values and set it to 0 in such a case
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (a:Person {name:{name}}) RETURN a.name as name, a.born as born LIMIT 1", Collections.singletonMap("name", name));
            if (result.hasNext()) {
                Record record = result.next();
                return new Person(record.get("name").asString(),
                        record.get("born").type().name().equals("NULL") ? 0 : record.get("born").asInt());
            }
        }
        return null;
    }

    @RequestMapping("/persons")
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


    @RequestMapping("/movie/{title}")
    public Movie getMovie(@PathVariable String title) {
        try (Session session = driver.session()) {
            StatementResult result = session.run("MATCH (movie:Movie {title:{title}}) OPTIONAL MATCH (movie)<-[r]-(person:Person)" +
                            " RETURN movie.title as title, movie.tagline as tagline, movie.released as released," +
                            " collect({name:person.name, born:person.born, job:head(split(lower(type(r)),'_'))}) as cast LIMIT 1",
                    Collections.singletonMap("title", title));
            if (result.hasNext()) {
                Record record = result.next();
                List<CastPerson> cast = new LinkedList<>();

                for (int i = 0; i < record.get("cast").size(); i++) {
                    int born = record.get("cast").get(i).get("born").toString().equals("NULL") ? 0 : record.get("cast").get(i).get("born").asInt();
                    cast.add(new CastPerson(record.get("cast").get(i).get("name").asString(),
                            born,
                            record.get("cast").get(i).get("job").asString()));
                }

                return new Movie(record.get("title").asString(),
                        record.get("tagline").asString(), record.get("released").asInt(), cast);
            }
        }
        return null;
    }

    @RequestMapping("/movies")
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

}
