/test-output/Suite1/all-tests.html# REST API Challenge

We have APIs to process orders and require you to make sure it's functioning correctly with black box testing. The API does these tasks:
1.	Place Order
2.	Fetch Order Details
3.	Driver to Take the Order
4.	Driver to Complete the Order
5.	Cancel Order

## Getting Started

Order Flow

An order has these statuses in sequence: ASSIGNING => ONGOING => COMPLETED or CANCELLED
•	ASSIGNING: looking for a driver to be assigned
•	ONGOING: a driver has been assigned and working on the order
•	COMPLETED: the driver has completed the order
•	CANCELLED: the order was cancelled

graph TD;    ASSIGNING-->ONGOING;
             ASSIGNING-->CANCELLED;
             ONGOING-->COMPLETED;
             ONGOING-->CANCELLED;
	
## Prerequisites
Configure Github repository Project in Eclipse.

## Installing
External Jars from the external_jars folder under root directory should be added in Java Build Path and TestNG Library should be added.

## Running the tests
Go to the root directory under RestApiChallenge browse your testNG.xml  right click and select Run as >>run TestNG Suite 

## To view Report 
Go to the root directory under RestApiChallenge/test-output/Suite1/all-tests.html