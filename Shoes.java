//Amirali Malekghasemi
//Student #501139438

import java.util.Scanner;

//Shoe products are part of clothing category and has additional
//info like sizes and books

public class Shoes extends Product
{

    //One array holds the stock for brown shoe sizes
    //And the other one for black shoe sizes
    private int[] blackSizes = new int[5];
    private int[] brownSizes = new int[5];



    //The constructor is similar for to Book and uses most of the
    //parent class "product" options and has the two unique arrays
    public Shoes(String name, String id, double price, int[] blackSizes, int[] brownSizes)
    {
        super(name, id, price, 100000, Category.CLOTHING);
        this.blackSizes = blackSizes;
        this.brownSizes = brownSizes;
    }


    // Check if a valid format
    public boolean validOptions(String productOptions)
    {
        String color = "";
        String size = "";
        Scanner options = new Scanner(productOptions);
        //The two if statements below here make sure that the user
        //Has inputted both the color and size
        //If the inputs format is right, then we will
        //Check to see if the entered options are valid.
        if (options.hasNext())
        {
            size = options.next();
        }
        else
        {
            return false;
        }
        if (options.hasNext())
        {
            color = options.next();
        }
        else
        {
            return false;
        }
        //This is if the input is more than 3 words
        if (options.hasNext())
        {
            return false;
        }

        //Checks to see if the color input is correct
        if (color.equals("Black") | color.equals("Brown"))
        {
            //Checks to see if the size input is correct
            for (int i = 6; i < 11; i++)
            {
                if (size.equals(Integer.toString(i)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    // Override getStockCount() in super class.
    public int getStockCount(String productOptions)
    {
        //Similar to validOptions, each part of the input will be assigned to
        //variable size and color
        Scanner options = new Scanner(productOptions);
        String size = options.next();
        String color = options.next();

        //The loops are used to find the stock between
        //the two arrays of stock
        if (color.equals("Black"))
        {
            for (int i = 6; i < 11; i++)
            {
                if (size.equals(Integer.toString(i)))
                {
                    return blackSizes[i - 6];
                }
            }
        }
        if (color.equals("Brown"))
        {
            for (int i = 6; i < 11; i++)
            {
                if (size.equals(Integer.toString(i)))
                {
                    return brownSizes[i - 6];
                }
            }
        }
        return super.getStockCount(productOptions);
    }

    public void setStockCount(int stockCount, String productOptions)
    {
        //Similar to validOptions, each part of the input will be assigned to
        //variable size and color
        Scanner options = new Scanner(productOptions);
        String size = options.next();
        String color = options.next();
        //The loops are used to find the stock between
        //the two arrays of stock and set the stock
        if (color.equals("Black"))
        {
            for (int i = 6; i < 11; i++)
            {
                if (size.equals(Integer.toString(i)))
                {
                    blackSizes[i - 6] = stockCount;
                }
            }
        }
        if (color.equals("Brown"))
        {
            for (int i = 6; i < 11; i++)
            {
                if (size.equals(Integer.toString(i)))
                {
                    brownSizes[i - 6] = stockCount;
                }
            }
        }
    }

    /*
     * When a book is ordered, reduce the stock count for the specific stock type
     */
    public void reduceStockCount(String productOptions)
    {
        //Similar to validOptions, each part of the input will be assigned to
        //variable size and color
        Scanner options = new Scanner(productOptions);
        String size = options.next();
        String color = options.next();
        //The loops are used to find the stock between
        //the two arrays of stock and reduces the stock
        if (color.equals("Black"))
        {
            for (int i = 6; i < 11; i++)
            {
                if (size.equals(Integer.toString(i)))
                {
                    blackSizes[i - 6] -= 1;
                }
            }
        }
        if (color.equals("Brown"))
        {
            for (int i = 6; i < 11; i++)
            {
                if (size.equals(Integer.toString(i)))
                {
                    brownSizes[i - 6] -= 1;
                }
            }
        }
    }

}
