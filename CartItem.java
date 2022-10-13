//Amirali Malekghasemi
//Student #501139438

public class CartItem {

    private Product product;
    private String productOpt;
    private String cartId;

    public CartItem(Product p, String productOpt, String cartId)
    {
        this.product = p;
        this.productOpt = productOpt;
        this.cartId = cartId;
    }

    public Product getProduct()
    {
        return product;
    }

    public String getProductOpt(){return productOpt;}

    //This prints the info of the product that is stored in this CartItem object
    public void printProduct()
    {
        product.print();
    }


}
