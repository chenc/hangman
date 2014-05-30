/*
 * File: Hangman.java
 * ------------------
 * This program is the console for the Hangman game.
 */

import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {
	
	//declare constants
	private final static int NUMGUESS = 8;
	
	//declare variables
	private HangmanLexicon hl;
	private HangmanCanvas canvas;
	private RandomGenerator rgen;
	private int guessCounter;
	private String gameWord;
	private boolean[] isVisible;

    //playing multiple games
	public void run() {
    	while (true) {
    		initializeGame();
    		playGame();
    		if (!playAgain()) {
    			println("Ok, goodbye.");
    			break;
    		}
    	}
    }
    
    //setup the game for play
    public void initializeGame() {
    	canvas.reset();
    	guessCounter = NUMGUESS;
		hl = new HangmanLexicon();
		rgen = RandomGenerator.getInstance();
    	println("Welcome to Hangman!");
    	gameWord = pickRandomWord();
    	isVisible = new boolean [gameWord.length()];
    	for (int i=0; i<isVisible.length; i++) {
    		isVisible[i] = false;
    	}

	}
    
    //winning and losing logic
	public void playGame() {	
		while (true) {
			playTurn();
			if (wonGame()) {
				println("The word now looks like this: " + visibleWord());
				canvas.displayWord(visibleWord());
				println("AWESOME! YOU WON!!");
				break;
			} 
			if (guessCounter == 0) {
				println("Sorry, you've been hung. :(");
				canvas.displayWord(gameWord);
				break;
			}
		}
	}

	//picks random word from the HangmanLexicon
	public String pickRandomWord() {
		int randomValue = rgen.nextInt(hl.getWordCount());
		return hl.getWord(randomValue);
	}
	
	//shows letter or dash
	public String visibleWord() {
		String result = "";
		for (int i=0; i<isVisible.length; i++) {
			if (isVisible[i]) {
				result += gameWord.charAt(i);
			} else { 
				result += "-";
			}
		}
		return result;
	}
    	
	//a guess and its results
	public void playTurn() {
		canvas.displayWord(visibleWord());
		println("The word now looks like this: " + visibleWord());
		
		if (guessCounter == 1) {
			println("You have " + guessCounter + " guess left.");
		} else {
			println("You have " + guessCounter + " guesses left.");
		}
		
		//input your guess
		String guess = readLine("Your guess: ");
		while (guess.length() != 1) {
			println("Please enter only 1 character.");
			guess = readLine("Your guess: ");
    		} 
   
    		if (!evaluateGuess(guess.toUpperCase())) {
    			guessCounter -= 1;
    			canvas.noteIncorrectGuess(guess.toUpperCase().charAt(0));
    			//canvas.drawBodyPart(NUMGUESS - guessCounter);	
		}
	}
    	
	//if there is a match between the letter inputted and a letter in the string 
	//(it checks each position through the whole word), then make the letter visible
	public boolean evaluateGuess(String guess) {
		boolean correctGuess = false;
		for (int i=0; i<gameWord.length(); i++) {
			//println (guess.charAt(0));
			//println (gameWord.charAt(i));
			if ((guess.charAt(0) == gameWord.charAt(i)) && !isVisible[i]) {
				isVisible[i] = true;
				correctGuess = true;
			} 
		}
		return correctGuess;		
	}
    
    //old code is commented out with "short circuiting" done instead
    public boolean wonGame() {
    	//boolean endResult = true;
    	for (int i=0; i<isVisible.length; i++) {
    		if (isVisible[i] == false) {
    		//endResult = false;
    			return false;
    		} 
    	}
    	//return endResult;
    	return true;
    }
    
    public boolean playAgain() {
    	while (true) {
    	String playResponse = readLine("Do you want to play again? (y/n): ");
    		if (playResponse.charAt(0) == 'y') {
    			return true;
    		}
    		if (playResponse.charAt(0) == 'n') {
    			return false;
    		}
    	}
    }

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
    }
}

// compiler won't evaluate after the && (it would break anyway if it did). Isn't it smart?
// if (i < length) && (array[i] != 3)
