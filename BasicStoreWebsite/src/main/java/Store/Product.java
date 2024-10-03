package Store;

import java.math.BigDecimal;

public class Product {
    String id;
    String name;
    String image;
    BigDecimal price;

    public Product(String id, String name, String image, BigDecimal price){
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(obj.getClass() != this.getClass()) return false;
        if(id.equals(((Product) obj).getId())) return true;
        return false;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

