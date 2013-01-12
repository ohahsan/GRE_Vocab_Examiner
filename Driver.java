import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Driver class for the definition game.
 * 
 * @author Omar
 *
 */

public class Driver {
	
	static final ArrayList<String> words = new ArrayList<String>(Arrays.asList(
			"ABATE>>To lessen in intensity or degree",
			"ABERRANT>>Deviating from the norm",
			"ABJURE>>To renounce or reject solemnly",
			"ABROGATE>>To repeal or revoke",
			"ABSTEMIOUS>>Eating or drinking in moderation",
			"ACCOLADE>>An expression of praise",
			"ACERBIC>>Having a sour or bitter taste or character",
			"ACUMEN>>Quick, keen, or accurate knowledge or insight",
			"ADMONISH>>To reprove; to express warning or disapproval",
			"ADROIT>>Adept; dexterous",
			"ADULATION>>Excessive praise; intense adoration",
			"ADULTERATE>>To reduce purity by combining with inferior ingredients",
			"AESTHETIC>>Dealing with, appreciative of, or responsive to art or beauty",
			"AGGRANDIZE>>To increase in intensity, power, or prestige",
			"ALACRICITY>>Eager and enthusiastic willingness",
			"ALCHEMY>>A medieval science aimed at the transmutation of metals, especially base metals, into gold",
			"AMALGAMATE>>To combine several elements into a whole",
			"AMENABLE>>Agreeable; responsive to suggestion",
			"ANACHRONISTIC>>Out of place in terms of historical or chronological context",
			"ANATHEMAL>>A solemn or religious curse; a cursed or thoroughly loathed person or thing",
			"ANOMALY>>Deviation from the normal order, form, or rule; abnormality",
			"ANTITHETICAL>>Diametrically opposed; as in antithesis",
			"ANTIPATHY>>Aversion; dislike",
			"APOCRYPHAL>>Of dubious authenticity or origin; spurious",
			"APOGEE>>Farthest or highest point; culmination; zenith",
			"APOSTATE>>One who abandons long-held religious or political convictions",
			"APOTHEOSIS>>Deification; supreme example",
			"APPOSITE>>Appropriate; pertinent; relevant",
			"APPRISE>>To give notice to; to inform",
			"APPROBATION>>An expression of approval or praise",
			"ARABESQUE>>A complex, ornate design; also a dance position",
			"ARCANE>>Mysterious; esoteric",
			"ARCHAIC>>Outdated; associated with an earlier, perhaps more primitive time",
			"ARTLESS>>Completely without guile; unsophisticated",
			"ASCETIC>>Someone practicing self-denial",
			"ASPERTION>>An act of defamation or maligning",
			"ASSAY>>To put to a test",
			"ASSIDUOUS>>Diligent; hard-working",
			"ASSUAGE>>To ease or lessen; to appease or pacify",
			"ASTRINGENT>>Biting; severe"
			));
	
	/**
	 * Main method.
	 */
	
	public static void main(String[] args) {
		
		// Game setup.
		
		Scanner reader = new Scanner(System.in);
		System.out.println("There are " + words.size() 
				+ " words in the system.");
		System.out.print("How many words do you want to attempt now? ");
		int numberOfWords = reader.nextInt();
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
		System.out.println("There are no words left. " + correct + "/"
				+ numberOfWords + " (" + percent + "%) were guessed correctly.");
		
	}

}
