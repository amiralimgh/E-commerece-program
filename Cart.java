//Amirali Malekghasemi
//Student #501139438

import java.util.ArrayList;

public class Cart
{

    private String customerID;
    private ArrayList<CartItem> cartItems;

    public Cart(String customerID)
    {
        this.customerID = customerID;
        cartItems = new ArrayList<CartItem>();
    }


    //Adds the product to the CarItem list
    public void addItem(Product product, String productOpt)
    {
        cartItems.add(new CartItem(product, productOpt, customerID));
    }


    //Used to remove the product from cart
    public boolean removeItem(Product product)
    {
        //This iterates through all the CartItems in the Cart and removes the intended one
        for (int i = 0; i < cartItems.size(); i++)
        {
            if (cartItems.get(i).getProduct() == product)
            {
                cartItems.remove(cartItems.get(i));
                return true;
            }
        }
        //If item is not found, we return false
        return false;
    }


    //This is used after all products have been ordered to clear the cart
    public void removeAllItems()
    {
        cartItems.clear();
    }


    //Uses the printProduct method of CartItem
    //Does this for every CartItem in the Cart
    public void printAllItems()
    {
        for (int i = 0; i < cartItems.size(); i++)
        {
            cartItems.get(i).printProduct();
        }
    }

    //This function returns an arraylist of all the Product objects stored in the CartItems of this Cart
    //This is used when the ORDERITEMS is initiated
    public ArrayList<Product> cartProducts()
    {
        ArrayList<Product> productsList = new ArrayList<>();
        for (CartItem c : cartItems)
        {
            productsList.add(c.getProduct());
        }
        return productsList;
    }


    //This function returns an arraylist of all the productOptions stored in the CartItems of this Cart
    //This is used when the ORDERITEMS is initiated
    public ArrayList<String> cartProductsOptions()
    {
        ArrayList<String> productsList = new ArrayList<>();
        for (CartItem c : cartItems)
        {
            productsList.add(c.getProductOpt());
        }
        return productsList;
    }
}
