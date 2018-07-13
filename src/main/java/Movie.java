import java.util.LinkedList;
import java.util.List;

public class Movie {

    private final String title;
    private final String tagline;
    private final int released;
    private final List<Person> actors;
    private final List<Person> directors;
    private final List<Person> producers;

    public Movie(String title, String tagline, int released, List<Person> actors, List<Person> directors, List<Person> producers) {
        this.title = title;
        this.tagline = tagline;
        this.released = released;
        this.actors = actors;
        this.directors = directors;
        this.producers = producers;
    }

    public Movie(String title, String tagline, int released) {
        this.title = title;
        this.tagline = tagline;
        this.released = released;
        this.actors = new LinkedList<>();
        this.directors = new LinkedList<>();
        this.producers = new LinkedList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getTagline() {
        return tagline;
    }

    public int getReleased() {
        return released;
    }

    public List<Person> getActors() {
        return actors;
    }

    public List<Person> getDirectors() {
        return directors;
    }

    public List<Person> getProducers() {
        return producers;
    }
}
