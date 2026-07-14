
package salesmanagement.product;

import salesmanagement.product.Product;
import java.io.*;

public class ProductManager {
    private final int MAX = 50;
    private Product productList[] = new Product[MAX];
    private int count = 0;

    
    public boolean isFull() {
        return count == MAX;
    }

    
    public int findProductIndex(String id) {
        for (int i = 0; i < count; i++) {
            if (productList[i].getId().equalsIgnoreCase(id)) {
                return i; 
            }
        }
        return -1; 
    }

    
    public boolean addProduct(Product p) {
        if (isFull()) {
            return false; 
        }
        productList[count] = p; 
        count++; 
        return true;
    }
    
    public boolean updateProduct(int index, Product updatedProduct) {
        if (index >= 0 && index < count) {
            productList[index] = updatedProduct;
            return true;
        }
        return false;
    }

   
    public Product getProduct(int index) {
        if (index >= 0 && index < count) {
            return productList[index]; 
        }
        return null; 
    }
    
    public boolean removeProduct(int index) {
        if (index >= 0 && index < count) {
           
            for (int i = index; i < count - 1; i++) {
                productList[i] = productList[i + 1];
            }
            productList[count - 1] = null; 
            count--; 
            return true;
        }
        return false;
    }
    
    public int getCount() {
        return count;
    }
    //hambosung
    public Product[] getProducts() {
        return this.productList;
    }
   public boolean saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < count; i++) {
                pw.println(productList[i].toFileString());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving product file: " + e.getMessage());
            return false;
        }
    }
 
    public boolean loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return false;
        }
        count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                String id = parts[0];
                String name = parts[1];
                String category = parts[2];
                double price = Double.parseDouble(parts[3]);
                int stockQuantity = Integer.parseInt(parts[4]);
 
                Product p = new Product(id, name, category, price, stockQuantity);
                addProduct(p);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error loading product file: " + e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing product data: " + e.getMessage());
            return false;
        }
    }
}
