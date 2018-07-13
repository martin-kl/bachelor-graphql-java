import java.util.LinkedList;
import java.util.List;

public class Person {
    private final String name;
    private final int born;
    private final List<Movie> actedIn;
    private final List<Movie> directed;
    private final List<Movie> produced;

    public Person(String name, int born, List<Movie> actedIn, List<Movie> directed, List<Movie> produced) {
        this.name = name;
        this.born = born;
        this.actedIn = actedIn;
        this.directed = directed;
        this.produced = produced;
    }

    public Person(String name, int born) {
        this.name = name;
        this.born = born;
        actedIn = new LinkedList<>();
        directed = new LinkedList<>();
        produced = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public int getBorn() {
        return born;
    }

    public List<Movie> getActedIn() {
        return actedIn;
    }

    public List<Movie> getDirected() {
        return directed;
    }

    public List<Movie> getProduced() {
        return produced;
    }
}
