
package salesmanagement.product;
public class Product {
    private String id;
    private String name;
    private String category;
    private double price;
    private int stockQuantity;

    
    public Product() {
    }

   
    public Product(String id, String name, String category, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

  
    public String getId() { return id; }
    public void setId(String id) { this.id = id; } 

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public String toFileString() {
        return id + ";" + name + ";" + category + ";" + String.format(java.util.Locale.US, "%.2f", price) + ";" + stockQuantity;
    }
    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-15s | %,15.0f | %-10d |", 
                id, name, category, price, stockQuantity);
    }
}
