package models;

/**
 * Created by Martin Klampfer in 2018
 * Technical University Vienna, Bachelor: Software & Information Engineering
 */

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
