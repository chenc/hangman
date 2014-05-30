/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	private String incorrectLetters;
	private GLabel previousWord;
	private GLabel previousIncorrectWord;
	
/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		incorrectLetters = "";
		drawScaffold();
	}

	private void drawScaffold() {
		GLine scaffold = new GLine (((getWidth()/2) - BEAM_LENGTH),20,((getWidth()/2) - BEAM_LENGTH),20 + SCAFFOLD_HEIGHT);
		add (scaffold);
		GLine beam = new GLine (((getWidth()/2) - BEAM_LENGTH), 20, (getWidth()/2), 20);
		add (beam);
		GLine rope = new GLine ((getWidth()/2), 20, (getWidth()/2), 20 + ROPE_LENGTH);
		add(rope);
	}
	
/*
 * Updates the word on the screen to correspond to the current
 * state of the game. The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		
		// remove previous label
	    if (previousWord != null) {
	    	remove(previousWord);
	    }
		
		// make new label
		//location of GLabel
	    GLabel w = new GLabel(word);
	    w.setFont("Arial-20");
	    double x = getWidth()/2 - (w.getWidth()/2);
	    double y = (BEAM_OFFSET*2) + HEAD + ROPE_LENGTH + BODY_LENGTH + LEG_LENGTH + 25;
	    add(w, x, y);
	        
		// remember so I can remove it next time		
	    previousWord = w;
	}

/*
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	
	public void noteIncorrectGuess(char letter) {

		// remove previous label
	    if (previousIncorrectWord != null) {
	    	remove(previousIncorrectWord);
	    }
	    
		// make new label
	    incorrectLetters += letter;
	    GLabel l = new GLabel(incorrectLetters);
		double x = (getWidth()/2) - (l.getWidth()/2);
	    double y = BEAM_OFFSET + (HEAD *2) + ROPE_LENGTH + BODY_LENGTH + LEG_LENGTH;
		add(l, x, y);	
		drawBodyPart(incorrectLetters.length());
		
		//store so it can be removed
		previousIncorrectWord = l;
		
	}

	private void drawBodyPart(int n) {
		switch (n) {
			case 1: drawHead();
					break;
			case 2: drawBody();	
					break;
			case 3: drawLeftArm();
					break;
			case 4: drawRightArm();
					break;
			case 5: drawLeftLeg();
					break;
			case 6: drawRightLeg();
					break;
			case 7: drawLeftFoot();
					break;
			case 8: drawRightFoot();
					break;
		}
	}
	
	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int BEAM_OFFSET = 20;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int HEAD = 2 * HEAD_RADIUS;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

	private void drawHead() {
		double x = getWidth()/2;
		GOval head = new GOval (x - HEAD_RADIUS, BEAM_OFFSET + ROPE_LENGTH, HEAD, HEAD);
		add (head);
	}
	
	private void drawBody() {
        double x = getWidth()/2;
        GLine body = new GLine (x, BEAM_OFFSET + ROPE_LENGTH + HEAD, x, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH);
        add (body);
    }
	
	private void drawLeftArm() {
		double x = getWidth()/2;
		GLine leftarm = new GLine (x, BEAM_OFFSET + ROPE_LENGTH + HEAD + ARM_OFFSET_FROM_HEAD, x - UPPER_ARM_LENGTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + ARM_OFFSET_FROM_HEAD);
		GLine lefthand = new GLine (x - UPPER_ARM_LENGTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + ARM_OFFSET_FROM_HEAD, x - UPPER_ARM_LENGTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add (leftarm);
		add (lefthand);
	}
	
	private void drawRightArm() {
		double x = getWidth()/2;
		GLine rightarm = new GLine (x, BEAM_OFFSET + ROPE_LENGTH + HEAD + ARM_OFFSET_FROM_HEAD, x + UPPER_ARM_LENGTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + ARM_OFFSET_FROM_HEAD);
		GLine righthand = new GLine (x + UPPER_ARM_LENGTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + ARM_OFFSET_FROM_HEAD, x + UPPER_ARM_LENGTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add (rightarm);
		add (righthand);
	}
	
	private void drawLeftLeg() {
		double x = getWidth()/2;
		GLine leftthigh = new GLine (x, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH, x - HIP_WIDTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH);
		add (leftthigh);
		GLine leftcalf = new GLine (x - HIP_WIDTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH, x - HIP_WIDTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH + LEG_LENGTH);
		add (leftcalf);
	}
	
	private void drawRightLeg() {
		double x = getWidth()/2;
		GLine rightthigh = new GLine (x, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH, x + HIP_WIDTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH);
		add (rightthigh);
		GLine rightcalf = new GLine (x + HIP_WIDTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH, x + HIP_WIDTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH + LEG_LENGTH);
		add (rightcalf);
	}
	
	private void drawLeftFoot() {
		double x = getWidth()/2;
		GLine leftfoot = new GLine (x - HIP_WIDTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH + LEG_LENGTH, x - HIP_WIDTH - FOOT_LENGTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH + LEG_LENGTH);
		add (leftfoot);
	}
	
	private void drawRightFoot() {
		double x = getWidth()/2;
		GLine rightfoot = new GLine (x + HIP_WIDTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH + LEG_LENGTH, x + HIP_WIDTH + FOOT_LENGTH, BEAM_OFFSET + ROPE_LENGTH + HEAD + BODY_LENGTH + LEG_LENGTH);
		add (rightfoot);
	}
	
}
	
