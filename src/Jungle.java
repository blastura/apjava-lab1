/*
 * @(#)Jungle.java
 * Time-stamp: "2008-11-13 18:48:53 anton"
 */

public class Jungle implements Plugable {
    String[] animals;
    public Jungle() {
        animals = new String[] {"Tiger", "Monkey", "Elephant"};
    }

    public boolean animalIsInDjungle(String animal) {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i].equals(animal)) {
                return true;
            }
        }
        return false;
    }

    public final String getDescription() {
        return "A simple representation of a djungle";
    }
}
