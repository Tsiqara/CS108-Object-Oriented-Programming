package Store;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.HashMap;

public class ShoppingCart {
    public HashMap<Product, Integer> cart;
    public BigDecimal total;

    public ShoppingCart(){
        cart = new HashMap<>();
        total = new BigDecimal(0);
    }

    public void addProduct(Product product, int quantity){
        if(cart.containsKey(product)){
            if(quantity > 0){
                cart.put(product, cart.get(product) + quantity);
                total = total.add(product.getPrice().multiply((new BigDecimal (quantity))));
            }else{
                total = total.subtract(product.getPrice().multiply(new BigDecimal(cart.get(product))));
                cart.remove(product);
            }
        }else{
            if(quantity > 0){
                cart.put(product, quantity);
                total = total.add(product.getPrice().multiply((new BigDecimal (quantity))));
            }
        }
    }
}
