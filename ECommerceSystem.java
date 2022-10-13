//Amirali Malekghasemi
//Student #501139438

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{

    private Map<Product, ArrayList<Integer>> productRating = new HashMap<>();
    private Map<Customer, ArrayList<Product>> custAlreadyRated = new HashMap<>();

    //This map holds the products objects
    private Map<String, Product> productMap = new TreeMap<>();
    //This map is for keeping count of the product orders
    private Map<Product, Integer> productOrderCount = new HashMap<>();

    //private ArrayList<Product>  products = new ArrayList<Product>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();	
    
    private ArrayList<ProductOrder> orders   = new ArrayList<ProductOrder>();
    private ArrayList<ProductOrder> shippedOrders   = new ArrayList<ProductOrder>();
    
    // These variables are used to generate order numbers, customer id's, product id's 
    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;
    
    // General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
    String errMsg = null;
    
    // Random number generator
    Random random = new Random();
    
    public ECommerceSystem()
    {
    	// NOTE: do not modify or add to these objects!! - the TAs will use for testing
    	// If you do the class Shoes bonus, you may add shoe products
    	
    	// Create some products. Notice how generateProductId() method is used
        /*
    	products.add(new Product("Acer Laptop", generateProductId(), 989.0, 99, Product.Category.COMPUTERS));
    	products.add(new Product("Apex Desk", generateProductId(), 1378.0, 12, Product.Category.FURNITURE));
    	products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "Ahm Gonna Make You Learn", "T. McInerney", 1930));
    	products.add(new Product("DadBod Jeans", generateProductId(), 24.0, 50, Product.Category.CLOTHING));
    	products.add(new Product("Polo High Socks", generateProductId(), 5.0, 199, Product.Category.CLOTHING));
    	products.add(new Product("Tightie Whities", generateProductId(), 15.0, 99, Product.Category.CLOTHING));
    	products.add(new Book("Book", generateProductId(), 35.0, 4, 2, "How to Fool Your Prof", "D. Umbast", 1960));
    	products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "How to Escape from Prison", "A. Fugitive", 1970));
    	products.add(new Book("Book", generateProductId(), 44.0, 14, 12, "Ahm Gonna Make You Learn More", "T. McInerney", 1940));
    	products.add(new Product("Rock Hammer", generateProductId(), 10.0, 22, Product.Category.GENERAL));
        products.add(new Shoes("Jordan 1's", generateProductId(), 200.0, new int[] {1, 2, 3, 4, 5}, new int[] {1, 2, 3, 4, 5}));
        products.add(new Book("Book", generateProductId(), 44.0, 14, 12, "Ahm Gonna Make You Learn More oldest", "T. McInerney", 1920));
         */

        productMap = productsReaderMap("products.txt");

        //This loop initiates the map that holds the data for the number of times each product has been ordered
        for (Map.Entry<String, Product> prod : productMap.entrySet())
        {
            productOrderCount.put(prod.getValue(), 0);
        }
        for (Map.Entry<String, Product> prod : productMap.entrySet())
        {
            productRating.put(prod.getValue(), new ArrayList<Integer>());
        }

    	// Create some customers. Notice how generateCustomerId() method is used
    	customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine"));
    	customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach"));

        for (Customer c: customers)
        {
            custAlreadyRated.put(c, new ArrayList<Product>());
        }

    }


    private Map<String, Product> productsReaderMap(String filename)
    {
        Map<String, Product> productMapFunc = new TreeMap<>();
        ArrayList<Product> productsList = new ArrayList<>();
        try {
            //Variables to hold the info extracted from the file
            String productName = null;
            Double productPrice = 0.0;
            Integer productStock = 0;
            Integer bookStockPb = 0;
            Integer bookStockHc = 0;
            String bookTitle = null;
            String bookAuthor = null;
            String categoryString = "";
            Integer bookYear = 0;
            Boolean prodIsBook = false;

            File prodFile = new File(filename);
            Scanner eachLine = new Scanner(prodFile);
            int counter = 1;
            while (eachLine.hasNextLine())
            {
                //Every 5 iteration of the while loop, gets the info of 1 product
                //Based on the counter(which is basically the line number counter)
                //the if statement corresponding to it will be activated and data will be stored
                prodIsBook = false;
                String line = eachLine.nextLine();
                //This is for books only, it gets the title, author and year
                if (counter % 5 == 0)
                {
                    if (!line.equals(""))
                    {
                        String[] bookInfo = line.split(":");
                        bookTitle = bookInfo[0];
                        bookAuthor = bookInfo[1];
                        bookYear = Integer.parseInt(bookInfo[2]);
                        prodIsBook = true;
                    }
                }
                //This statement is for getting the stock
                else if (counter % 4 == 0)
                {
                    String[] prodStock = line.split(" ");
                    //If it's a book this if-statement will be activated
                    if (prodStock.length == 2)
                    {
                        bookStockPb = Integer.parseInt(prodStock[0]);
                        bookStockHc = Integer.parseInt(prodStock[1]);
                    }
                    else
                    {
                        productStock = Integer.parseInt(prodStock[0]);
                    }
                }
                //This statement gets the price
                else if (counter % 3 == 0)
                {
                    productPrice = Double.parseDouble(line);
                }
                //This statement gets the name of the product
                else if (counter % 2 == 0)
                {
                    productName = line;
                }
                else
                {
                    categoryString = line;
                }

                counter += 1;
                //This statement activated when we have read 5 lines
                if (counter > 5)
                {
                    //Reset counter for the new product
                    counter = 1;
                    //If product is a book this gets activated and adds a book to the product list
                    if (prodIsBook)
                    {
                        productsList.add(new Book(productName, generateProductId(), productPrice, bookStockPb, bookStockHc, bookTitle, bookAuthor, bookYear));
                    }
                    else
                    {
                        productsList.add(new Product(productName, generateProductId(), productPrice, productStock, Product.Category.valueOf(categoryString)));
                    }
                }
            }

        }
        catch (IOException e)
        {
            System.out.println("File Corrupted");
            System.exit(1);
        }

        //This loop basically converts the data in the arraylist obtained from the txt file to a map
        for (Product p: productsList)
        {
            productMapFunc.put(p.getId(), p);
        }

        return productMapFunc;
    }
    
    private String generateOrderNumber()
    {
    	return "" + orderNumber++;
    }

    private String generateCustomerId()
    {
    	return "" + customerId++;
    }
    
    private String generateProductId()
    {
    	return "" + productId++;
    }
    
    public String getErrorMessage()
    {
    	return errMsg;
    }
    
    public void printAllProducts()
    {
    	for (Map.Entry<String, Product> e : productMap.entrySet())
            e.getValue().print();
    }
    
    // Print all products that are books. See getCategory() method in class Product
    public void printAllBooks()
    {
    	for (Map.Entry<String, Product> prod : productMap.entrySet())
        {
            if (prod.getValue().getCategory() == Product.Category.BOOKS)
            {
                prod.getValue().print();
            }
        }
    }
    
    // Print all current orders
    public void printAllOrders()
    {
    	for (int i = 0; i < orders.size(); i++)
        {
            orders.get(i).print();
        }
    }
    // Print all shipped orders
    public void printAllShippedOrders()
    {
        for (int i = 0; i < shippedOrders.size(); i++)
        {
            shippedOrders.get(i).print();
        }
    }
    
    // Print all customers
    public void printCustomers()
    {
        for (int i = 0; i < customers.size(); i++)
        {
            customers.get(i).print();
        }
    }
    /*
     * Given a customer id, print all the current orders and shipped orders for them (if any)
     */
    public void printOrderHistory(String customerId) throws customerNotFound
    {
      // Make sure customer exists - check using customerId
    	// If customer does not exist, set errMsg String and return false
    	// see video for an appropriate error message string
    	// ... code here

        //This loop will make sure the customer exists
    	for (int i = 0; i < customers.size(); i++) {
            //Uses the overwritten equal in class customer
            if (customers.get(i).getId().equals(customerId))
            {
                // Print current orders of this customer
                System.out.println("Current Orders of Customer " + customerId);
                // enter code here

                //This loop go through every order and if the order is for the
                //Customer with the desired customer number it will print it
                for (int y = 0; y < orders.size(); y++)
                {
                    if (orders.get(y).getCustomer() == customers.get(i))
                    {
                        orders.get(y).print();
                    }
                }

                // Print shipped orders of this customer
                System.out.println("\nShipped Orders of Customer " + customerId);
                //enter code here
                //This loop go through every shipped order and if the order is for the
                //Customer with the desired customer number it will print it
                for (int z = 0; z < shippedOrders.size(); z++)
                {
                    if (shippedOrders.get(z).getCustomer() == customers.get(i))
                    {
                        shippedOrders.get(z).print();
                    }
                }
                return;
            }
        }

        //If customer doesn't exist, errMsg will be set and we will return false
        throw new customerNotFound("Customer " + customerId + " Not Found");
    }
    
    public String orderProduct(String productId, String customerId, String productOptions) throws customerNotFound, productNotFound, invalidProductOpt, prodOutOfStock, invalidMenuSelection
    {
    	// First check to see if customer object with customerId exists in array list customers
    	// if it does not, set errMsg and return null (see video for appropriate error message string)
    	// else get the Customer object
        boolean custFound = false;
        int custIndex = 0;

        //Check to see if the customer exists
    	for (int i = 0; i < customers.size(); i++)
        {
            if (customers.get(i).getId().equals(customerId))
            {
                custFound = true;
                custIndex = i;
                break;
            }
        }

        //If customer doesn't exist, errMsg will be printed
        //and we return null
        if (!custFound)
        {
            throw new customerNotFound("Customer " + customerId + " Not Found");
        }
    	// Check to see if product object with productId exists in array list of products
    	// if it does not, set errMsg and return null (see video for appropriate error message string)
    	// else get the Product object

        //int prodIndex = 0;

        //Check to see if the product exists
        /*

        for (int i = 0; i < products.size(); i++)
        {
            if (products.get(i).getId().equals(productId))
            {
                prodFound = true;
                prodIndex = i;
                break;
            }
        }

         */
        boolean prodFound = false;
        Product prodObj = null;
        for (Map.Entry<String, Product> prod : productMap.entrySet())
        {
            if (prod.getKey().equals(productId))
            {
                prodFound = true;
                prodObj = prod.getValue();
                break;
            }
        }
        //If product doesn't exist, errMsg will be printed
        //and we return null
        if (!prodFound)
        {
            throw new productNotFound("product " + productId + " Not Found");
        }



        //This will check to see if the user has used the "order" option
        //to order a book or a shoe. If so, the errMsg will be set, and we return null
        if (!prodObj.validOptions(productOptions) & productOptions.equals("NormalProduct"))
        {
            throw new invalidMenuSelection("This product is a book or a shoe and should be ordered from orderbook or ordershoes");
        }

    	
    	// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
    	// See class Product and class Book for the method validOptions()
    	// If options are not valid, set errMsg string and return null;
        if (prodObj.getCategory() == Product.Category.BOOKS)
        {
            if (!(prodObj.validOptions(productOptions)))
            {
                throw new invalidProductOpt("Product Book ProductId " + productId + " Invalid Options: " + productOptions);
            }
        }


        //I added this

        //Checks to see if the option entered for shoes is correct or not. If the option is
        //Invalid, there will be an error message displayed.
        if (prodObj.getCategory() == Product.Category.CLOTHING & !productOptions.equals("NormalProduct"))
        {
            if (!prodObj.validOptions(productOptions))
            {
                throw new invalidProductOpt("Product Shoe ProductId " + productId + " Invalid Options: " + productOptions);
            }
        }

        //This will check to see if the user is trying to order a normal product in
        //ordershoes or orderbook
        if (!prodObj.validOptions(productOptions))
        {
            errMsg = "You can not order this item in this section. Please use \"order\" for non shoe and book items";
            errMsg += "\nUse \"ordershoes\" to order shoes";
            errMsg += "\nUse \"orderbook\" to order a book";
            throw new invalidMenuSelection(errMsg);
        }


    	// Check if the product has stock available (i.e. not 0)
    	// See class Product and class Book for the method getStockCount()
    	// If no stock available, set errMsg string and return null
        if (prodObj.getStockCount(productOptions) == 0)
        {
            throw new prodOutOfStock("Product " + productId + " Out of Stock");
        }


    	
        // Create a ProductOrder, (make use of generateOrderNumber() method above)
    	// reduce stock count of product by 1 (see class Product and class Book)
    	// Add to orders list and return order number string
        orders.add(new ProductOrder(generateOrderNumber(), prodObj, customers.get(custIndex), productOptions));
        productOrderCount.put(prodObj, productOrderCount.get(prodObj) + 1);
        prodObj.reduceStockCount(productOptions);
        return "Order # " + orders.get(orders.size() - 1).getOrderNumber();
    }
    
    /*
     * Create a new Customer object and add it to the list of customers
     */
    
    public void createCustomer(String name, String address) throws invalidCustomerName, invalidCustomerAddress
    {
    	// Check name parameter to make sure it is not null or ""
    	// If it is not a valid name, set errMsg (see video) and return false
    	// Repeat this check for address parameter
        if (name == null | name.equals(""))
        {
            throw new invalidCustomerName("Invalid Customer Name");
        }
        if (address == null | address.equals(""))
        {
            throw new invalidCustomerAddress("Invalid Customer Address");
        }
    	
    	// Create a Customer object and add to array list
        customers.add(new Customer(generateCustomerId(), name, address));
    }
    
    public void shipOrder(String orderNumber) throws invalidOrderNum
    {
        // Check if order number exists first. If it doesn't, set errMsg to a message (see video)
    	// and return false
        boolean orderFound = false;
        int orderIndex = 0;
        for (int i = 0; i < orders.size(); i++)
        {
            if (orders.get(i).getOrderNumber().equals(orderNumber))
            {
                orderFound = true;
                orderIndex = i;
                break;
            }
        }
        if (!orderFound)
        {
            throw new invalidOrderNum("Order " + orderNumber + " Not Found");
        }

    	// Retrieve the order from the orders array list, remove it, then add it to the shippedOrders array list
        orders.get(orderIndex).print();
        shippedOrders.add(orders.get(orderIndex));
        orders.remove(orderIndex);
    	// return a reference to the order
    }
    
    /*
     * Cancel a specific order based on order number
     */
    public boolean cancelOrder(String orderNumber) throws invalidOrderNum
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
    	// and return false

        boolean orderFound = false;
        int orderIndex = 0;
        for (int i = 0; i < orders.size(); i++)
        {
            if (orders.get(i).getOrderNumber().equals(orderNumber))
            {
                orderFound = true;
                orderIndex = i;
                break;
            }
        }
        if (!orderFound)
        {
            throw new invalidOrderNum("Order " + orderNumber + " Not Found");
        }

        orders.remove(orderIndex);
    	return true;
    }

    //The comparator helps to sort the arraylist of objects by comparing each
    //object by another until the arraylist is sorted by name/price in this case
    //I originally coded the comparator in the product class itself
    //But had to move it here because importing is not allowed in the assignment


    // Sort products by increasing price
    public ArrayList<Product> sortByPrice()
    {
        class productPriceComparator implements Comparator<Product>
        {
            @Override
            public int compare(Product p1, Product p2) {
                return (int) (p1.getPrice() - p2.getPrice());
            }
        }

        //This turns a map into an arraylist, sorts the arraylist and prints that since maps can not be sorted easily
        ArrayList<Product> products = new ArrayList<>();
        for (Map.Entry<String, Product> prod : productMap.entrySet())
        {
            products.add(prod.getValue());
        }
        products.sort(new productPriceComparator());
        return products;
    }
    
    
    // Sort products alphabetically by product name
    public ArrayList<Product> sortByName()
    {
        class productNameComparator implements Comparator<Product>
        {
            @Override
            public int compare(Product n1, Product n2) {
                return n1.getName().toLowerCase().compareTo(n2.getName().toLowerCase());
            }
        }

        //This turns a map into an arraylist, sorts the arraylist and prints that since maps can not be sorted easily
        ArrayList<Product> products = new ArrayList<>();
        for (Map.Entry<String, Product> prod : productMap.entrySet())
        {
            products.add(prod.getValue());
        }
        products.sort(new productNameComparator());
        return products;
    }
    
        
    // Sort products alphabetically by product name
    public void sortCustomersByName()
    {
        Collections.sort(customers);
    }


    //This is the bonus class
    public void booksOfAuthorYear(String author) throws authorNotFound
    {
        boolean authorFound = false;
        //This will save the books found written by the author
        ArrayList<Book> bookByAuthor = new ArrayList<>();
        //This goes through all the products finding the books written by
        //the author
        for (Map.Entry<String, Product> prod : productMap.entrySet())
        {
            if (prod.getValue().getCategory() == Product.Category.BOOKS)
            {
                if (prod.getValue().getAuthor().equals(author))
                {
                    bookByAuthor.add((Book) prod.getValue());
                    authorFound = true;
                }
            }
        }
        //If no books by this author was found, we set and error message
        if (!authorFound)
        {
            throw new authorNotFound("This Author Does Not Exist");
        }
        //This will sort the books by year
        Collections.sort(bookByAuthor);
        //This will print each book
        for (int j = 0; j < bookByAuthor.size(); j++)
        {
            bookByAuthor.get(j).print();
        }
    }

    public String addCartItem(String customerId, String productId, String productOpt, String prodType) throws customerNotFound, productNotFound, invalidProductOpt, prodOutOfStock, invalidMenuSelection
    {
        // Get customer
        int index = customers.indexOf(new Customer(customerId));
        if (index == -1)
        {
            throw new customerNotFound("Customer " + customerId + " Not Found");
        }
        Customer customer = customers.get(index);

        // Get product
        boolean prodFound = false;
        Product product = null;
        for (Map.Entry<String, Product> prod : productMap.entrySet())
        {
            if (prod.getKey().equals(productId))
            {
                prodFound = true;
                product = prod.getValue();
                break;
            }
        }
        //If product doesn't exist, errMsg will be printed
        //and we return null
        if (!prodFound)
        {
            throw new productNotFound("Product " + productId + " Not Found");
        }

        //Validating Entry
        if (prodType.equals("Book"))
        {
            //If user has entered "Book" but selects a non-book item this exception will be thrown
            if (product.getCategory() != Product.Category.BOOKS)
            {
                throw new invalidMenuSelection("This product is not a book and should be ordered from another selection");
            }
            if (!product.validOptions(productOpt))
            {
                throw new invalidProductOpt("Product Book ProductId " + productId + " Invalid Options: " + productOpt);
            }
        }

        if (prodType.equals("Shoes"))
        {
            //If user has entered "Shoe" but selects a non-shoe item this exception will be thrown
            if (product.getCategory() != Product.Category.CLOTHING)
            {
                throw new invalidMenuSelection("This product is not shoes and should be ordered from another selection");
            }
            if (!product.validOptions(productOpt))
            {
                throw new invalidProductOpt("Product Shoes ProductId " + productId + " Invalid Options: " + productOpt);
            }
        }

        if (prodType.equals("Others"))
        {
            //If user has entered "Others" but selects a book or shoes this exception will be thrown
            if (!product.validOptions(productOpt))
            {
                throw new invalidMenuSelection("This product should be ordered from another selection");
            }
        }

        //This exception will be thrown if the product is out of stock and, it prevents the user from adding it to the cart
        if (product.getStockCount(productOpt) == 0)
        {
            throw new prodOutOfStock("Product " + productId + " Out of Stock");
        }

        Cart customerCart = customer.getCustomerCart();

        customerCart.addItem(product, productOpt);

        return "Product with ID #" + product.getId() + " has been added to your cart";
    }

    public String remCartItem(String customerId, String productId) throws customerNotFound, productNotFound, itemNotInCart
    {
        // Get customer
        int index = customers.indexOf(new Customer(customerId));
        if (index == -1)
        {
            throw new customerNotFound("Customer " + customerId + " Not Found");
        }
        Customer customer = customers.get(index);

        // Get Product
        boolean prodFound = false;
        Product product = null;
        for (Map.Entry<String, Product> prod : productMap.entrySet())
        {
            if (prod.getKey().equals(productId))
            {
                prodFound = true;
                product = prod.getValue();
                break;
            }
        }
        //If product doesn't exist, throw an exception
        if (!prodFound)
        {
            throw new productNotFound("product " + productId + " Not Found");
        }

        //This is customer cart object
        Cart customerCart = customer.getCustomerCart();

        //The item might exist but not be in the cart, so this exception will be thrown
        if (!customerCart.removeItem(product))
        {
            throw new itemNotInCart("This item is not in the cart!");
        }

        //Message indicating the removal was successful
        return "Product with ID #" + product.getId() + " has been removed from your cart";
    }
    public void printCart(String customerId) throws customerNotFound
    {
        // Get customer
        int index = customers.indexOf(new Customer(customerId));
        if (index == -1)
        {
            throw new customerNotFound("Customer " + customerId + " Not Found");
        }
        Customer customer = customers.get(index);
        //This is customer cart object
        Cart customerCart = customer.getCustomerCart();


        //Prints the products in the cart
        customerCart.printAllItems();

    }

    public String orderCart(String customerId) throws customerNotFound
    {
        boolean outOfStock = false;
        // Get customer
        int index = customers.indexOf(new Customer(customerId));
        if (index == -1)
        {
            throw new customerNotFound("Customer " + customerId + " Not Found");
        }
        Customer customer = customers.get(index);


        Cart customerCart = customer.getCustomerCart();

        //This loop will iterate through each cartitem(each cartitem has a product info in it)
        for (int i = 0; i < customerCart.cartProducts().size(); i++)
        {
            try {
                //I use the already existing orderPorduct function that was created for a1
                orderProduct(customerCart.cartProducts().get(i).getId(), customerId, customerCart.cartProductsOptions().get(i));
            }
            catch (Exception e){
                //The product might run out of stock while being ordered since it was placed in the cart and not actually ordered
                //This will stop that from happening and print the right message for it
                System.out.println(e.getMessage());
                outOfStock = true;
            }
        }
        customerCart.removeAllItems();

        if (outOfStock)
        {
            return "All the products except for the one's mentioned above have been ordered.";
        }

        return "All products in the cart have been ordered";
    }

    public void stats()
    {
        //The line below converts the already existing HashMap to a stream to sort it using the values(we do this because Maps don't have sorting trait)
        // and then converts the stream back to a LinkedHashMap(Linked because normal HashMap can ignore the iteration order)
        Map<Product, Integer> result = productOrderCount.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey,
                Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        //Printing the most ordered to the least ordered products
        for (Map.Entry<Product, Integer> prod : result.entrySet())
        {
            System.out.println("Product " + prod.getKey().getName()+ " with ID #"+ prod.getKey().getId()+ " has been ordered " + prod.getValue());
        }
    }

    public void addRating(String productId, String customerId, String rating) throws customerNotFound, productNotFound, customerAlreadyRated, invalidRating
    {
        // Get customer
        int index = customers.indexOf(new Customer(customerId));
        if (index == -1)
        {
            throw new customerNotFound("Customer " + customerId + " Not Found");
        }
        Customer customer = customers.get(index);
        // Get Product
        boolean prodFound = false;
        Product product = null;
        for (Map.Entry<String, Product> prod : productMap.entrySet())
        {
            if (prod.getKey().equals(productId))
            {
                prodFound = true;
                product = prod.getValue();
                break;
            }
        }
        //If product doesn't exist, throw an exception
        if (!prodFound)
        {
            throw new productNotFound("product " + productId + " Not Found");
        }


        //Validates rating input
        Boolean correctRating = false;
        if (rating.equals("5") | rating.equals("4") | rating.equals("3") | rating.equals("2") | rating.equals("1"))
        {
            correctRating = true;
        }

        if (!correctRating)
        {
            throw new invalidRating("The inputted rating is not valid! Please enter a number between 1 to 5");
        }


        //Checks to see if the customer has already rated this product or not
        ArrayList<Product> custProds = custAlreadyRated.get(customer);
        for (Product p: custProds)
        {
            if (p.equals(product))
            {
                throw new customerAlreadyRated("The customer with the ID#" + customerId + " has already rated this product");
            }
        }

        //Adding the rating to the products map for ratings
        ArrayList<Integer> prodRating = productRating.get(product);
        prodRating.add(Integer.valueOf(rating));
        productRating.put(product, prodRating);

        //Adding the info so that customer can not rate this product again
        custProds.add(product);
        custAlreadyRated.put(customer, custProds);

        System.out.println("Rating has been added successfully");
    }

    public void ratingPrint(String productId) throws productNotFound
    {
        // Get Product
        boolean prodFound = false;
        Product product = null;
        for (Map.Entry<String, Product> prod : productMap.entrySet())
        {
            if (prod.getKey().equals(productId))
            {
                prodFound = true;
                product = prod.getValue();
                break;
            }
        }
        //If product doesn't exist, throw an exception
        if (!prodFound)
        {
            throw new productNotFound("product " + productId + " Not Found");
        }

        ArrayList<Integer> prodRating = productRating.get(product);
        int[] ratingHolder = {0, 0, 0, 0, 0};

        //This will add the count of each rating to an array that will be used to output the result
        for (Integer r: prodRating)
        {
            if (r == 1)
            {
                ratingHolder[0] += 1;
            }
            if (r == 2)
            {
                ratingHolder[1] += 1;
            }
            if (r == 3)
            {
                ratingHolder[2] += 1;
            }
            if (r == 4)
            {
                ratingHolder[3] += 1;
            }
            if (r == 5)
            {
                ratingHolder[4] += 1;
            }
        }

        for (int i = 0; i < 5; i++)
        {
            System.out.println("The number of " + (i + 1) + " star ratings for this product is: " + ratingHolder[i]);
        }
    }

    public void ratingThreshold(String category, String rating) throws invalidCategory, invalidRating
    {
        Boolean correctRating = false;
        if (rating.equals("5") | rating.equals("4") | rating.equals("3") | rating.equals("2") | rating.equals("1"))
        {
            correctRating = true;
        }

        if (!correctRating)
        {
            throw new invalidRating("The inputted rating is not valid! Please enter a number between 1 to 5");
        }

        int ratingInt = Integer.valueOf(rating);

        //GENERAL, CLOTHING, BOOKS, FURNITURE, COMPUTERS
        Product.Category categoryObj = null;

        //Validating entered category
        if (category.equals("General"))
        {
            categoryObj = Product.Category.GENERAL;
        }
        else if (category.equals("Clothing"))
        {
            categoryObj = Product.Category.CLOTHING;
        }
        else if (category.equals("Books"))
        {
            categoryObj = Product.Category.BOOKS;
        }
        else if (category.equals("Furniture"))
        {
            categoryObj = Product.Category.FURNITURE;
        }
        else if (category.equals("Computers"))
        {
            categoryObj = Product.Category.COMPUTERS;
        }
        else
        {
            throw new invalidCategory("Invalid Category! Please try again!");
        }

        for (Map.Entry<Product, ArrayList<Integer>> prod : productRating.entrySet())
        {
            //If the product is in the same category
            if (prod.getKey().getCategory() == categoryObj)
            {
                int sum = 0;
                double average = 0;
                //Sums up all the recorded ratings
                for (Integer r: prod.getValue())
                {
                    sum += r;
                }
                //We don't want to divide by zero
                if (prod.getValue().size() != 0)
                {
                    average = (double) sum / prod.getValue().size();
                }
                //Check to see if the rating is higher
                if (average >= ratingInt)
                {
                    System.out.println("Product " + prod.getKey().getName() + " with ID#" + prod.getKey().getId() + " has a rating of " + average);
                }
            }
        }
    }


}

class customerNotFound extends Exception
{

    public customerNotFound(String message)
    {
        super(message);
    }
}

class productNotFound extends Exception
{

    public productNotFound(String message)
    {
        super(message);
    }
}

class invalidProductOpt extends Exception
{

    public invalidProductOpt(String message)
    {
        super(message);
    }
}

class invalidMenuSelection extends Exception
{

    public invalidMenuSelection(String message)
    {
        super(message);
    }
}

class prodOutOfStock extends Exception
{

    public prodOutOfStock(String message)
    {
        super(message);
    }
}

class invalidCustomerAddress extends Exception
{

    public invalidCustomerAddress(String message)
    {
        super(message);
    }
}

class invalidCustomerName extends Exception
{

    public invalidCustomerName(String message)
    {
        super(message);
    }
}

class invalidOrderNum extends Exception
{

    public invalidOrderNum(String message)
    {
        super(message);
    }
}

class authorNotFound extends Exception
{
    public authorNotFound(String message)
    {
        super(message);
    }
}

class itemNotInCart extends Exception
{
    public itemNotInCart(String message)
    {
        super(message);
    }
}

class customerAlreadyRated extends Exception
{
    public customerAlreadyRated(String message)
    {
        super(message);
    }
}

class invalidRating extends Exception
{
    public invalidRating(String message)
    {
        super(message);
    }
}

class invalidCategory extends Exception
{
    public invalidCategory(String message)
    {
        super(message);
    }
}