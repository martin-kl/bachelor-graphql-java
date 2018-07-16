package graphql;

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
