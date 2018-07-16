package graphql;

public class Person {
    private final String name;
    private final int born;

    public Person(String name, int born) {
        this.name = name;
        this.born = born;
    }

    public String getName() {
        return name;
    }

    public int getBorn() {
        return born;
    }

}
