
/* STUDENT NAME:Burak Yildirim
 * File: SuperKarelBro.java
 */

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import stanford.karel.SuperKarel;

public class SuperKarelBros extends SuperKarel {

	public void run() {

		playThemeSong(THEME_SONG);

		// Your code starts here.
		treeMission();
		stairMission();
		coinMission();
		pipeMission();
		towerMission();
		reverseStairMission();
		flagMission();
		fortressMission();
		// Your code ends here.

		playVictorySong(VICTORY_SONG);
	}

	/** Helper Methods 
	 * @return */
	// Your code starts here.
	private void treeMission() {
		findObject();
		turnLeft();
		climbTree();
		turnRight();
		checkColor();
		move();
		checkColor();
		while(rightIsBlocked()) {
			checkColor();
			move();
		}
		turnRight();
		checkColor();
		move();
		checkColor();
		descendTree();
		checkColor();
		turnLeft();
	}
	
	private void stairMission() {
		findObject();
		turnLeft();
		while(rightIsBlocked()) {
			move();
		}
		turnRight();
		move();
		move();
		turnRight();
		descendStair();
	}
	
	private void coinMission() {
		while(rightIsBlocked()) {
			move();
		}
		move();
		turnLeft();
		while(cornerColorIs(CYAN)) {
			move();
		}
		
		while(noBeepersPresent()) {
			checkCoin();
			turnAround();
			findObject();
			turnLeft();
			move();
			turnLeft();
			while(cornerColorIs(CYAN)) {
				move();
			}
		}
		pickBeeper();
		turnAround();
		findObject();
		turnLeft();
		findObject();
		turnLeft();
		while(rightIsBlocked()) {
			move();
		}
		turnRight();
		move();
		turnRight();
	}
	
	private void pipeMission() { //checks if right or left is blocked in order to turn to the right side
		move();
			while(frontIsClear()) {
				while(beepersPresent()) {
					pickBeeper();
				}
				move();
			if(rightIsBlocked() && frontIsBlocked()) {
				turnLeft();
			}
			if(frontIsBlocked() && leftIsBlocked()) {
				turnRight();
		    }
			if(!cornerColorIs(GREEN)) {
				break;
			}
	  }
			turnRight();
			move();
			turnRight();
			findObject();
			turnLeft();
			while(cornerColorIs(CYAN)) {
				move();
			}
			turnLeft();
		
    }
	
	private void towerMission() { // picks beeper in front except from black tile then go to the end and turns around lastly puts to black and repeat this process.
		while(frontIsClear() || beepersPresent()) {
			detectBeeper();
			if(noBeepersPresent() && frontIsBlocked()) {
				turnAround();
				break;
			}
			reverse();
			putBlack();
			reverse();
			
		}
		putBlack();
		findObject();
		turnLeft();
		findObject();
	}
	
	private void reverseStairMission() { // repeats certain moves in certain place then if front is clear it finishes.
		turnLeft();
		move();
		turnRight();
		move();
		while(frontIsBlocked() && rightIsBlocked()) {
			turnLeft();
			move();
			turnRight();
			move();
			if(frontIsClear()) {
				break;
			}
		}
		move();
		turnRight();
		findObject();
		turnLeft();
		findObject();
		turnLeft();
		move();
		turnRight();
		move();
		move();
		turnLeft();
	}
	
	private void flagMission() { // climbs to flag by checking the color and turns around start descending also while descending it does the given missions.
		while(cornerColorIs(CYAN)) {
			move();
		}
		pickBeeper();
		turnAround();
		while(rightIsBlocked()) {
			putBeeper();
			paintCorner(BLUE);
			paintCorner(CYAN);
			pickBeeper();
			move();
		}
		turnLeft();
		move();
		turnRight();
		move();
		turnLeft();
	}
	
	private void fortressMission() { // finds the door
		while(!cornerColorIs(WHITE)) {
			move();
		}
	}
	
	private void reverse() { // helper method for tower mission
		findObject();
		turnAround();
	}

	private void putBlack() { //helper method for tower mission
		while(cornerColorIs(PINK)) {
			move();
		}
		putBeeper();
	}
	
	
	private void detectBeeper() { //it detects and picks beeper in front except from black tile
		while(true) {
			if(noBeepersPresent() && frontIsBlocked()) {
				turnAround();
				break;
			}
			if(beepersPresent() && cornerColorIs(BLACK) && frontIsClear()) {
				move();
				detectBeeper();
				break;
			}
			if(beepersPresent()) {
				pickBeeper();
				break;
			}
			move();
		}
		
	}

	

	
	private void descendStair() {
		move();
		while(frontIsBlocked() && rightIsBlocked()) {
			turnLeft();
			move();
			turnRight();
			if(frontIsBlocked()) {
				break;
			}
			move();
		}
		turnLeft();
	}
	
	private void checkColor() {
		while(cornerColorIs(RED)) {
			paintCorner(CYAN);
		}
	}
	
	private void findObject() {
		while(frontIsClear()) {
			move();
		}
	}
	private void climbTree() {
		while(rightIsBlocked()) {
		    checkColor();
			move();
		}
	}
	private void descendTree() {
		while(rightIsBlocked() && frontIsClear()) {
			checkColor();
			move();
		}
	}
	private void checkCoin() {
		if(noBeepersPresent()) {
			paintCorner(CYAN);
		} else {
			pickBeeper();
		}
	}
	// Your code ends here.

	/** ----- Do not change anything below here. ----- */

	private void playThemeSong(String fileLocation) {
		try {
			inputStream = AudioSystem.getAudioInputStream(new File(fileLocation));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void playVictorySong(String fileLocation) {
		try {
			clip.close();
			inputStream.close();
			inputStream = AudioSystem.getAudioInputStream(new File(fileLocation));
			clip.open(inputStream);
			clip.start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}


	private static final String THEME_SONG = "theme.wav";
	private static final String VICTORY_SONG = "victory.wav";
	private Clip clip;
	private AudioInputStream inputStream;

}
