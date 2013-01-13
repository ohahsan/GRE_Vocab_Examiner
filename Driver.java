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
	 * Multiple choice mode. Disabled when less than 10 words are chosen.
	 * 
	 */
	
	static boolean multipleChoiceMode;
	
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
	 * Examiner object that has the game mechanics.
	 * 
	 */
	
	static Examiner examiner;
	
	/**
	 * Main method.
	 * 
	 */
	
	public static void main(String[] args) {
		
		// Game setup.
		
		do {
			
			initializeWords();
			System.out.println("There are " + words.size() 
					+ " words in the system.");
			filterWords();
			
			if (query("Display list of words? (YES or NO): ")) {
				multipleChoiceMode = false;
				displayWords();
			} else {
				multipleChoiceMode = 
						query("Multiple choice mode? (YES or NO): ");
			}
			
			System.out.println("There are " + words.size() + " words.");
			System.out.print("Number of words? (Multiple choice will be" +
					"disabled if this is less than 10): ");
			int numberOfWords = reader.nextInt();
			
			if (numberOfWords < 10 && multipleChoiceMode) {
				System.out.println("Multiple choice mode has been diabled.");
				multipleChoiceMode = false;
			}
			
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
			
			examiner = new Examiner(modifiedWords);
			int correct = 0;
			while (!examiner.isEmpty()) {
				correct += definitionQuestion();
			}
			
			// End of game.
			
			// Make a percent rounded to 2 decimal places.
			double percent = correct * 100.0 / numberOfWords;
			int percentHat = (int)(percent * 100);
			percent = percentHat / 100.0;
			System.out.println("There are no words left. " + correct + "/"
					+ numberOfWords + " (" + percent 
					+ "%) were guessed correctly.");

		} while (query("Another round? (YES or NO): "));
		reader.close();
	}
	
	/**
	 * Initializes the words array list.
	 * 
	 */
	
	public static void initializeWords() {
		words.clear();
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
		boolean response = 
				query("Do you want to filter the words? (YES or NO): ");
		if (response) {
			System.out.print("Enter the range of words (example: 30-80): ");
			String range = reader.nextLine();
			int beginning = 
					Integer.valueOf(range.substring(0, range.indexOf("-")));
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
	
	/**
	 * Gives a standard match definition to word question.
	 * 
	 */

	public static int definitionQuestion() {
		String definition = examiner.getDefinition();
		System.out.println("Which word matches this definition? "
				+ definition + ".");
		if (multipleChoiceMode) {
			multipleChoice();
		}
		String guess = reader.nextLine();
		
		if (examiner.verifyDefinition(guess)) {
			System.out.println("Correct.");
			return 1;
		} else {
			System.out.println("Incorrect. The correct word is " + 
					examiner.getCurrentWord() + ".");
			return 0;
		}
	}
	
	/**
	 * Prompts a yes/no question.
	 * 
	 */
	
	public static boolean query(String question) {
		System.out.print(question);
		String response = reader.nextLine();
		return response.toLowerCase().equals("yes");
	}
	
	/**
	 * Prints out the words.
	 * 
	 */
	
	public static void displayWords() {
		Collections.sort(words);
		for (String current : words) {
			System.out.println(current.substring(0, current.indexOf(">>")));
		}
	}
	
	/**
	 * Displays multiple choices.
	 * 
	 */
	
	public static void multipleChoice() {
		Collections.shuffle(words);
		ArrayList<String> answers = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			if (words.get(i).equals(examiner.getCurrentWord())) {
				String lastWord = words.get(words.size() - 1);
				answers.add(lastWord.substring(0, lastWord.indexOf(">>")));
			} else {
				answers.add(words.get(i).substring(
						0, words.get(i).indexOf(">>")));
			}
		}
		answers.add(examiner.getCurrentWord());
		Collections.shuffle(answers);
		System.out.print("Choices: ");
		for (String word : answers) {
			System.out.print(word + " ");
		}
		System.out.println();
	}
}
