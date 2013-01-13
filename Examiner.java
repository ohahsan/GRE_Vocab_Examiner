import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class creates an Examiner object that presents questions
 * to the user.
 * 
 * @author Omar
 * 
 */

public class Examiner {
	
	/**
	 * List of words and their definitions.
	 * 
	 */
	
	private List<String> words;
	
	/**
	 * Current definition being displayed.
	 * 
	 */
	
	private String currentDefinition;
	
	/**
	 * Word that matches the current definition.
	 * 
	 */
	
	private String currentWord;
	
	/**
	 * The constructor initializes the words instance variable.
	 * 
	 * @param words
	 * 
	 */
	
	public Examiner(ArrayList<String> words) {
		this.words = words;
	}
	
	/**
	 * This method gives a definition as a question.
	 * 
	 * @return
	 * 
	 */
	
	public String getDefinition() {
		String next = words.get(0);
		currentWord = next.substring(0, next.indexOf(">>"));
		currentDefinition = next.substring(next.indexOf(">>") + 2);
		words.remove(0);
		return currentDefinition;
	}
	
	/**
	 * This method returns true if word is guessed correctly, false otherwise.
	 * 
	 * @param answer
	 * @return
	 * 
	 */
	
	public boolean verifyDefinition(String answer) {
		answer = answer.toLowerCase();
		return answer.equals(currentWord.toLowerCase());
	}
	
	/**
	 * Returns true if the game is over (no words remain).
	 * 
	 * @return
	 * 
	 */
	
	public boolean isEmpty() {
		return words.isEmpty();
	}
	
	/**
	 * Getter for the current definition.
	 * 
	 * @return
	 * 
	 */

	public String getCurrentDefinition() {
		return currentDefinition;
	}
	
	/**
	 * Setter for the current definition (should never be called).
	 * 
	 * @param currentDefinition
	 * 
	 */

	public void setCurrentDefinition(String currentDefinition) {
		this.currentDefinition = currentDefinition;
	}
	
	/**
	 * Getter for the current word.
	 * 
	 * @return
	 * 
	 */

	public String getCurrentWord() {
		return currentWord;
	}
	
	/**
	 * Setter for the current word (should never be called).
	 * 
	 * @param currentWord
	 * 
	 */

	public void setCurrentWord(String currentWord) {
		this.currentWord = currentWord;
	}

}
