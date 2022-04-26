import java.util.HashMap;

public class ShoppingCart {
   private HashMap<Product,Integer> listOfProducts;//note: The int represent the amount customer choose to buy for each product.

    public ShoppingCart() {
        this.listOfProducts =new HashMap<>();
    }

    public HashMap<Product,Integer> getListOfProducts() {
        return listOfProducts;
    }

    public void setListOfProducts(HashMap<Product,Integer> listOfProducts) {
        this.listOfProducts = listOfProducts;
    }

}
