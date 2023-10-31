import java.util.Set;

class Shop {
private double price;
private Set<String> products;

public Shop(double price, String[] products) {
        this.price = price;
        this.products = Set.of(products);
        }

public double getPrice() {
        return price;
        }

public Set<String> getProducts() {
        return products;
        }
        }