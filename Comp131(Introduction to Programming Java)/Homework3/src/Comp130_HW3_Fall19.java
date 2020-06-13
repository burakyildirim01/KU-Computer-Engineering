/*|Student Name: BURAK YILDIRIM
 *|Student Number: 72849
 *|COMP 130/131 Homework #3				
 *|Fine A Car! - A Crossroads Simulation	
 */

import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.Color;
import java.awt.*;

//Ignore SuppressWarnings tag, it is irrelevant to the course.
@SuppressWarnings("serial")
public class Comp130_HW3_Fall19 extends GraphicsProgram {

	//Do not change these variables.
	/**
	 * Light objects.
	 */
	GOval vLight, hLight;
	// Additional instance variables
	//Your code starts here.
	private static final String CAR_CRASH = "crash.wav";
	boolean flag = true;
	boolean cR1 = false; //it stands for check red
	boolean cR2 = false;
	boolean cRF1 = false; //it stands for check red fine
	boolean cRF2 = false;
	boolean bF1 = false; //it stands for both fines
	boolean bF2 = false;
	boolean d1 = false; //it is used to detect no rules are violated
	boolean d2 = false;
	
	
	int counter = 0;
	int neededSteps = 0;
	int minNeededSteps = 0;
	int maxNeededSteps = 0;
	double horizontalLine=(SCREEN_WIDTH+ROAD_THICKNESS)/2;
	double verticalLine=(SCREEN_HEIGHT-ROAD_THICKNESS)/2;
	int r1 = rgen.nextInt(MIN_SPEED, MAX_SPEED);
	int r2 = rgen.nextInt(MIN_SPEED, MAX_SPEED);
	int tBlue = 0;
	int tRed = 0;
	int totalBlue = 0;
	int totalRed = 0;
	GLabel redLabel;
	GLabel blueLabel;
	GRect redCar = createCar(SCREEN_WIDTH, SCREEN_HEIGHT/2 - ROAD_THICKNESS/4 - CAR_WIDTH/2, CAR_LENGHT, CAR_WIDTH, CAR_COLOR_RED);
	GRect blueCar = createCar(SCREEN_WIDTH/2 - ROAD_THICKNESS/4 - CAR_WIDTH/2, -CAR_LENGHT, CAR_WIDTH, CAR_LENGHT, CAR_COLOR_BLUE);
	

	
	 
	//Your code ends here.
	
	public void run(){
		
		//Your code ends here.
		//Initialize the labels.
		prepareLabels(); //first setting up the labels
		
		//Initiate cars and their speeds.
		add(redCar); //adding the red car
		add(blueCar); //adding the blue car

		//Initiate light times.
		minNeededSteps = MIN_LIGHT_TIME/PAUSE_TIME; //finds minimum step needed to change the lights as time
		maxNeededSteps = MAX_LIGHT_TIME/PAUSE_TIME; //finds maximum step needed to change the lights as time
		
		//Main animation loop.
		while(!(isCollide(blueCar, redCar))) { //loop goes until blue and red collides
				
				changeLights(hLight); //changing the lights
				changeLights(vLight);
				
				if(redCar.getX() + CAR_LENGHT <= 0) { //do certain things like recreating while redCar is outside of bounds
					if(cR1) {                         //changing my flags to false for the redCar for being able to fine the cars
						cR1 = false;
					}	
					if(cRF2) {
						cRF2 = false;
					}
					if(bF2) {
						bF2 = false;
					}
					if(d2) {
						d2 = false;
					}
					redCar = createCar(SCREEN_WIDTH, SCREEN_HEIGHT/2 - ROAD_THICKNESS/4 - CAR_WIDTH/2, CAR_LENGHT, CAR_WIDTH, CAR_COLOR_RED);
					add(redCar);
					int rSpeed1 = rgen.nextInt(MIN_SPEED, MAX_SPEED); //generating random speed
					r1 = rSpeed1;
				}
				if(blueCar.getY() >= SCREEN_HEIGHT) { //do certain things like recreating while blueCar is outside of bounds
					if(cR2) {                         //changing my flags to false for the blueCar for being able to fine the cars 
						cR2 = false;
					}
					if(cRF1) {
						cRF1 = false;
					}
					if(bF1) {
						bF1 = false;
					}
					if(d1) {
						d1 = false;
					}
					blueCar = createCar(SCREEN_WIDTH/2 - ROAD_THICKNESS/4 - CAR_WIDTH/2 , -CAR_LENGHT, CAR_WIDTH, CAR_LENGHT, CAR_COLOR_BLUE);
					add(blueCar);
					int rSpeed2 = rgen.nextInt(MIN_SPEED, MAX_SPEED); //generating random speed
					r2 = rSpeed2;
				}
				if(!(blueCar.getY() >= SCREEN_HEIGHT)) { //while blueCar is in the screen it moves
					blueCar.move(0, r2);
				}
				if(!(redCar.getX() + CAR_LENGHT <= 0)) { //while redCar is in the screen it moves
					redCar.move(-r1, 0);
				}
				
				
				
				isBlueBothFine(); //these methods check for fines
				isRedBothFine();
				speedFine(); 
				isRedLightFine();
				
				if(hLight.getFillColor() == Color.GREEN && blueCar.getY()+blueCar.getHeight()>=verticalLine && r2 <= SPEED_LIMIT && !d1) {
					show(blueLabel, ""); //when no rules violated set the label empty for blueCar
					d1 = true;
				}
				
				if(vLight.getFillColor() == Color.GREEN && redCar.getX()<=horizontalLine && r1 <= SPEED_LIMIT && !d2) {
					show(redLabel, ""); //when no rules violated set the label empty for redCar
					d2 = true; 
				}
				
				
				pause(PAUSE_TIME);
				
			
		}
		//Post collision final display.
		show(redLabel,("Total fine: "+totalRed+"$")); //after collision total fines are displayed in the labels
		show(blueLabel,("Total fine: "+totalBlue+"$"));
		explosionImage();
		playSong(CAR_CRASH);
		
		//Your code ends here.
		
	}
	
	private GRect createCar(double x, double y, double width, double height, Color color) { //creates Car
		GRect car = new GRect(x, y, width, height);
        
		car.setFilled(true);
		car.setColor(color);
        
		return car;
	}
	
	
	private boolean isCollide(GRect a, GRect b) { //checks whether blue and red collided or not
		   if (a.getBounds().intersects(b.getBounds())) {
		     return true;
		   } else {
		     return false;
		   }
		 }
	
	
	private void changeLights(GOval tLight) { //changing the lights for a random amount time using flags
		
		if(flag) { //when light changes its color
			neededSteps = rgen.nextInt(minNeededSteps, maxNeededSteps);
			flag = false;
		}
		
		if(counter < neededSteps) { //count the time
			counter++;
		} else { //after counting finished change the color on light
			if(tLight.getFillColor() == Color.RED) {
				tLight.setFillColor(Color.GREEN);
			} else {
				tLight.setFillColor(Color.RED);
			}
			flag = true; //prepare flag for generating new random amount of time
			counter = 0; //prepare counter for new operation
		}
	}
	
	
	private void redFine(GRect car) { //determine which car violates the red light rule and fine them

		if(car.equals(blueCar)) {
			totalBlue += RED_LIGHT_FINE;
			show(blueLabel,("+"+RED_LIGHT_FINE+"$ Reason: Red Light"));
			playSong("akbil.wav");
		}
		if (car.equals(redCar)) {
			totalRed += RED_LIGHT_FINE;
			show(redLabel,("+"+RED_LIGHT_FINE+"$ Reason: Red Light"));
			playSong("akbil.wav");
		}
	}
	
	
	private void isRedLightFine() { //checks the conditions for red light
			
			
			if(vLight.getFillColor()==Color.RED && blueCar.getY()+blueCar.getHeight()>=verticalLine && !cRF1 && !bF1 && !d1) {
				
				redFine(blueCar);
				cRF1 = true; //stops fining process until the car re-enters to the screen
				playSong("akbil.wav");
				 
			}
			
			
			if(hLight.getFillColor()==Color.RED && redCar.getX()<=horizontalLine && !cRF2 && !bF2 && !d2) {
				
				redFine(redCar);
				cRF2 = true; //stops fining process until the car re-enters to the screen
				playSong("akbil.wav");
				
			}
		}
		
	
	private void isRedBothFine() { //checks both fine conditions and sets the label
		
		if(hLight.getFillColor()==Color.RED && redCar.getX()<=horizontalLine && !bF2 && r1>SPEED_LIMIT) {
			totalRed += (int)((r1-SPEED_LIMIT)*SPEED_FINE)+RED_LIGHT_FINE;
			tRed = (int)(((r1-SPEED_LIMIT)*SPEED_FINE)+RED_LIGHT_FINE);
			show(redLabel,("+"+tRed+"$ Reason: Speeding & red light"));
			bF2 = true; //stops fining process until the car re-enters to the screen
			playSong("akbil.wav");
			
		}
	}
	
	
	private void isBlueBothFine() { //checks both fine conditions and sets the label
		
		if(vLight.getFillColor()==Color.RED && blueCar.getY()+blueCar.getHeight()>=verticalLine && !bF1 && r2>SPEED_LIMIT) {
			totalBlue += (int)((r2-SPEED_LIMIT)*SPEED_FINE)+RED_LIGHT_FINE;
			tBlue = (int)(((r2-SPEED_LIMIT)*SPEED_FINE)+RED_LIGHT_FINE);
			show(blueLabel,("+"+tBlue+"$ Reason: Speeding & red light"));
			bF1 = true; //stops fining process until the car re-enters to the screen
			playSong("akbil.wav");
			
		}
	}
	
	private void speedFine() {
		
		if(r1 > SPEED_LIMIT && !cR1 && !bF2 && redCar.getX()<=horizontalLine) { //conditions for speedfine
			totalRed += (int)(r1-SPEED_LIMIT)*SPEED_FINE; //calculation given in pdf
			show(redLabel,("+"+(int)(r1-SPEED_LIMIT)*SPEED_FINE+"$ Reason: Speeding")); //changing the label
			cR1 = true; //stops fining process until the car re-enters to the screen
			playSong("akbil.wav");
		}
		
		if(r2 > SPEED_LIMIT && !cR2 && !bF1 && blueCar.getY()+blueCar.getHeight()>=verticalLine) { //conditions for speedfine
			totalBlue += (int)(r2-SPEED_LIMIT)*SPEED_FINE; //calculation given in pdf
			show(blueLabel,("+"+(int)(r2-SPEED_LIMIT)*SPEED_FINE+"$ Reason: Speeding")); //changing the label
			cR2 = true; //stops fining process until the car re-enters to the screen
			playSong("akbil.wav");
		}
	}
	
	
	private void show(GLabel label, String str){ //locates and changes the string in the label
		label.setLabel(str);
		locateLabels();
	}
	
	
	private void locateLabels() { //locates the labels depending on the string length
		redLabel.setLocation(SCREEN_WIDTH-redLabel.getWidth()-LABEL_X_MARGIN,LABEL_Y_MARGIN);
		blueLabel.setLocation(LABEL_X_MARGIN, LABEL_Y_MARGIN);
	}
	
	
	
	private void prepareLabels(){ //setting up the labels and the properties of them
		redLabel=new GLabel("");
		redLabel.setFont(LABEL_FONT);
		redLabel.setColor(CAR_COLOR_RED);
		blueLabel=new GLabel("");
		blueLabel.setFont(LABEL_FONT);
		blueLabel.setColor(CAR_COLOR_BLUE);
		locateLabels();
		add(redLabel);
		add(blueLabel);
	}	
	
	private void playSong(String fileLocation) {
		
		/* InputStream music;
		
		try {
			music = new FileInputStream(new File(fileLocation));
			AudioStream audios = new AudioStream(music);
			AudioPlayer.player.start(audios);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		 */
		print("");

	}
	
	private void explosionImage() {
		GImage explosion = new GImage("explosion.png");
		explosion.setSize(2*CAR_LENGHT, 2*CAR_LENGHT);
		explosion.setLocation(SCREEN_WIDTH/2 - ROAD_THICKNESS/2 - CAR_LENGHT/4, SCREEN_HEIGHT/2);
		add(explosion);
	}
	
	
	static RandomGenerator rgen = new RandomGenerator(); //allows to generate random values
	
	/*
	 * DO NOT change anything below this line!
	 */
	
	/** 
	 * Initialization method. This method is guaranteed to run before run().
	 */
	public void init(){
		
		//Initialize the screen size.
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//Set the background color. 
		this.setBackground(new Color(0,128,0));
		
		//Construct the roads.
		constructRoads();
		
		//Place the lights.
		placeLights();
		
	}
	
	/**
	 * This method constructs the lanes and the intersection.
	 */
	public void constructRoads(){
		
		//Road and crossing objects.
		GRect vRoad, hRoad, crossing;
		
		//Vertical and horizontal road creation.
		vRoad = createRoad(SCREEN_WIDTH/2, ROAD_THICKNESS, "v");
		hRoad = createRoad(SCREEN_HEIGHT/2, ROAD_THICKNESS, "h");
		add(vRoad);
		add(hRoad);
		
		//Square crossing section.
		crossing = new GRect(SCREEN_WIDTH/2-ROAD_THICKNESS/2, SCREEN_HEIGHT/2-ROAD_THICKNESS/2, ROAD_THICKNESS, ROAD_THICKNESS);
		crossing.setColor(new Color(255,255,255));
		add(crossing);
		
		//Lane separator lines.
		for(int i = 0; i<SCREEN_WIDTH/2-ROAD_THICKNESS/2; i+=25){
			GLine line = new GLine(i, SCREEN_HEIGHT/2, i+15, SCREEN_HEIGHT/2);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = SCREEN_WIDTH; i>SCREEN_WIDTH/2+ROAD_THICKNESS/2; i-=25){
			GLine line = new GLine(i, SCREEN_HEIGHT/2, i-15, SCREEN_HEIGHT/2);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = 0; i<SCREEN_HEIGHT/2-ROAD_THICKNESS/2; i+=25){
			GLine line = new GLine(SCREEN_WIDTH/2, i, SCREEN_WIDTH/2, i+15);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = SCREEN_HEIGHT; i>SCREEN_HEIGHT/2+ROAD_THICKNESS/2; i-=25){
			GLine line = new GLine(SCREEN_WIDTH/2, i, SCREEN_WIDTH/2, i-15);
			line.setColor(Color.WHITE);
			add(line);
		}
		
	}
	
	/**
	 * This method creates and places the light objects.
	 * @see See vLight and hLight for the light objects.
	 */
	public void placeLights(){
		
		//Create and place the vertical light.
		vLight = new GOval(25, 25);
		vLight.setFilled(true);
		vLight.setFillColor(Color.RED);
		add(vLight, SCREEN_WIDTH/2-75-3, SCREEN_HEIGHT/2-100);
		
		//Create and place the horizontal light.
		hLight = new GOval(25, 25);
		hLight.setFilled(true);
		hLight.setFillColor(Color.RED);
		add(hLight, SCREEN_WIDTH/2+75, SCREEN_HEIGHT/2-75-3);
		
	}
	
	/**
	 * @param center Center of the road to be built.
	 * @param width Thickness of the road.
	 * @param dir Direction of the road. Allowed values: "v", "h"
	 * @throws RuntimeException on invalid orientation.
	 * @return Created road as a GRect.
	 * @see constructRoads()
	 */
	public GRect createRoad(int center, int width, String dir){
		
		//Use dir to determine the road orientation.
		if(dir.equals("v")){
			
			//Create the road object.
			GRect road = new GRect(center-width/2, 0, width, SCREEN_HEIGHT);
			
			//Set border color.
			road.setColor(new Color(0,0,0,0));
			
			//Set fill color.
			road.setFilled(true);
			road.setFillColor(new Color(50,50,50));
			
			//Return the created road object.
			return road;
			
		}else if (dir.equals("h")){
			
			//Create the road object.
			GRect road = new GRect(0, center-width/2, SCREEN_WIDTH, width);
			
			//Set border color.
			road.setColor(new Color(0,0,0,0));
			
			//Set fill color.
			road.setFilled(true);
			road.setFillColor(new Color(50,50,50));
			
			//Return the created road object.
			return road;
			
		}else{
			
			//Ignore throw keyword, it is irrelevant to the course.
			throw new RuntimeException("Invalid argument 'orientation' = '" + dir + "' @ drawRoad.");
			
		}
	}
	

	/**
	 * Width of the screen.
	 */
	public static final int SCREEN_WIDTH = 800;
	
	/**
	 * Height of the screen.
	 */
	public static final int SCREEN_HEIGHT = 600;
	
	/**
	 * Thickness of the roads constructed on the screen.
	 */
	public static final int ROAD_THICKNESS = 100;
	
	/**
	 * Width of the cars.
	 */
	public static final int CAR_WIDTH = 30;
	
	/**
	 * Lenght of the cars.
	 */
	public static final int CAR_LENGHT = 70;
	
	/**
	 * Color for the blue car.
	 */
	public static final Color CAR_COLOR_BLUE = new Color(0,0,192);
	
	/**
	 * Color for the red car.
	 */
	public static final Color CAR_COLOR_RED = new Color(192,0,0);
	
	/**
	 * Minimum speed allowed for the cars.
	 */
	public static final int MIN_SPEED = 5;
	
	/**
	 * Maximum speed allowed for the cars.
	 */
	public static final int MAX_SPEED = 30;
	
	/**
	 * Speed limit for the cars
	 */
	public static final int SPEED_LIMIT = 20;
	
	/**
	 * Unit fine used to calculate speeding fines.
	 */
	public static final int SPEED_FINE = 10;
	
	/**
	 * Static fine for passing a red light.
	 */
	public static final int RED_LIGHT_FINE = 100;
	
	/**
	 * Pause time for the animation.
	 */
	public static final int PAUSE_TIME = 50;
	
	/**
	 * Minimum time for a traffic light to change.
	 */
	public static final int MIN_LIGHT_TIME = 1000;
	
	/**
	 * Maximum time for a traffic light to change. 
	 */
	public static final int MAX_LIGHT_TIME = 5000;
	
	/**
	 * Font for use in labels.
	 */
	public static final Font LABEL_FONT = new Font("Courier", Font.PLAIN, 15);
	
	/**
	 * Margin of fine labels for the Y-axis.
	 */
	public static final int LABEL_Y_MARGIN = 20;
	
	/**
	 * Margin of fine labels for the X-axis.
	 */
	public static final int LABEL_X_MARGIN = 10;

}
