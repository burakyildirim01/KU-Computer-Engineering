package simulationjava;

/*
THIS CODE IS MY OWN WORK. I DID NOT SEARCH FOR SOLUTION, or I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES. 
I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT. NAME: BURAK YILDIRIM
 */

import java.util.LinkedList;

public class CustomerQueue {

  public static LinkedList<Customer> c_queue = new LinkedList<Customer>();
  
  public int max_custom_line = 0;
  public int total_wait_t = 0;
  public int max_wait_t = 0;

  //method for enqueue the customer
  public void enqueue(Customer c) {

    c_queue.add(c);

  }

  //method for dequeue the customer and checks if queue is empty or not
  public void dequeue() {

    find_max_wait();
    custom_amount_queue();
    if (!(c_queue.size()==0)) {
      total_wait_t += c_queue.get(0).wait_t;
      c_queue.remove();

    }
  }

  //method for increasing the customer's wait time in the queue except the first customer
  public void add_queue_wait() {
    for (Customer c : c_queue) {
      if (c == c_queue.get(0)) 
        continue;
      
      c.wait_custom();

    }
  }
  
  //method for determining the customer amount in the queue
  public void custom_amount_queue() {
    int customer_in_line = c_queue.size();

    if (customer_in_line > max_custom_line) {
      max_custom_line = customer_in_line;
    }
  }
  
  
  
  //method for calculating the average wait time
  public double find_average_wait() {
    return (double)total_wait_t/Cashier.total_custom;
  }
  
  
  //method for finding the max wait time
  public void find_max_wait() {

	    for (Customer c : c_queue) {
	      int instant_wait_t = c.wait_t;
	      
	      
	      if (max_wait_t == 0) {
		        max_wait_t = instant_wait_t;
		  }
	      if (instant_wait_t > max_wait_t) {
		        max_wait_t = instant_wait_t;
		  }
	    }

  }
  
  
  
  
  
  
}
