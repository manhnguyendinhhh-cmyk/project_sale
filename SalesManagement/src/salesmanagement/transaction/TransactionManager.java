
package salesmanagement.transaction;
import salesmanagement.customer.CustomerManager;
import salesmanagement.customer.Customer;
import salesmanagement.product.ProductManager;
import salesmanagement.product.Product;
import salesmanagement.util.DataInput;
import java.io.*;

public class TransactionManager {
    private final int MAX_TRANS = 100;
    private Transaction[] transactionList = new Transaction[MAX_TRANS];
    private int transactionCount = 0;
    private String getValidID(String prompt) {
        while (true) {
            String input = DataInput.getString(prompt);
            if (input == null || input.trim().isEmpty()) {
                System.out.println("Error: Input cannot be empty! Please try again.");
                continue;
            }
            input = input.trim();
            if (!input.matches("^[a-zA-Z0-9]+$")) {
                System.out.println("Error: ID must contain letters and numbers only! No spaces or special characters.");
                continue;
            }
            return input;
        }
    }

    private String getValidDate(String prompt) {
        while (true) {
            String input = DataInput.getString(prompt);
            if (input == null || input.trim().isEmpty()) {
                System.out.println("Error: Date cannot be empty!");
                continue;
            }            
            input = input.trim();
                   
            if (!input.matches("^\\d{1,2}/\\d{1,2}/\\d{4}$")) {
                System.out.println("Error: Invalid date format! Please use dd/MM/yyyy (e.g., 15/12/2026).");
                continue;
            }
            
            String[] parts = input.split("/");
            int d = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            
            if (y <= 0) {
                System.out.println("Error: Year must be greater than 0!");
                continue;
            }
            if (m < 1 || m > 12) {
                System.out.println("Error: Month must be between 1 and 12!");
                continue;
            }
            
            int maxDays = 31;
            if (m == 4 || m == 6 || m == 9 || m == 11) {
                maxDays = 30;
            } else if (m == 2) {
                if ((y % 400 == 0) || (y % 4 == 0 && y % 100 != 0)) {
                    maxDays = 29;
                } else {
                    maxDays = 28;
                }
            }
            
            if (d < 1 || d > maxDays) {
                System.out.println("Error: Month " + m + " of year " + y + " only has " + maxDays + " days!");
                continue;
            }
            
            
            return input;
        }
    }

 
    private int getValidQuantity(String prompt) {
        while (true) {
            int qty = DataInput.getIntegerNumber(prompt);
            if (qty <= 0) {
                System.out.println("Error: Quantity must be an integer greater than zero!");
                continue;
            }
            return qty;
        }
    }

   
    public void createTransaction(CustomerManager customerManager, ProductManager productManager) {
        if (transactionCount >= MAX_TRANS) {
            System.out.println("Error: Transaction database full!");
            return;
        }  
        System.out.println("\n----------- NEW TRANSACTION -----------");
       
        String transId = "T" + String.format("%02d", (transactionCount + 1));
        System.out.println("Transaction ID: " + transId);

        Customer customer = null;
        while (true) {
            String custId = getValidID("Customer ID: ");
            int custIdx = customerManager.findCustomerIndex(custId);
            if (custIdx == -1) {
                System.out.println("Error: Customer ID does not exist in the system! Please try again.");
                continue;
            }
            customer = customerManager.getCustomer(custIdx);
            break;
        }
        
        String date = getValidDate("Date: ");

        
        Product[] tempProducts = new Product[30];
        int[] tempQuantities = new int[30];
        int tempCount = 0;
        String choice;

       
        do {
          System.out.println("\nTask S5 — Add Product to Transaction");
            Product product = null;
            
            while (true) {
                String prodId = getValidID("Product ID: ");
                int prodIdx = productManager.findProductIndex(prodId);
                
                
                if (prodIdx == -1) {
                    System.out.println("Error: Product does not exist in the system! Please enter a valid Product ID.");
                    continue; 
                }
                
                product = productManager.getProduct(prodIdx);
                
             
                if (product.getStockQuantity() == 0) {
                    System.out.println("Error: This product is OUT OF STOCK (Stock = 0)!");
                    String retry = DataInput.getString("Do you want to try another Product ID? (Y/N): ");
                    if (retry != null && retry.equalsIgnoreCase("Y")) {
                        continue; 
                    } else {
                        product = null; 
                        break; 
                    }
                }
                break; 
            }

            
            if (product == null) {
                while (true) {
                    choice = DataInput.getString("Do you want to add another product to this bill? (Y/N): ");
                    if (choice != null && (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N"))) {
                        break;
                    }
                    System.out.println("Error: Please enter 'Y' for Yes or 'N' for No!");
                }
                continue; 
            }

          
            int quantity = 0;
            while (true) {
                quantity = getValidQuantity("Quantity: ");
                             
                int alreadyInCart = 0;
                for (int i = 0; i < tempCount; i++) {
                   if (tempProducts[i].getId().equalsIgnoreCase(product.getId())) {
                        alreadyInCart += tempQuantities[i];
                    }
                }
                int availableStock = product.getStockQuantity() - alreadyInCart;
                
                if (quantity > availableStock) {
                    System.out.println("Error: Insufficient stock!");
                    System.out.println("Current stock in warehouse: " + product.getStockQuantity());
                    System.out.println("Already added to this bill: " + alreadyInCart);
                    System.out.println("Available quantity left to buy: " + availableStock);
                    System.out.println("Please enter a smaller quantity.");
                    continue;
                }
                break;
            }

          
            tempProducts[tempCount] = product;
            tempQuantities[tempCount] = quantity;
            tempCount++;
            System.out.println("\nOutput:");
            System.out.println("Product added to transaction.");

            while (true) {
                choice = DataInput.getString("Do you want to add another product to this bill? (Y/N): ");
                if (choice != null && (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N"))) {
                    break;
                }
                System.out.println("Error: Please enter 'Y' for Yes or 'N' for No!");
            }
        } while (choice.equalsIgnoreCase("Y") && tempCount < 30);

        
        if (tempCount == 0) {
            System.out.println("Error: Transaction must contain at least one product. Aborted.");
            return;
        }

       
        System.out.println("\nTask S6 — Calculate Total");
        System.out.println("\n----------- BILL SUMMARY -----------");
        System.out.format("%-15s %-8s %-15s %-15s\n", "Product", "Qty", "Price", "Total");
        System.out.println("------------------------------------------------------------------");

       double totalBill = 0;
        for (int i = 0; i < tempCount; i++) {
            Product p = tempProducts[i];
            int q = tempQuantities[i];
            double subTotal = p.getPrice() * q;
            totalBill += subTotal;

            System.out.format("%-15s %-8d %,15.0f %,15.0f\n", p.getName(), q, p.getPrice(), subTotal);
        }

   
        double discountAmount = customer.calculateDiscount(totalBill);
        double finalTotal = totalBill - discountAmount;

        System.out.println("------------------------------------------------------------------");
        System.out.format("Subtotal: %,.0f VND\n", totalBill);
        System.out.format("Discount Applied: -%,.0f VND\n", discountAmount);
        System.out.format("Final Total Amount: %,.0f VND\n", finalTotal);
   
        for (int i = 0; i < tempCount; i++) {
            int oldStock = tempProducts[i].getStockQuantity();
            tempProducts[i].setStockQuantity(oldStock - tempQuantities[i]);
        }

     
        Transaction newTrans = new Transaction(transId, customer, date, tempProducts, tempQuantities, tempCount, finalTotal);
        transactionList[transactionCount] = newTrans;
        transactionCount++;
    }

   
    public void cancelTransaction() {
    System.out.println("\n>> --- CANCEL TRANSACTION ---");
    if (transactionCount == 0) {
        System.out.println("No transactions recorded yet!");
        return;
    }

 
    String transId = getValidID("Enter Transaction ID to cancel: ");
    
  
    Transaction trans = null;
    for (int i = 0; i < transactionCount; i++) {
        if (transactionList[i].getTransactionId().equalsIgnoreCase(transId)) {
            trans = transactionList[i];
            break;
        }
    }

  
    if (trans == null) {
        System.out.println("Error: Transaction ID not found!");
        return;
    }
    if (trans.getStatus().equalsIgnoreCase("Canceled")) {
        System.out.println("Error: This transaction is already canceled!");
        return;
    }

    String confirm = DataInput.getString("Are you sure you want to cancel " + transId + "? (Y/N): ");
    if (confirm.equalsIgnoreCase("Y")) {
        
        Product[] products = trans.getProductList();
        int[] quantities = trans.getQuantityList();
        
        for (int i = 0; i < trans.getProductCount(); i++) {
            Product p = products[i];
            int q = quantities[i];
            p.setStockQuantity(p.getStockQuantity() + q);
        }
        
       
        trans.setStatus("Canceled");
        System.out.println("Transaction " + transId + " has been successfully canceled and stock refunded.");
    } else {
        System.out.println("Cancellation aborted.");
    }
}

 
    public void viewTransactionHistory() {
        System.out.println("\n>> --- VIEW TRANSACTION HISTORY ---");
        if (transactionCount == 0) {
            System.out.println("No transactions recorded yet!");
            return;
        }

        System.out.println("+-----------------+--------------+----------------------+------------------+------------+");
        System.out.println("| Transaction ID  | Date         | Customer Name        | Total Bill       | Status     |");
        System.out.println("+-----------------+--------------+----------------------+------------------+------------+");
        for (int i = 0; i < transactionCount; i++) {
            System.out.println(transactionList[i].toString());
        }
        System.out.println("+-----------------+--------------+----------------------+------------------+------------+");
    }
 
    public void updateTransaction(CustomerManager customerManager, ProductManager productManager) {
        System.out.println("\n>> --- UPDATE TRANSACTION ---");
        if (transactionCount == 0) {
            System.out.println("No transactions recorded yet to update!");
            return;
        }

       
        String transId = getValidID("Enter Transaction ID to update: ");
        Transaction trans = null;
        for (int i = 0; i < transactionCount; i++) {
            if (transactionList[i].getTransactionId().equalsIgnoreCase(transId)) {
                trans = transactionList[i];
                break;
            }
        }

        
        if (trans == null) {
            System.out.println("Error: Transaction ID not found!");
            return;
        }
        if (trans.getStatus().equalsIgnoreCase("Canceled")) {
            System.out.println("Error: Cannot update a canceled transaction!");
            return;
        }

        System.out.println("Transaction found! Status: " + trans.getStatus());
        String confirm = DataInput.getString("Do you want to proceed with updating " + transId + "? (Y/N): ");
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Update aborted.");
            return;
        }

        Product[] oldProducts = trans.getProductList();
        int[] oldQuantities = trans.getQuantityList();
        for (int i = 0; i < trans.getProductCount(); i++) {
            Product p = oldProducts[i];
            int q = oldQuantities[i];
            p.setStockQuantity(p.getStockQuantity() + q);
        }

        System.out.println("\n--- ENTER NEW DETAILS FOR " + transId + " ---");

      
        Customer customer = null;
        while (true) {
            String custId = getValidID("New Customer ID: ");
            int custIdx = customerManager.findCustomerIndex(custId);
            if (custIdx == -1) {
                System.out.println("Error: Customer ID does not exist! Please try again.");
                continue;
            }
            customer = customerManager.getCustomer(custIdx);
            break;
        }

        String date = getValidDate("New Date: ");

       
        Product[] tempProducts = new Product[30];
        int[] tempQuantities = new int[30];
        int tempCount = 0;
        String choice = "N";

        do {
            System.out.println("\nAdd Product to Updated Transaction");
            Product product = null;

            while (true) {
                String prodId = getValidID("Product ID: ");
                int prodIdx = productManager.findProductIndex(prodId);

                if (prodIdx == -1) {
                    System.out.println("Error: Product does not exist! Please enter a valid Product ID.");
                    continue;
                }

                product = productManager.getProduct(prodIdx);

                
                if (product.getStockQuantity() == 0) {
                    System.out.println("Error: This product is OUT OF STOCK (Stock = 0)!");
                    String retry = DataInput.getString("Do you want to try another Product ID? (Y/N): ");
                    if (retry != null && retry.equalsIgnoreCase("Y")) {
                        continue;
                    } else {
                        product = null;
                        break;
                    }
                }
                break;
            }

            if (product == null) {
                while (true) {
                    choice = DataInput.getString("Do you want to add another product to this bill? (Y/N): ");
                    if (choice != null && (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N"))) {
                        break;
                    }
                    System.out.println("Error: Please enter 'Y' for Yes or 'N' for No!");
                }
                continue;
            }

           
            int quantity = 0;
            while (true) {
                quantity = getValidQuantity("Quantity: ");

                int alreadyInCart = 0;
                for (int i = 0; i < tempCount; i++) {
                    
                    if (tempProducts[i].getId().equalsIgnoreCase(product.getId())) {
                        alreadyInCart += tempQuantities[i];
                    }
                }

                int availableStock = product.getStockQuantity() - alreadyInCart;

                if (quantity > availableStock) {
                    System.out.println("Error: Insufficient stock!");
                    System.out.println("Current stock in warehouse: " + product.getStockQuantity());
                    System.out.println("Already added to this bill: " + alreadyInCart);
                    System.out.println("Available quantity left to buy: " + availableStock);
                    System.out.println("Please enter a smaller quantity.");
                    continue;
                }
                break;
            }

            tempProducts[tempCount] = product;
            tempQuantities[tempCount] = quantity;
            tempCount++;
            System.out.println("Product added to transaction.");

            while (true) {
                choice = DataInput.getString("Do you want to add another product to this bill? (Y/N): ");
                if (choice != null && (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N"))) {
                    break;
                }
                System.out.println("Error: Please enter 'Y' for Yes or 'N' for No!");
            }
        } while (choice.equalsIgnoreCase("Y") && tempCount < 30);

       
        if (tempCount == 0) {
            System.out.println("Error: Updated transaction must contain at least one product. Rollbacking to old data.");
            for (int i = 0; i < trans.getProductCount(); i++) {
                Product p = oldProducts[i];
                int q = oldQuantities[i];
                p.setStockQuantity(p.getStockQuantity() - q);
            }
            return;
        }

       
        double totalBill = 0;
        for (int i = 0; i < tempCount; i++) {
            totalBill += tempProducts[i].getPrice() * tempQuantities[i];
        }

        double discountAmount = customer.calculateDiscount(totalBill);
        double finalTotal = totalBill - discountAmount;

       
        for (int i = 0; i < tempCount; i++) {
            int oldStock = tempProducts[i].getStockQuantity();
            tempProducts[i].setStockQuantity(oldStock - tempQuantities[i]);
        }

      
        trans.setCustomer(customer);
        trans.setDate(date);
        trans.setProductList(tempProducts);
        trans.setQuantityList(tempQuantities);
        trans.setProductCount(tempCount);
        trans.setTotalBill(finalTotal);

        System.out.println("\nTransaction " + transId + " has been successfully updated!");
        System.out.format("New Total Amount: %,.0f VND\n", finalTotal);
    }
public int getTransactionCount() {
    return this.transactionCount; 

}


public Transaction[] getTransactionList() {
    return this.transactionList;
    
}

   public boolean saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < transactionCount; i++) {
                pw.println(transactionList[i].toFileString());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving transaction file: " + e.getMessage());
            return false;
        }
    }
 
    public boolean loadFromFile(String filename, CustomerManager customerManager, ProductManager productManager) {
        File file = new File(filename);
        if (!file.exists()) {
            return false;
        }
        transactionCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                String transId = parts[0];
                String customerId = parts[1];
                String date = parts[2];
                double totalBill = Double.parseDouble(parts[3]);
                String status = parts[4];
                int productCount = Integer.parseInt(parts[5]);
 
               
                int custIdx = customerManager.findCustomerIndex(customerId);
                if (custIdx == -1) {
                    System.out.println("Warning: Customer " + customerId + " not found. Skipping transaction " + transId);
                    continue;
                }
                Customer customer = customerManager.getCustomer(custIdx);
 
                Product[] productList = new Product[productCount];
                int[] quantityList = new int[productCount];
 
                if (productCount > 0 && parts.length > 6) {
                    String[] items = parts[6].split(",");
                    for (int i = 0; i < items.length; i++) {
                        String[] pair = items[i].split(":");
                        String prodId = pair[0];
                        int qty = Integer.parseInt(pair[1]);
 
                        int prodIdx = productManager.findProductIndex(prodId);
                        if (prodIdx == -1) {
                            System.out.println("Warning: Product " + prodId + " not found for transaction " + transId);
                            continue;
                        }
                        productList[i] = productManager.getProduct(prodIdx);
                        quantityList[i] = qty;
                    }
                }
 
                Transaction trans = new Transaction(transId, customer, date, productList, quantityList, productCount, totalBill);
                trans.setStatus(status);
 
                transactionList[transactionCount] = trans;
                transactionCount++;
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error loading transaction file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error parsing transaction data: " + e.getMessage());
            return false;
        }
    }


}
        
