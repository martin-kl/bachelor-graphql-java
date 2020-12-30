package models;

/**
 * Created by Martin Klampfer in 2018
 * Technical University Vienna, Bachelor: Software & Information Engineering
 *
 * This is the definition of a person as a cast of a movie.
 * It extends the Person class and adds information about their job.
 */

public class CastPerson extends Person {

    private String job;

    public CastPerson(String name, int born, String job) {
        super(name, born);
        this.job = job;
    }

    public String getJob() {
        return job;
    }
}
