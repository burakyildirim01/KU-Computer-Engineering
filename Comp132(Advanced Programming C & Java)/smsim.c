/* THIS CODE IS MY OWN WORK. I DID NOT SEARCH FOR A SOLUTION, or I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES.
I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT.
NAME: BURAK YILDIRIM
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "../queue/queue.h"

struct customer {
    int arrTime;
    int servTime;
};


int main()
{

	time_t t;

	srand((unsigned)time(&t));

	int maxArrivalTime;
	int maxServiceTime;
	int simTime;

	// taking simTime, maxArrivalTime and maxServiceTime data from the user

	printf("Enter max simulation time, max_arrival_time, max_service_time:\nSimulation parameters: ");
	scanf("%d %d %d", &simTime, &maxArrivalTime, &maxServiceTime);
	printf("---------------\n");


	QueueNodePtr headPtr = NULL; // initialize headPtr
	QueueNodePtr tailPtr = NULL; // initialize tailPtr


	int maxCustomLine = 0;    //initializing necessary variables

	int customLine = 0;

	double maxCustomWait = 0;

	double allCustomWait = 0;

	double allServTime = 0;

	double allArrivalInterv = 0;

	int customerServed = 0;


	// arrange arrival and service time for the next customers also start calculation of allArrivalInterv
	int nextInterArr;
	int nextArrTime = 1 + rand() % maxArrivalTime;
	allArrivalInterv += nextArrTime;
	int nextServiceTime = 1 + rand() % maxServiceTime;

	int availableCashier = 0;
	int customerAmount = 0;
	int flag=0;

	for (int instantTime = 1; instantTime <= simTime || !isEmpty(headPtr) || availableCashier >= 0; instantTime++, availableCashier--)
	{
	    if(instantTime == simTime+1) {
	        printf("     %d: Max Simulation Time Reached - servicing remaining customers\n", instantTime);
	    }

		// if a new customer arrive at current time

		if (instantTime <= simTime+1 && instantTime == nextArrTime)
		{
		    printf("     %d: customer %d arrived\n",instantTime,++customerAmount);
		    // doing allocation of a customer
			struct customer *c = (struct customer *)  malloc(sizeof(struct customer));
			// arrange next arrival and next service time data to customer
			c->arrTime = nextArrTime;
			c->servTime = nextServiceTime;
			// doing insertion of a customer into the queue
			enqueue(&headPtr, &tailPtr, c);
			// adding to the total amount of customers in the line
			customLine++;
			// if the amount of customers in the line is greater than the amount of maximum customers in the line
			// we should update the amount of maximum customers in the line
			if (maxCustomLine < customLine)
			{
				maxCustomLine = customLine;
			}
			// arrange arrival and service time for the next customers
			nextInterArr = 1 + rand() % maxArrivalTime;
			nextArrTime = instantTime + nextInterArr;
			nextServiceTime = 1 + rand() % maxServiceTime;
			allArrivalInterv += nextInterArr;
		}

		// if the cashier is available
		if (availableCashier <= 0)
		{
		    if(customerServed != 0 && flag==0){
		        printf("     %d: service completed for customer %d\n", instantTime, customerServed);
		        flag=1;
			}
		    // if line is not empty
			if (!isEmpty(headPtr))
			{
			    if(customerServed != 0){
			        printf("     %d: service starts for customer %d\n", instantTime, customerServed+1);
			        flag=0;
			    }
			    // remove the customer from queue
				struct customer *c = (struct customer *) dequeue(&headPtr, &tailPtr);
				// subtracting to the total amount of customers in the line
				customLine--;
				// service to the customer
				availableCashier = c->servTime;
				// calculation of the waiting time of a customer
				int customWaitTime = instantTime - c->arrTime;
				// if customer's waiting time is greater than the customer's maximum waiting time
				// we should update the maximum waiting time of customer
				if (maxCustomWait < customWaitTime)
				{
					maxCustomWait = customWaitTime;
				}
				// adding waiting time of customer in allCustomWait
				allCustomWait += customWaitTime;
				// adding service time of customer in allServTime
				allServTime += c->servTime;
				// adding to the total amount of customer served
				customerServed++;
			}
		}
	}

	printf("\n---------------------------------------------\n");
	printf("                Simulation Statistics\n");
	printf("---------------------------------------------\n");

	// calculation of avgAllCustomWait
	double avgAllCustomWait = allCustomWait / customerServed;
	// calculation of avgAllServTime
	double avgAllServTime = allServTime / customerServed;
	// calculation of avgAllArrivalInterv
	double avgAllArrivalInterv = allArrivalInterv / customerServed;

	// finally printing all of the statistics
	printf(" Number of Customers: %d\n", customerAmount);
	printf(" Average Wait Time: %f\n", avgAllCustomWait);
	printf(" Average Service Time: %f\n", avgAllServTime);
	printf(" Maximum Wait Time: %f\n", maxCustomWait);
	printf(" Maximum Queue Length: %d\n", maxCustomLine);
	printf(" Average Arrival Interval Time of Customers: %f\n", avgAllArrivalInterv);
    printf("---------------------------------------------\n");

	return EXIT_SUCCESS;
}
