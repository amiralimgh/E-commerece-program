//Amirali Malekghasemi
//Student #501139438

/* A book IS A product that has additional information - e.g. title, author

 	 A book also comes in different formats ("Paperback", "Hardcover", "EBook")
 	 
 	 The format is specified as a specific "stock type" in get/set/reduce stockCount methods.

*/
public class Book extends Product implements Comparable<Book>
{
  private String author;
  private String title;
  private int year;

  
  // Stock related information NOTE: inherited stockCount variable is used for EBooks
  int paperbackStock;
  int hardcoverStock;
  
  public Book(String name, String id, double price, int paperbackStock, int hardcoverStock, String title, String author, int year)
  {
  	 // Make use of the constructor in the super class Product. Initialize additional Book instance variables. 
  	 // Set category to BOOKS

      //Initialize the inherited values and then the unique variables
    super(name, id, price, 100000, Category.BOOKS);
    this.paperbackStock = paperbackStock;
    this.hardcoverStock = hardcoverStock;
    this.title = title;
    this.author = author;
    this.year = year;
  }

  //This is for bonus. This gets year.
  public int getYear()
  {
      return year;
  }

  public String getAuthor()
  {
      return author;
  }

  // Check if a valid format  
  public boolean validOptions(String productOptions)
  {
  	// check productOptions for "Paperback" or "Hardcover" or "EBook"
  	// if it is one of these, return true, else return false
      if (productOptions.equals("Paperback") | productOptions.equals("Hardcover")  | productOptions.equals("EBook"))
      {
          return true;
      }
      else
      {
          return false;
      }
  }
  
  // Override getStockCount() in super class.
  public int getStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and return) the number of stock for "Paperback" etc
  	// Use the variables paperbackStock and hardcoverStock at the top. 
  	// For "EBook", use the inherited stockCount variable.

        if (productOptions.equals("Paperback"))
        {
            return paperbackStock;
        }
        else if (productOptions.equals("Hardcover"))
        {
            return hardcoverStock;
        }

        return super.getStockCount(productOptions);

	}
  
  public void setStockCount(int stockCount, String productOptions)
	{
    // Use the productOptions to check for (and set) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
        if (productOptions.equals("Paperback"))
        {
            this.paperbackStock = stockCount;
        }
        else if (productOptions.equals("Hardcover"))
        {
            this.hardcoverStock = stockCount;
        }
        else
        {
            super.setStockCount(stockCount, productOptions);
        }
	}
  
  /*
   * When a book is ordered, reduce the stock count for the specific stock type
   */
  public void reduceStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and reduce) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
        if (productOptions.equals("Paperback"))
        {
            this.paperbackStock--;
        }
        else if (productOptions.equals("Hardcover"))
        {
            this.hardcoverStock--;
        }
        else
        {
            super.setStockCount((super.getStockCount(productOptions) - 1), productOptions);
        }
	}
  /*
   * Print product information in super class and append Book specific information title and author
   */
  public void print()
  {
  	// Replace the line below.
  	// Make use of the super class print() method and append the title and author info. See the video
      super.print();
      System.out.print(" Book Title: " + title + " Author: " + author);
  }

    @Override
    public int compareTo(Book o) {
        if (this.getYear() == o.getYear())
        {
            return 0;
        }
        else if (this.getYear() > o.getYear())
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
}
