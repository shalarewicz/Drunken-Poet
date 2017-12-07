/* Copyright (c) 2015-2017 MIT 6.005/6.031 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.IOException;

/**
 * Example program using GraphPoet.
 * 
 * <p>PS2 instructions: you are free to change this example class.
 */
public class Main {
    
    /**
     * Generate example poetry.
     * 
     * @param args unused
     * @throws IOException if a poet corpus file cannot be found or read
     */
    public static void main(String[] args) throws IOException {
        final GraphPoet nimoy = new GraphPoet(new File("src/poet/gameOfThrones.txt"));
        final String input = " I see in your eyes the same fear that would take the heart of me. "
        		+ "The day may come when the courage of Men fails; when we forsake our friends "
        		+ "and break all bonds of fellowship; but it is not this day - an hour of wolves "
        		+ "and shattered shields, when the Age of Man comes crashing down - but it is not this day!!! "
        		+ "This day we fight! By all that you hold dear on this good earth - I bid you stand! Men of the West";
        System.out.println(input + "\n>>>\n" + nimoy.poem(input));
    }
    
}
