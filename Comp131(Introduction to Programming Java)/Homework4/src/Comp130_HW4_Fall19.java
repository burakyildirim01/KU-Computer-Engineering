
//Name:BURAK YILDIRIM

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Comp130_HW4_Fall19 extends GraphicsProgram {


	/**
	 * Initializer. Prints the welcome messages. Do <b>not</b> modify.
	 */
	public void init() {
		setTitle("MakeAMovie");
		println("Welcome to Make a Movie!");
		println("Start by either creating a new project or opening an existing one.");
	}
	
	/**
	 * Entry method for your implementation.
	 */
	public void run() {
		//your code starts here 
		while(true) {
			command = readLine(CLI_INPUT_STR);
			setBack();
			setGrammar();
			addScene();
			removeScene();
			listScenes();
			play();
			playWithMusic();
			if(command.equalsIgnoreCase("exit")) {
				break;
			}
			if(!(command.equals("exit") || command.equals("playwithmusic") || command.equals("play") || command.equals("listscenes") || command.equals("removescenes") || command.equals("addscene") || command.equals("setgrammar") || command.equals("setback"))) {
				println("Unknown command!"); //for the commands other than the known ones
			}
					
				
		}
		// your code ends here
	}
	
	//ADDITIONAL HELPER METHODS//
	//your code starts here
	
	private void setBack() { //setting the background color for each case
		if(command.equalsIgnoreCase("setback")) {
			String color = readLine("Specify the background color: (White, Green, Blue, Magenta)");
			switch(color) {
			case "Blue":
				setBackground(Color.BLUE);
				break;
			case "Green":
				setBackground(Color.GREEN);
				break;
			case "White":
				setBackground(Color.WHITE);
				break;
			case "Magenta":
				setBackground(Color.MAGENTA);
				break;
			default: 
				color = readLine("Please specify the background color: (White, Green, Blue, Magenta");
				break;
			}
		}
	}
	
	private void setGrammar() { // determine the order of the grammar and find the index number for the elements which we require
		if(command.equalsIgnoreCase("setgrammar")) {
			grammar = readLine("Please specify the order of the grammar elements.\n[scale, image, from, to, time]\n ");
			order = grammar.split(" ");
			int j = 0;
			for(int i = 0; i<order.length; i++) {
				switch(order[i]) { 
				
				case "scale":
					indexScale = j;
					j++;
					break;
					
				case "image":
					indexImage = j;
					j++;
					break;
					
				case "from":
					j++;
					indexFrom = j;
					j++;
					break;
					
				case "to":
					j++;
					indexTo = j;
					j++;
					break;
					
				case "time":
					indexTimeType = j+2;
					indexTime = j+1;
					j+=3;
					break;
				
				}
			}
		}
	}
	
	private void addScene() { //adding a scene, setting up arrays, parsing, scaling, creating image
		
		if(command.equalsIgnoreCase("addscene")) {
			
			String newScene = readLine("Describe the new scene:\n");
			parsedScenes = newScene.split(" ");
			scaleMultiplier = Double.parseDouble(parsedScenes[indexScale]);
			scaleMultipliers.add(scaleMultiplier);
			GImage newImage = new GImage(LIBRARY_PATH+parsedScenes[indexImage]+IMAGE_TYPE);
			IMAGE_HEIGHT = newImage.getHeight();
			IMAGE_WIDTH = newImage.getWidth();
			imageHeights.add(IMAGE_HEIGHT);
			imageWidths.add(IMAGE_WIDTH);
			newImage.scale(scaleMultiplier);
			setTime();
			setFrom();
			setTo();			
			
			images.add(newImage);
			scenes.add(newScene);
		}
	}
	
	private void removeScene() {
		
		if(command.equalsIgnoreCase("removescenes")) { //removing a scene and its properties
			int deleteIndex = readInt("Specify the scene you want to delete: ");
			scenes.remove(deleteIndex-1);
			images.remove(deleteIndex-1);
			time.remove(deleteIndex-1);
			if(!(startPoints.isEmpty())) {
				startPoints.remove(deleteIndex-1);
			}
			if(!(startX.isEmpty())) {
				startX.remove(deleteIndex-1);
			}
			if(!(startY.isEmpty())) {
				startY.remove(deleteIndex-1);
			}
			if(!(endX.isEmpty())) {
				endX.remove(deleteIndex-1);
			}
			if(!(endY.isEmpty())) {
				endY.remove(deleteIndex-1);
			}
			imageHeights.remove(deleteIndex-1);
			imageWidths.remove(deleteIndex-1);
			from.remove(deleteIndex-1);
			to.remove(deleteIndex-1);
			scaleMultipliers.remove(deleteIndex-1);
		}
	}
	
	private void listScenes() { //listing the scenes
		
		if(command.equalsIgnoreCase("listscenes")) {
			int counter = 1;
			for(String value : scenes) {
				println(counter + ") " + value);
				counter++;
			}
		}
	}
	
	private void play() { //playing the movie
		if(command.equalsIgnoreCase("play")) {
			for(current = 0; current<scenes.size(); current++) { //animating the loop for number of scenes
				
				pointFrom(); //getting image height and width info in every loop for every single image
				pointTo();
				speedX = (endX.get(current) - startX.get(current))/time.get(current); //calculating speeds by start and ending points
				speedY = (endY.get(current) - startY.get(current))/time.get(current);
				images.get(current).setLocation(startPoints.get(current));
				add(images.get(current)); //adding the object before animating
				for(int j = 0; j<time.get(current); j++) { //repeat the loop for time info
					
					images.get(current).move(speedX, speedY);
					
					
					
					pause(PAUSE_TIME);
					
				}
				remove(images.get(current)); //removing image after doing its job
//				images.get(current).setLocation(startPoints.get(current));
			}
		}
	}
	
	private void setFrom() {
		
		switch(parsedScenes[indexFrom]) { //getting the from info from the array
		
		case "left":
			from.add("left"); //store the from data in an arraylist 
			break;
			
		case "right":
			from.add("right");
			break;
			
		case "bottom":
			from.add("bottom");
			break;
			
		case "top":
			from.add("top");
			break;

		case "center":
			from.add("center");
			break;
			
		default:
			break;
		}
		
		
		
	}
	
	private void setTo() {

		switch(parsedScenes[indexTo]) { //getting the to info from the array
		
		case "left": //store the to data in an arraylist 
			to.add("left");
			break;
			
		case "right":
			to.add("right");
			break;
			
		case "bottom":
			to.add("bottom");
			break;
			
		case "top":
			to.add("top");
			break;
			
		case "center":
			to.add("center");
			break;
			
			
		default:
			break;
		}
		
		
	}

	private void pointFrom() { //using the current from data to determine starting points for each condition
		switch(from.get(current)) { 
		
		case "left":
			xStartPos = 0;
			yStartPos = getHeight()/2 - (imageHeights.get(current)/2)*scaleMultipliers.get(current);
			startPos = new GPoint(xStartPos, yStartPos);
			startPoints.add(startPos);
			startX.add(xStartPos);
			startY.add(yStartPos);
			break;
			
		case "right":
			xStartPos = getWidth()-(imageWidths.get(current))*scaleMultipliers.get(current);
			yStartPos = getHeight()/2 - (imageHeights.get(current)/2)*scaleMultipliers.get(current);
			startPos = new GPoint(xStartPos, yStartPos);
			startPoints.add(startPos);
			startX.add(xStartPos);
			startY.add(yStartPos);
			break;
			
		case "bottom":
			xStartPos = getWidth()/2 - (imageWidths.get(current)/2)*scaleMultipliers.get(current);
			yStartPos = getHeight()-(imageHeights.get(current))*scaleMultipliers.get(current);
			startPos = new GPoint(xStartPos, yStartPos);
			startPoints.add(startPos);
			startX.add(xStartPos);
			startY.add(yStartPos);
			break;
			
		case "top":
			xStartPos = getWidth()/2 - (imageWidths.get(current)/2)*scaleMultipliers.get(current);
			yStartPos = 0;
			startPos = new GPoint(xStartPos, yStartPos);
			startPoints.add(startPos);
			startX.add(xStartPos);
			startY.add(yStartPos);
			break;
			
		case "center":
			xStartPos = getWidth()/2 - (imageWidths.get(current)/2)*scaleMultipliers.get(current);
			yStartPos = getHeight()/2 - (imageHeights.get(current)/2)*scaleMultipliers.get(current);
			startPos = new GPoint(xStartPos, yStartPos);
			startPoints.add(startPos);
			startX.add(xStartPos);
			startY.add(yStartPos);
			break;
		default:
			break;
			
		
		
		
		}
		
		
		
	}
	
	private void pointTo() { //using the current to data to determine starting points for each condition

		switch(to.get(current)) {
		
		case "left":
			xEndPos = 0;
			yEndPos = getHeight()/2 - (imageHeights.get(current)/2)*scaleMultipliers.get(current);
			endPos = new GPoint(xEndPos, yEndPos);
			endX.add(xEndPos);
			endY.add(yEndPos);
			break;
			
		case "right":
			xEndPos = getWidth()-(imageWidths.get(current))*scaleMultipliers.get(current);
			yEndPos = getHeight()/2 - (imageHeights.get(current)/2)*scaleMultipliers.get(current);
			endPos = new GPoint(xEndPos, yEndPos);
			endX.add(xEndPos);
			endY.add(yEndPos);
			break;
			
		case "bottom":
			xEndPos = getWidth()/2 - (imageWidths.get(current)/2)*scaleMultipliers.get(current);
			yEndPos = getHeight()-(imageHeights.get(current))*scaleMultipliers.get(current);
			endPos = new GPoint(xEndPos, yEndPos);
			endX.add(xEndPos);
			endY.add(yEndPos);
			break;
			
		case "top":
			xEndPos = getWidth()/2 - (imageWidths.get(current)/2)*scaleMultipliers.get(current);
			yEndPos = 0;
			endPos = new GPoint(xEndPos, yEndPos);
			endX.add(xEndPos);
			endY.add(yEndPos);
			break;
			
		case "center":
			xEndPos = getWidth()/2 - (imageWidths.get(current)/2)*scaleMultipliers.get(current);
			yEndPos = getHeight()/2 - (imageHeights.get(current)/2)*scaleMultipliers.get(current);
			endPos = new GPoint(xEndPos, yEndPos);
			endX.add(xEndPos);
			endY.add(yEndPos);
			break;
			
			
		default:
			break;
			
		
		
		
		}
		
		
		
	
		
		
		
	}
	
	private void setTime() { //determine whether the time type is seconds or milliseconds
		
		
		switch (parsedScenes[indexTimeType]) { //getting the time type data from the array
		
		case "seconds":
			double tempTime;
			tempTime = Integer.parseInt(parsedScenes[indexTime])*1000;
			time.add(tempTime);
			break;
		case "second":
			double tempTime3;
			tempTime3 = Integer.parseInt(parsedScenes[indexTime])*1000;
			time.add(tempTime3);
			break;
		case "milliseconds":
			double tempTime1;
			tempTime1 = Integer.parseInt(parsedScenes[indexTime]);
			time.add(tempTime1);
			break;
		case "millisecond":
			double tempTime2;
			tempTime2 = Integer.parseInt(parsedScenes[indexTime]);
			time.add(tempTime2);
			break;
		}
		
		
		
		
	}
	
	private void playWithMusic() { //playing the movie with music 
		if(command.equalsIgnoreCase("playwithmusic")) {
			int selectedOption = readInt("Please select a song to play with your movie."
					+ "\n1)Hangover\n2)Nights\n3)Everything Black\n");
			switch(selectedOption) {
			case 1:
				playSong("hangover.wav");
				songName = "hangover.wav";
				break;
			case 2:
				playSong("nights.wav");
				songName = "nights.wav";
				break;
				
			case 3:
				playSong("everythingblack.wav");
				songName = "everythingblack.wav";
				break;
			}
			
			
			
			
			
			for(current = 0; current<scenes.size(); current++) {
				
				pointFrom();
				pointTo();
				speedX = (endX.get(current) - startX.get(current))/time.get(current);
				speedY = (endY.get(current) - startY.get(current))/time.get(current);
				images.get(current).setLocation(startPoints.get(current));
				add(images.get(current));
				for(int j = 0; j<time.get(current); j++) {
					
					images.get(current).move(speedX, speedY);
					
					
					
					pause(PAUSE_TIME);
					
				}
				remove(images.get(current));
				images.get(current).setLocation(startPoints.get(current));
			}
			stopSong("hangover.wav");
		}
	}
	
	private void playSong(String fileLocation) { //playing song for extra method
		
		InputStream music;
		
		try {
			music = new FileInputStream(new File(fileLocation));
			AudioStream audios = new AudioStream(music);
			AudioPlayer.player.start(audios);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	private void stopSong(String fileLocation) { //stopping song for extra method
		
		InputStream music;
		
		try {
			music = new FileInputStream(new File(fileLocation));
			AudioStream audios = new AudioStream(music);
			AudioPlayer.player.stop(audios);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
	
	
	
	
	
	// your code ends here
	
	//ADDITIONAL INSTANCE AND CONSTANT VARIABLES//
	//your code starts here 
	double xStartPos, xEndPos, yStartPos, yEndPos;
	GPoint startPos, endPos;
	String command;
	String[] order;
	String[] parsedScenes;
	List<String> from = new ArrayList<String>();
	List<String> to = new ArrayList<String>();
	List<Double> scaleMultipliers = new ArrayList<Double>();
	List<Double> time = new ArrayList<Double>();
	List<Double> imageHeights = new ArrayList<Double>();
	List<Double> imageWidths = new ArrayList<Double>();
	List<Double> endX = new ArrayList<Double>();
	List<Double> endY = new ArrayList<Double>();
	List<Double> startX = new ArrayList<Double>();
	List<Double> startY = new ArrayList<Double>();
	List<GImage> images = new ArrayList<GImage>();
	List<GPoint> startPoints = new ArrayList<GPoint>();
	String songName;
	int indexTime, indexTo, indexFrom, indexScale, indexImage, indexTimeType;
	int current;
	double scaleMultiplier;
	double speedX, speedY;
	double IMAGE_HEIGHT, IMAGE_WIDTH;
	// your code ends here
	
	
	// DO NOT REMOVE THIS SECTION//

	private String grammar = "";
	private List<String> scenes = new ArrayList<String>();
	private static int PAUSE_TIME = 1;
	
	// INSTANCE VARIABLES AND CONSTANTS
	/**
	 * Constant <code>String</code> used to prompt user for commands.
	 */
	public static String CLI_INPUT_STR = "MakeAMovie -> ";

	/**
	 * Path to the folder enclosing the images. Read images from this path.
	 */
	public static String LIBRARY_PATH = "../lib/";

	/**
	 * File extension of image files.
	 */
	public static String IMAGE_TYPE = ".png";

}
