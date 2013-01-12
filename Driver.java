import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Driver class for the definition game.
 * 
 * @author Omar
 *
 */

public class Driver {
	
	/**
	 * The list of vocabulary words.
	 * 
	 */
	
	static ArrayList<String> words = new ArrayList<String>();
	
	/**
	 * Scanner for standard input.
	 * 
	 */
	
	static Scanner reader = new Scanner(System.in);
	
	/**
	 * Main method.
	 * 
	 */
	
	public static void main(String[] args) {
		
		// Game setup.
		
		initializeWords();
		System.out.println("There are " + words.size() 
				+ " words in the system.");
		filterWords();
		
		System.out.println("There are now " + words.size() + " words.");
		System.out.print("How many words do you want to attempt now? ");
		int numberOfWords = reader.nextInt();
		
		if (numberOfWords < 1 || numberOfWords > words.size()) {
			System.out.println("Invalid number of words.");
			System.exit(1);
		}
		
		reader.nextLine(); // Toss out the newline.
		Collections.shuffle(words);
		ArrayList<String> modifiedWords = new ArrayList<String>();
		for (int i = 0; i < numberOfWords; i++) {
			modifiedWords.add(words.get(i));
		}
		
		// Main game code.
		
		Examiner examiner = new Examiner(modifiedWords);
		int correct = 0;
		while (!examiner.isEmpty()) {
			String definition = examiner.getDefinition();
			System.out.println("What word matches this definition?: "
					+ definition + ".");
			String guess = reader.nextLine();
			
			if (examiner.verify(guess)) {
				System.out.println("Correct.");
				correct++;
			} else {
				System.out.println("Incorrect. The correct word is: " + 
						examiner.getCurrentWord() + ".");
			}
		}
		
		// End of game.
		
		double percent = correct * 100.0 / numberOfWords;
		int percentHat = (int)(percent * 100);
		percent = percentHat / 100.0;
		System.out.println("There are no words left. " + correct + "/"
				+ numberOfWords + " (" + percent + "%) were guessed correctly.");
		
		reader.close();
	}
	
	/**
	 * Initializes the words array list.
	 * 
	 */
	
	public static void initializeWords() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("words.txt"));
			String nextLine = reader.readLine();
			while (nextLine != null) {
				words.add(nextLine);
				nextLine = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("The words list was not found.");
		} catch (IOException e) {
			System.out.println("Error opening file.");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.out.println("File failed to close");
				}
			}
		}
	}
	
	/**
	 * This method filters the words.
	 * 
	 */	
	
	public static void filterWords() {
		System.out.print("Do you want to filter the words? (YES or NO): ");
		String response = reader.nextLine();
		
		if (response.toLowerCase().equals("yes")) {
			System.out.print("Enter the range of words (example: 30-80): ");
			String range = reader.nextLine();
			int beginning = Integer.valueOf(range.substring(0, range.indexOf("-")));
			int end = Integer.valueOf(range.substring(range.indexOf("-") + 1));
			
			if (beginning < 1 || end > words.size()) {
				System.out.println("Invalid range.");
				System.exit(1);
			}
			
			// Mark words for deletion.
			for (int i = 0; i < words.size(); i++) {
				if (i < beginning - 1 || i >= end) {
					words.remove(i);
					words.add(i, "delete");
				}
			}
			
			// Delete the words.
			Iterator<String> wordsIterator = words.iterator();
			while (wordsIterator.hasNext()) {
				String temp = wordsIterator.next();
				if (temp.equals("delete")) {
					wordsIterator.remove();
				}
			}
		}
	}

}
