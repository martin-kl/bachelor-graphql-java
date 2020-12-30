package models;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Martin Klampfer in 2018
 * Technical University Vienna, Bachelor: Software & Information Engineering
 */

public class Movie {

    private final String title;
    private final String tagline;
    private final int released;
    private final List<CastPerson> cast;

    public Movie(String title, String tagline, int released, List<CastPerson> cast) {
        this.title = title;
        this.tagline = tagline;
        this.released = released;
        this.cast = cast;
    }

    public Movie(String title, String tagline, int released) {
        this.title = title;
        this.tagline = tagline;
        this.released = released;
        this.cast = new LinkedList<>();
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

    public List<CastPerson> getCast() {
        return cast;
    }
}
