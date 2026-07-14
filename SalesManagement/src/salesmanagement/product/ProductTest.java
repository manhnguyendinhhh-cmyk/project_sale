
package salesmanagement.product;

import salesmanagement.product.ProductManager;
import salesmanagement.product.Product;
import salesmanagement.util.DataInput;
public class ProductTest {
   
    public void processProductMenu(ProductManager productManager) {
        int subChoice;
        do {
            System.out.println("\n--- PRODUCT MANAGEMENT ---");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Remove Product");
            System.out.println("4. View All Products");
            System.out.println("5. Search Product By ID");
            System.out.println("6. Back to Main Menu");
            System.out.println("--------------------------");
            
            subChoice = DataInput.getIntegerNumber("Choose a sub-option: ");
            
            switch (subChoice) {
                case 1:
                    processCase01(productManager); 
                    break;
                case 2:
                   processCase02(productManager); 
                    break;
                 case 3:
                    processCase03(productManager); 
                    break;
                    case 4:
                    processCase04(productManager); 
                    break;
                    case 5:
                    processCase05(productManager); 
                    break;
                    case 6:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option! Please choose from 1 to 6.");
            }
        } while (subChoice != 6);
    }

       //task1
    public void processCase01(ProductManager productManager) {
        System.out.println("\n>> Task S1 - Add New Product");
        
        //auto ID
        if (productManager.isFull()) {
            System.out.println("Cannot add. Product database is full!");
            return;
        }

     
        int nextIdNumber = productManager.getCount() + 1; 
       
        String id = "P" + String.format("%02d", nextIdNumber); 
        
        System.out.println("Generated Product ID: " + id); 

        //input name
        String name;
        while (true) {
            name = DataInput.getString("Enter Product Name: ");
            if (name.trim().isEmpty()) {
                System.out.println("Error: Product Name cannot be empty! Please enter again.");
                continue;
            }
            if (!name.matches("^[a-zA-Z0-9\\s]+$")) {
                System.out.println("Error: Product Name must contain alphabetic characters only! Please enter again.");
                continue;
            }
            break;
        }

   //input category
        String category;
        while (true) {
            category = DataInput.getString("Enter Category: ");
            if (category.trim().isEmpty()) {
                System.out.println("Error: Cate"
                        + "gory cannot be empty! Please enter again.");
                continue;
            }
            if (!category.matches("^[a-zA-Z\\s]+$")) {
                System.out.println("Error: Category must contain alphabetic characters only! Please enter again.");
                continue;
            }
            break;
        }

   //input price
        double price;
        while (true) {
            price = DataInput.getDoubleNumber("Enter Price: ");
            if (price <= 0) {
                System.out.println("Error: Price must be a positive number (> 0) and in valid format! Please enter again.");
                continue;
            }
            break;
        }

  // input stock
        int stockQuantity;
        while (true) {
            stockQuantity = DataInput.getIntegerNumber("Enter Stock Quantity: ");
            if (stockQuantity <= 0) {
                System.out.println("Error: Stock Quantity must be >= 0! Please enter again.");
                continue;
            }
            break;
        }

        
        Product newProduct = new Product(id, name, category, price, stockQuantity);
        if (productManager.addProduct(newProduct)) {
            System.out.println("Product " + id + " added successfully!");
        } else {
            System.out.println("Product added failed!");
        }
    }
    
    //update product
    public void processCase02(ProductManager productManager) {
        System.out.println("\n>> Task S2 - Update Product");
        
        int foundIndex = -1;
        String id = "";
        
        
        while (true) {
            id = DataInput.getString("Enter Product ID to update: ");
            
          
            if (id.trim().isEmpty()) {
                System.out.println("Error: Product ID cannot be empty! Please enter again.");
                continue;
            }
            
          
            foundIndex = productManager.findProductIndex(id);
            
            
            if (foundIndex == -1) {
                System.out.println("Error: Product with ID '" + id + "' does not exist! Please try another ID.");
                continue; 
            }
            
    
            break; 
        }
        
        
        Product oldProduct = productManager.getProduct(foundIndex);
        System.out.println("Product found! Enter new details (All fields are required, cannot be empty):");

       
        String name;
        while (true) {
            name = DataInput.getString("Enter New Product Name: ");
            if (name.trim().isEmpty()) {
                System.out.println("Error: Product Name cannot be empty! Please enter again.");
                continue;
            }
            if (!name.matches("^[a-zA-Z0-9\\s]+$")) {
                System.out.println("Error: Product Name must contain alphabetic characters only! Please enter again.");
                continue;
            }
            break;
        }

       
        String category;
        while (true) {
            category = DataInput.getString("Enter New Category: ");
            if (category.trim().isEmpty()) {
                System.out.println("Error: Category cannot be empty! Please enter again.");
                continue;
            }
            if (!category.matches("^[a-zA-Z\\s]+$")) {
                System.out.println("Error: Category must contain alphabetic characters only! Please enter again.");
                continue;
            }
            break;
        }

       
        double price;
        while (true) {
            price = DataInput.getDoubleNumber("Enter New Price: ");
            if (price <= 0) {
                System.out.println("Error: Price must be a positive number (> 0) and in valid format! Please enter again.");
                continue;
            }
            break;
        }

       
        int stockQuantity;
        while (true) {
            stockQuantity = DataInput.getIntegerNumber("Enter New Stock Quantity: ");
            if (stockQuantity < 0) {
                System.out.println("Error: Stock Quantity must be >= 0! Please enter again.");
                continue;
            }
            break;
        }

        
        Product updatedProduct = new Product(oldProduct.getId(), name, category, price, stockQuantity);
        
        if (productManager.updateProduct(foundIndex, updatedProduct)) {
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("Product updated failed!");
        }
    }
    //remove product
  public void processCase03(ProductManager productManager) {
        System.out.println("\n>> Task S3 - Remove Product");

        if (productManager.getCount() == 0) {
            System.out.println("No products available to remove!");
            return;
        }

        int foundIndex = -1;
        String id = "";

        while (true) {
            id = DataInput.getString("Enter Product ID to remove: ");
            if (id.trim().isEmpty()) {
                System.out.println("Error: Product ID cannot be empty! Please enter again.");
                continue;
            }

            foundIndex = productManager.findProductIndex(id);
            if (foundIndex == -1) {
                System.out.println("Error: Product with ID '" + id + "' does not exist! Please try another ID.");
                continue; 
            }
            break; 
        }
        
        Product p = productManager.getProduct(foundIndex);
        System.out.println("Product found: " + p.getName() + " (Category: " + p.getCategory() + ")");
        
        String confirm = DataInput.getString("Are you sure you want to remove this product? (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            if (productManager.removeProduct(foundIndex)) {
                System.out.println("Product removed successfully!");
            } else {
                System.out.println("Product removed failed!");
            }
        } else {
            System.out.println("Removal canceled. Product is safe!");
        }
    }
   //show all product
    public void processCase04(ProductManager productManager) {
        System.out.println("\n>> Task S4 - View All Products");
        
       
        int total = productManager.getCount();
        
       
        if (total == 0) {
            System.out.println("No products available in the store!");
            return;
        }
        
        
        System.out.println("+------------+----------------------+-----------------+-----------------+------------+");
        System.out.println("| ID         | Name                 | Category        | Price           | Stock      |");
        System.out.println("+------------+----------------------+-----------------+-----------------+------------+");
         
        
        for (int i = 0; i < total; i++) {
            Product p = productManager.getProduct(i);
            System.out.println(p.toString());
        }
        
       
        System.out.println("+------------+----------------------+-----------------+-----------------+------------+");
    }
    //search product
    public void processCase05(ProductManager productManager) {
        System.out.println("\n>> Task S5 - Search Product By ID");
        
      
        if (productManager.getCount() == 0) {
            System.out.println("No products available in the store to search!");
            return;
        }

        int foundIndex = -1;
        String id = "";

        
        while (true) {
            id = DataInput.getString("Enter Product ID to search: ");
            
            if (id.trim().isEmpty()) {
                System.out.println("Error: Product ID cannot be empty! Please enter again.");
                continue;
            }
            
            foundIndex = productManager.findProductIndex(id);
            
            if (foundIndex == -1) {
                System.out.println("Error: Product with ID '" + id + "' does not exist! Please try another ID.");
                continue; 
            }
            
            break; 
        }
        
        
        System.out.println("Product found:");
        System.out.println("+------------+----------------------+-----------------+-----------------+------------+");
        System.out.println("| ID         | Name                 | Category        | Price           | Stock      |");
        System.out.println("+------------+----------------------+-----------------+-----------------+------------+");
        System.out.println(productManager.getProduct(foundIndex).toString());
        System.out.println("+------------+----------------------+-----------------+-----------------+------------+");
    }
}
