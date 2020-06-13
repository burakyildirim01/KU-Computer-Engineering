/** Student Name:BURAK YILDIRIM
 * This is a console program designed to conduct an audition for an architecture
 * competition. Four contestants are competing against each other given their 
 * education, experience and their conciousness about environment. 
 * 
**/

import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

public class KUArchitect extends ConsoleProgram {

	public void run() {
		// your code starts here
		// this for loop goes through all KUarchitects
		for(currentKUArchitectID = 1; currentKUArchitectID <= CONTESTANT_NUM; currentKUArchitectID++ ) {
			println("NEW CONTESTANT: ");
			int point = pointCalculator(askKnowledge(), askExperience(), askAwards(), hasRecycledMaterial());
			assignPoint(point);
			println("KUArchitect# " + currentKUArchitectID + " has reached " + point + " points.");
			println(" ");
			println("------------------------------------------------");
			println(" ");
			println(" ");
		}
		comparator(); //do the comparing part 
		
		finalePart(); //declare the winner by their tournament
		// your code ends here
		
	}

	////////////GIVEN HELPER METHODS /////////////////////
	// You need to implement the given helper methods ///
	// You ARE NOT ALLOWED to change the signature of the given methods.
	
	/**
	 * This methods asks the number of year of education and number of year of experience. 
	 * It will keep on asking user input until a valid input is read.
	 * @return - returns the knowledge point
	 */
	private double askKnowledge() {
		// your code starts here
		int educationYear = readInt("Years of education of KUArchitect#" + currentKUArchitectID + ": ");
		while ((educationYear < 4 || educationYear > 6)) {
				educationYear = readInt("Enter a valid number of education year by KUArchitect#" + currentKUArchitectID + ": ");
		}
		int experienceYear = readInt("Years of experience of KUArchitect#" + currentKUArchitectID + ": ");
		while ((experienceYear < 2 || experienceYear > 10)) {
				experienceYear = readInt("Enter a valid number of experience year by KUArchitect#" + currentKUArchitectID + ": ");
			}
		
		// your code ends here
	
		return calculateKnowledge(educationYear, experienceYear);
		
		}
	

	/**
	 * This method calculates the knowledge point given the number of year of education 
	 * and the number of year of experience
	 * @param yearsEducation - the number of year of education
	 * @param yearsExperience - the number of year of experience
	 * @return - knowledge point
	 */
	private double calculateKnowledge(int yearsEducation, int yearsExperience) {
		//calculations given in pdf
		// your code starts here
		double KnowledgePoint = Math.pow((yearsEducation + yearsExperience)/5., yearsExperience-2)+fibonacci(yearsEducation);
		return KnowledgePoint;
		// your code ends here
	}

	

	/**
	 * This methods asks the number of projects and number of different projects. 
	 * It will keep on asking user input until a valid input is read.
	 * @return - returns the experience point
	 */
	private int askExperience() { //ask for experience with bounds
		int projectNumber = readInt("Number of projects of KUArchitect#" + currentKUArchitectID + ": ");
		while ((projectNumber < 4 || projectNumber > 15)) {
				projectNumber = readInt("Enter a valid number of projects by KUArchitect#" + currentKUArchitectID + ": ");
			
		}
		int projectTypes = readInt("Number of different projects of KUArchitect#" + currentKUArchitectID + ": ");
		while ((projectTypes < 2 || projectTypes > 5)) {
				projectTypes = readInt("Enter a valid number of different projects by KUArchitect#" + currentKUArchitectID + ": ");
		}
		return calculateExperience(projectNumber, projectTypes);
		// your code ends here

	}
	/**
	 * This method calculates the experience point given the number of projects
	 * and the different projects worked on.
	 * @param numProjects - number of projects
	 * @param numDifProjects - number of different projects
	 * @return - experience point
	 */
	private int calculateExperience(int numProjects, int numDifProjects) { 
		//calculations given in pdf
		// your code starts here
		int ExperiencePoint = numProjects * factorial(numDifProjects);
		return ExperiencePoint;
		// your code ends here
	}


	/**
	 * This methods asks the number of awards received.
	 * It will keep on asking user input until a valid input is read.
	 * @return - number of awards
	 */
	private int askAwards() { //ask for awards with bounds
		// your code starts here
		int awardNumber = readInt("Number of awards of KUArchitect#" + currentKUArchitectID + ": ");
		while ((awardNumber < 1 || awardNumber > 5)) {
			awardNumber = readInt("Number of awards of" + currentKUArchitectID);
		}
		return awardNumber;
		// your code ends here
	}

	/**
	 * This method decides which architect gets the recycled materials.
	 * @return - whether the architect received a recycled material or not.
	 */
	private boolean hasRecycledMaterial() {
		// your code starts here
		return rgen.nextBoolean(); //gives true or false with equal probability
		// your code ends here
	}
	
	/**
	 * This method calculates the total points of a KUArchitect
	 * @param knowledge - the knowledge point of a KUArchietct
	 * @param experience - the experience point of a KUArchietct
	 * @param awards - the number of awards of a KUArchietct
	 * @param hasRecycledMaterial - whether KUArchiects received recyled material or not.
	 * @return - the total point of a KUArchitect
	 */
	private int pointCalculator(double knowledge, int experience,  int awards, boolean hasRecycledMaterial) {
		//calculates total point
		// your code starts here
		double TotalPoint; 
		if (hasRecycledMaterial == true) { //calculations given in pdf document
			TotalPoint = (Math.sqrt(awards*experience)) + (1.3 * knowledge);	
		} else {
			TotalPoint = (Math.sqrt(awards*experience)) + (0.9 * knowledge);
		}
		return roundNumber(TotalPoint);
		// your code ends here
	}
	
	private int fibonacci(int count) { //finds the value in terms of fibonacci sequence
		int n1=0, n2=1, n3 = 0, i;
		for(i = 1; i<count; i++ ) {
			n3 = n1 + n2;
			n1 = n2;
			n2 = n3;
		}
		return n3;
	}
	
	private int factorial(int num) { //calculates the factorial of a number
		int i, fact=1;
		for(i=1;i<=num;i++) {
			fact = fact*i;
		}
		return fact;
	}

	/**
	 * This method assigns the total point calculatef for a KUArchitects and assigns to the correct KUArchitect
	 * @param p - the total point of any given KUArchitect
	 */
	private void assignPoint(int p) { //assigns points to the related variables
		// your code starts here
		switch (currentKUArchitectID) {
		case 1: totalPointOfKUArchitect1 = p; break;
		case 2: totalPointOfKUArchitect2 = p; break;
		case 3: totalPointOfKUArchitect3 = p; break;
		case 4: totalPointOfKUArchitect4 = p; break;
		}
		// your code ends here
	}

	/**
	 * This method compares the total point of all contestants and prints
	 * the first, secong and third order of the KUArchitect according to their total points.
	 */
	private void comparator() { /* compares 4 architects and finds the rankings among them
	then assigns first, second and third ID s to related values. */
		// your code starts here
		
		int a = totalPointOfKUArchitect1;
		int b = totalPointOfKUArchitect2;
		int c = totalPointOfKUArchitect3;
		int d = totalPointOfKUArchitect4;
		
		int amax = Math.max(Math.max(a, b), Math.max(c, d)); //finds the maximum total points of the architects.
		firstPoint = amax;
		int amin = Math.min(Math.min(a, b), Math.min(c, d)); //finds the minimum total points of the architects.
		if (amax == a) { //finds the second point 
			secondPoint = Math.max(b, Math.max(c, d));
		}
		if (amax == b) {
			secondPoint = Math.max(a, Math.max(c, d));
		}
		if (amax == c) {
			secondPoint = Math.max(b, Math.max(a, d));
		}
		if (amax == d) {
			secondPoint = Math.max(b, Math.max(c, a));
		}
		
		
		if (amin == a) { //finds the third point
			thirdPoint = Math.min(b, Math.min(c, d));
		}
		if (amin == b) {
			thirdPoint = Math.min(a, Math.min(c, d));
		}
		if (amin == c) {
			thirdPoint = Math.min(b, Math.min(a, d));
		}
		if (amin == d) {
			thirdPoint = Math.min(b, Math.min(c, a));
		}
		
		
		 if (firstPoint == totalPointOfKUArchitect1) { //finds the number of first Architect
			firstID = 1;
		} else if(firstPoint == totalPointOfKUArchitect2) {
			firstID = 2;
		} else if(firstPoint == totalPointOfKUArchitect3){
			firstID = 3;
		} else if(firstPoint == totalPointOfKUArchitect4) {
			firstID = 4;
		}
		if (secondPoint == totalPointOfKUArchitect1 && firstID != 1) { //finds the number of second Architect
			secondID = 1;
		} else if(secondPoint == totalPointOfKUArchitect2 && firstID != 2) {
			secondID = 2;
		} else if(secondPoint == totalPointOfKUArchitect3 && firstID != 3){
			secondID = 3;
		} else if(secondPoint == totalPointOfKUArchitect4 && firstID != 4) {
			secondID = 4;
		}
		if (thirdPoint == totalPointOfKUArchitect1 && firstID != 1 && secondID != 1) { //finds the number of third Architect
			thirdID = 1;
		} else if(thirdPoint == totalPointOfKUArchitect2 && firstID != 2 && secondID != 2) {
			thirdID = 2;
		} else if(thirdPoint == totalPointOfKUArchitect3 && firstID != 3 && secondID != 3){
			thirdID = 3;
		} else if(thirdPoint == totalPointOfKUArchitect4 && firstID != 4 && secondID != 4) {
			thirdID = 4;
		} 
		
		
		println("KUArchitect#" + firstID + " becomes #1 with " +firstPoint);
		println("KUArchitect#" + secondID + " becomes #2 with " + secondPoint);
		println("KUArchitect#" + thirdID + " becomes #3 with " + thirdPoint);
		println(" ");
		// your code ends here
	}
	
	private int roundNumber(double a) { /* rounds the number by checking whether
	numbers decimal part is equal or greater than 5 or lower than 5. */
		int b;
		double decimalNumber = a % 1;
		if (decimalNumber >= 0.5) {
			b = (int) (a)+1;
		} else {
			b = (int) (a);
		}
		return b;
	}
	
	private void finalePart() { // let the architects duel
		
		println("The game is now completed and the scores are as below: ");
		println(" ");
		
		int ku1 = 0;
		int ku2 = 0;
		int ku3 = 0;
		
		double a1 = firstPoint;
		double a2 = secondPoint;
		double a3 = thirdPoint;
		
		
		for(int a=0; a<NTIMES; a++) {
			boolean ku2vku3 = rgen.nextBoolean((a2/(a2+a3))); // duel of second and third
			//the function above gives a random true value with the probability of second/second+third
			if(ku2vku3) {
				ku2++;
			} else {
			    ku3++;
			}
		}
		for(int b=0; b<NTIMES; b++) {
			boolean ku1vku2 = rgen.nextBoolean((a1/(a1+a2))); // duel of first and second
			//the function above gives a random true value with the probability of first/first+second
			if(ku1vku2) {
				ku1++;
			} else {
			    ku2++;
			}
		}
		for(int c=0; c<NTIMES; c++) {
			
			boolean ku1vku3 = rgen.nextBoolean((a1/(a1+a3))); // duel of first and third
			//the function above gives a random true value with the probability of first/first+third
			if(ku1vku3) {
				ku1++;
			} else {
			    ku3++;
			}
		}
		
		println("KUArchitect#"+firstID+" won " +ku1+ " times in 300 games.");
		println("KUArchitect#"+secondID+" won " +ku2+ " times in 300 games.");
		println("KUArchitect#"+thirdID+" won " +ku3+ " times in 300 games.");
		println(" ");
		print("CONGRATULATIONS KUArchitect#");
		declareWinner(ku1,ku2,ku3);
		print("!! YOU ARE THE WINNER OF KUArchitect.");
	}

	////////////ADDITIONAL HELPER METHODS /////////////////////
	// Feel free to add additional helper methods
	// your code starts here
	private void declareWinner(int a, int b, int c) { //finds the winners ID by comparing top three architects
		int winnerPoint = Math.max(a, Math.max(c,b));
		if (winnerPoint == a) {
			print(firstID);
		}
		if (winnerPoint == b) {
			print(secondID);
		}
		if (winnerPoint == c) {
			print(thirdID);
		}
	}
	// your code ends here
	
	//////////// GIVEN VARIABLES and CONSTANTS /////////////////////
	int currentKUArchitectID = 1;

	int totalPointOfKUArchitect1;
	int totalPointOfKUArchitect2;
	int totalPointOfKUArchitect3;
	int totalPointOfKUArchitect4;
	
	
	int firstID;
	int secondID;
	int thirdID;

	int firstPoint;
	int secondPoint;
	int thirdPoint;
	

	
	private final int CONTESTANT_NUM = 4;
	private final int NTIMES = 100;
	static RandomGenerator rgen = new RandomGenerator();
	////////////ADDITIONAL VARIABLES and CONSTANTS  /////////////////////
	// Feel free to add additional variables and constants 
	// your code starts here
	
	// your code ends here

}