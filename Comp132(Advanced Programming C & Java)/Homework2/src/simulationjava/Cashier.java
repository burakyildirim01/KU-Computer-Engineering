package simulationjava;

/*
THIS CODE IS MY OWN WORK. I DID NOT SEARCH FOR SOLUTION, or I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES. 
I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT. NAME: BURAK YILDIRIM
 */

public class Cashier {

  public static int total_custom = 0;
  public static int total_serv_t = 0;
  int instant_t_served = 0;
  public boolean serve_complete = false;

  //constructor of cashier class
  public Cashier() {

  }
  
//method for servicing customer
  public void do_service(Customer c) {
	    instant_t_served++;

	    if (c.serv_t > instant_t_served) {
	      serve_complete = false;
	    } else {
	      serve_complete = true;
	      instant_t_served = 0;
	    }
  }
  
  //method for calculating average service time
  public double find_average_serv_t() {
    return (double)total_serv_t / total_custom;
  }
  
  



  

}

