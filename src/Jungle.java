/*
 * @(#)Jungle.java
 * Time-stamp: "2008-11-16 21:48:25 anton"
 */

/**
 * A simple representation of a Jungle.
 *
 * @author Anton Johansson dit06ajn@cs.umu.se
 * @version 1.0
 */
public class Jungle implements Plugable {
    String[] animals;
    /** Creates a new Jungle, fills an array with some animals. */
    public Jungle() {
        animals = new String[] {"Tiger", "Monkey", "Elephant"};
    }

    /**
     * Prints true if the specified animal exists in this Jungle.
     *
     * @param animal the animal to check for.
     */
    public void animalIsInDjungle(String animal) {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i].equals(animal)) {
                System.out.println("true");
            }
        }
        System.out.println("false");
    }

    public final String getDescription() {
        return "A simple representation of a djungle";
    }
}
