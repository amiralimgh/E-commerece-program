//Amirali Malekghasemi
//Student #501139438

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args)
	{
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");
		
		// Process keyboard actions
		while (scanner.hasNextLine()) {
			try {
				String action = scanner.nextLine();

				if (action == null || action.equals("")) {
					System.out.print("\n>");
					continue;
				} else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				else if (action.equalsIgnoreCase("PRODS"))    // List all products for sale
				{
					amazon.printAllProducts();
				} else if (action.equalsIgnoreCase("BOOKS"))    // List all books for sale
				{
					amazon.printAllBooks();
				} else if (action.equalsIgnoreCase("CUSTS"))    // List all registered customers
				{
					amazon.printCustomers();
				} else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
				{
					amazon.printAllOrders();
				} else if (action.equalsIgnoreCase("SHIPPED"))    // List all orders that have been shipped
				{
					amazon.printAllShippedOrders();
				} else if (action.equalsIgnoreCase("NEWCUST"))    // Create a new registered customer
				{
					String name = "";
					String address = "";

					System.out.print("Name: ");
					if (scanner.hasNextLine())
						name = scanner.nextLine();

					System.out.print("\nAddress: ");
					if (scanner.hasNextLine())
						address = scanner.nextLine();

					amazon.createCustomer(name, address);

				} else if (action.equalsIgnoreCase("SHIP"))    // ship an order to a customer
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					// Get order number from scanner
					if (scanner.hasNextLine()) {
						orderNumber = scanner.nextLine();
					}

					amazon.shipOrder(orderNumber);


					// Ship order to customer (see ECommerceSystem for the correct method to use
				} else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer id
				{
					String customerId = "";

					System.out.print("Customer Id: ");
					// Get customer Id from scanner
					if (scanner.hasNextLine()) {
						customerId = scanner.nextLine();
					}

					// Print all current orders and all shipped orders for this customer
					amazon.printOrderHistory(customerId);

				} else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
				{
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					// Get product Id from scanner
					if (scanner.hasNextLine()) {
						productId = scanner.nextLine();
					}

					System.out.print("\nCustomer Id: ");
					// Get customer Id from scanner
					if (scanner.hasNextLine()) {
						customerId = scanner.nextLine();
					}


					// Order the product. Check for valid orderNumber string return and for error message set in ECommerceSystem
					String order = amazon.orderProduct(productId, customerId, "NormalProduct");
					System.out.println(order);
				} else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
				{
					String productId = "";
					String customerId = "";
					String options = "";

					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine()) {
						productId = scanner.nextLine();
					}
					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine()) {
						customerId = scanner.nextLine();
					}

					System.out.print("\nFormat [Paperback Hardcover EBook]: ");
					// get book format and store in options string
					if (scanner.hasNextLine()) {
						options = scanner.nextLine();
					}

					// Order product. Check for error mesage set in ECommerceSystem
					String order = amazon.orderProduct(productId, customerId, options);
					System.out.println(order);

				} else if (action.equalsIgnoreCase("ORDERSHOES")) // order shoes for a customer, provide size and color
				{
					String productId = "";
					String customerId = "";
					String options = "";

					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine()) {
						productId = scanner.nextLine();
					}
					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine()) {
						customerId = scanner.nextLine();
					}
					System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
					// get shoe size and store in options
					if (scanner.hasNextLine()) {
						options = scanner.nextLine();
					}

					System.out.print("\nColor: \"Black\" \"Brown\": ");
					// get shoe color and append to options
					if (scanner.hasNextLine()) {
						options = options + " " + scanner.nextLine();
					}
					//order shoes
					String order = amazon.orderProduct(productId, customerId, options);
					System.out.println(order);

				} else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					// get order number from scanner
					if (scanner.hasNextLine()) {
						orderNumber = scanner.nextLine();
					}
					// cancel order. Check for error
					if (!(amazon.cancelOrder(orderNumber))) {
						System.out.println(amazon.errMsg);
					}
				} else if (action.equalsIgnoreCase("PRINTBYPRICE")) // sort products by price
				{
					ArrayList<Product> products = amazon.sortByPrice();
					for (Product p: products)
					{
						p.print();
					}
				} else if (action.equalsIgnoreCase("PRINTBYNAME")) // sort products by name (alphabetic)
				{
					ArrayList<Product> products = amazon.sortByName();
					for (Product p: products)
					{
						p.print();
					}
				} else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
				{
					amazon.sortCustomersByName();
				}

				//This is the bonus
				else if (action.equalsIgnoreCase("BooksByAuthor")) // sort products by price
				{
					String author = "";

					System.out.print("Author Name: ");
					// get order number from scanner
					if (scanner.hasNextLine()) {
						author = scanner.nextLine();
					}
					amazon.booksOfAuthorYear(author);
				}


				/*

				This is A2
				.
				.
				.
				.
				.
				.
				.
				.
				.
				.
				 */


				else if (action.equalsIgnoreCase("ADDTOCART")) // List all the current orders and shipped orders for this customer id
				{
					String productId = "";
					String customerId = "";
					String options = "";
					String itemType = "";

					//This is the addtocart front end, the user will be prompted to select one of the three options
					//If the option is a book or a shoe the user will be asked for product option
					//Also the selection for shoes is pointless due to the prudct txt not having any shoe items and
					//I have not implemented shoes for it

					System.out.print("\nWill you be ordering a Book or a Shoes or other products(Book/Shoes/Others): ");
					if (scanner.hasNextLine()) {
						itemType = scanner.nextLine();
					}

					if (itemType.equals("Book") || itemType.equals("Shoes") || itemType.equals("Others")) {
						System.out.print("Product Id: ");
						// get product id
						if (scanner.hasNextLine()) {
							productId = scanner.nextLine();
						}
						System.out.print("\nCustomer Id: ");
						// get customer id
						if (scanner.hasNextLine()) {
							customerId = scanner.nextLine();
						}

						if (itemType.equals("Book")) {
							System.out.print("\nFormat [Paperback Hardcover EBook]: ");
							// get book format and store in options string
							if (scanner.hasNextLine()) {
								options = scanner.nextLine();
							}
						} else if (itemType.equals("Shoes")) {
							System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
							// get shoe size and store in options
							if (scanner.hasNextLine()) {
								options = scanner.nextLine();
							}

							System.out.print("\nColor: \"Black\" \"Brown\": ");
							// get shoe color and append to options
							if (scanner.hasNextLine()) {
								options = options + " " + scanner.nextLine();
							}
						} else if (itemType.equals("Others")) {
							options = "NormalProduct";
						}

						String cartItem = amazon.addCartItem(customerId, productId, options, itemType);
						if (cartItem == null) {
							System.out.println(amazon.errMsg);
						} else {
							System.out.println(cartItem);
						}
					} else {
						System.out.println("Wrong Input! Please enter Book/Shoes/Others!");
					}


				} else if (action.equalsIgnoreCase("REMCARTITEM"))
				{
					String productId = "";
					String customerId = "";
					String options = "";

					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine()) {
						productId = scanner.nextLine();
					}
					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine()) {
						customerId = scanner.nextLine();
					}

					String cartItem = amazon.remCartItem(customerId, productId);
					System.out.println(cartItem);

				} else if (action.equalsIgnoreCase("PRINTCART"))
				{
					String customerId = "";

					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine()) {
						customerId = scanner.nextLine();
					}

					amazon.printCart(customerId);

				} else if (action.equalsIgnoreCase("ORDERITEMS"))
				{
					String customerId = "";

					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine()) {
						customerId = scanner.nextLine();
					}

					String cartItem = amazon.orderCart(customerId);
					if (cartItem == null) {
						System.out.println(amazon.errMsg);
					} else {
						System.out.println(cartItem);
					}
				}
				else if (action.equalsIgnoreCase("STATS"))
				{
					amazon.stats();
				}

				//This is used to ad rating to the product
				else if (action.equalsIgnoreCase("RATEPROD"))
				{
					String productId = "";
					String customerId = "";
					String rating = "";

					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine()) {
						productId = scanner.nextLine();
					}
					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine()) {
						customerId = scanner.nextLine();
					}

					System.out.print("\nRating(From 1 to 5): ");
					// get customer rating
					if (scanner.hasNextLine()) {
						rating = scanner.nextLine();
					}


					amazon.addRating(productId, customerId, rating);
				}
				//This will print out the rating count for each product
				else if (action.equalsIgnoreCase("PRINTRATING"))
				{
					String productId = "";

					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine()) {
						productId = scanner.nextLine();
					}

					amazon.ratingPrint(productId);
				}

				else if (action.equalsIgnoreCase("HIGHERRATING"))
				{
					String category = "";
					String rating = "";

					System.out.print("Please Select Category (General/ Clothing/ Books/ Furniture/ Computers): ");
					// get category
					if (scanner.hasNextLine()) {
						category = scanner.nextLine();
					}

					System.out.print("\nPlease Enter Rating Threshold(From 1 to 5): ");
					// get customer rating
					if (scanner.hasNextLine()) {
						rating = scanner.nextLine();
					}

					amazon.ratingThreshold(category, rating);

				}

				System.out.print("\n>");
			}

			//This catches all the possible exceptions
			catch (productNotFound | customerNotFound | invalidProductOpt | invalidMenuSelection| prodOutOfStock | invalidCustomerName | invalidCustomerAddress |
			invalidOrderNum | authorNotFound | itemNotInCart | customerAlreadyRated | invalidRating | invalidCategory e)
			{
				System.out.println(e.getMessage());
				System.out.print("\n>");
			}
		}
	}
}
