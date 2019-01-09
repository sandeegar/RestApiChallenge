# REST API Challenge

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
Install maven 4.0.0 into your system.

## Running the tests
Go to the root directory under RestApiChallenge browse your testNG.xml  right click and select Run as >>run TestNG Suite
 
To run each Test class individually go to the class under com.rest.api package, select the test class, right click and select Run as >>run TestNG Test  

## To view Report 
Go to the root directory under RestApiChallenge/test-output/Rest-API-Testing/Regression-Suite.html

## Important Note
There is performance issue on the hosted server of the application as there are list of 87 API tests which have been automated including positive and negative scenarios. So Application crashes after executing 19 tests passed and for the remaining tests it shows HTTP 503 Service Unavailable error.

## Test Summary
While Executing each Test class individually mentioned in testng.xml  out of 87 API tests 6 tests are failing due to the anomaly in the application which are as below :

### test_04_verify_order_not_placed_with_incorrect_location_of_one_stop
Test class: com.rest.api.PostApi
Parameters: 1234567890 
java.lang.AssertionError: 
Expected: is <400>
     but: was <503>
     
### test_04_verify_order_not_placed_with_incorrect_location_of_one_stop
Test class: com.rest.api.PostApi
Parameters: .000000000034 
java.lang.AssertionError: 
Expected: is <400>
     but: was <503>
      
### test_05_verify_order_not_placed_with_incorrect_locations_of_stops
Test class: com.rest.api.PostApi
Parameters: 1234567890 
java.lang.AssertionError: 
Expected: is <400>
     but: was <503>
 
### test_05_verify_order_not_placed_with_incorrect_locations_of_stops
Test class: com.rest.api.PostApi
Parameters: .000000000034 
java.lang.AssertionError: 
Expected: is <400>
     but: was <503>
     
### test_08_verify_order_not_placed_with_identical_two_stops
Test class: com.rest.api.PostApi 
java.lang.AssertionError: 
Expected: is <400>
     but: was <503>

### test_04_verify_order_not_cancelled_an_already_cancelled_order_with_specific_order_id
Test class: com.rest.api.PutApiCancelOrder 
java.lang.AssertionError: 
Expected: is <400>
     but: was <200>
       
   

