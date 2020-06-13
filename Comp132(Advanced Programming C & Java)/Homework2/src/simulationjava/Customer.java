package simulationjava;

/*
THIS CODE IS MY OWN WORK. I DID NOT SEARCH FOR SOLUTION, or I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES. 
I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT. NAME: BURAK YILDIRIM
 */

public class Customer {

    public int id;
    public static int next_id = 0;
    public int serv_t;
    public int wait_t;
    public int custom_arr_t;
    

    //constructor for customer class.
    //each costumer will have different unique id
    //also taking the customer's arrival time
    public Customer(int custom_arr_t) {
    	id = ++next_id;
    	this.custom_arr_t = custom_arr_t;
    }
    
    //method for checking whether customer is arrived or not
    public boolean customer_arrived (int instant_t) {
        return instant_t >= custom_arr_t;
    }
    
    
    
    //method for increasing customer's wait time
    public void wait_custom() {
        wait_t++;
    }
    
   



    

}


	
	


