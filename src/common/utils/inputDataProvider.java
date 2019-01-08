package common.utils;

import org.testng.annotations.DataProvider;

public class inputDataProvider {

	@DataProvider(name = "invalidTimeFormats")
    public static String[][] invalidTimeFormats()
		{
			return new String[][] {
					{"1234567890"},
					{"SampleString"},
					{"!@#$%^&*()"},
					{"10-09-1984"},
					{"12.34"},
					{"Select * from orders;"}
			};
		}
	
	@DataProvider(name = "invalidLattitude")
    public static String[][] invalidLattitude()
		{
			return new String[][] {
					{"1234567890"},
					{".000000000034"}
				};
		}
	
	@DataProvider(name = "invalidEndPoint")
    public static String[][] invalidEndPoint()
		{
			return new String[][] {
					{"v1/order"},
					{".000000000034/orders"},
					{"1234567890/orders"},
					{"String/orders"},
					{"!@#$%^&*()/orders"},
					{"select* from orders/orders"},
					{"1234.9089/orders"}
				};
		}
	
	
	@DataProvider(name = "invalidIds")
    public static String[][] invalidIds()
		{
			return new String[][] {
					{"1234567890"},
					{"SampleString"},
					{"!@#$%^&*()"},
					{"10-09-1984"},
					{"12.34"},
					{"Select * from orders;"}
			};
		}
	
	
	@DataProvider(name = "invalidFetchEndPoint")
    public static String[][] invalidFetchEndPoint()
		{
			return new String[][] {
					{"v1/order/1"},
					{".000000000034/orders/1"},
					{"1234567890/orders/1"},
					{"String/orders/1"},
					{"!@#$%^&*()/orders/1"},
					{"select* from orders/orders/1"},
					{"1234.9089/orders/1"}
				};
		}
	
	


	@DataProvider(name = "invalidTakeEndPoint")
	public static String[][] invalidTakeEndPoint()
		{
			return new String[][] {
					{"v1/order/1/take"},
					{".000000000034/orders/1/take"},
					{"1234567890/orders/1/take"},
					{"String/orders/1/take"},
					{"!@#$%^&*()/orders/1/take"},
					{"select* from orders/orders/1/take"},
					{"1234.9089/orders/1/take"},
					{"v1/order/1/taken"}
				};
		}
	
	@DataProvider(name = "invalidCompleteEndPoint")
	public static String[][] invalidCompleteEndPoint()
		{
			return new String[][] {
					{"v1/order/1/complete"},
					{".000000000034/orders/1/complete"},
					{"1234567890/orders/1/complete"},
					{"String/orders/1/complete"},
					{"!@#$%^&*()/orders/1/complete"},
					{"select* from orders/orders/1/complete"},
					{"1234.9089/orders/1/complete"},
					{"v1/order/1/completed"}
				};
		}
	
	@DataProvider(name = "invalidCancelEndPoint")
	public static String[][] invalidCancelEndPoint()
		{
			return new String[][] {
					{"v1/order/1/cancel"},
					{".000000000034/orders/1/cancel"},
					{"1234567890/orders/1/cancel"},
					{"String/orders/1/cancel"},
					{"!@#$%^&*()/orders/1/cancel"},
					{"select* from orders/orders/1/cancel"},
					{"1234.9089/orders/1/cancel"},
					{"v1/order/1/cancelled"}
				};
		}
	
	}



