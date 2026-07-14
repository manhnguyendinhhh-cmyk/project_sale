
package salesmanagement.file;
import salesmanagement.customer.CustomerManager;
import salesmanagement.product.ProductManager;
import salesmanagement.transaction.TransactionManager;
 
public class FileManager {
    private static final String CUSTOMER_FILE = "customers.txt";
    private static final String PRODUCT_FILE = "products.txt";
    private static final String TRANSACTION_FILE = "transactions.txt";
 
    
    public void loadAllData(CustomerManager customerManager, ProductManager productManager, TransactionManager transactionManager) {
        
        customerManager.loadFromFile(CUSTOMER_FILE);
        productManager.loadFromFile(PRODUCT_FILE);
        transactionManager.loadFromFile(TRANSACTION_FILE, customerManager, productManager);
        System.out.println("Data loaded from files successfully!");
    }
 
   
    public void saveAllData(CustomerManager customerManager, ProductManager productManager, TransactionManager transactionManager) {
        customerManager.saveToFile(CUSTOMER_FILE);
        productManager.saveToFile(PRODUCT_FILE);
        transactionManager.saveToFile(TRANSACTION_FILE);
        System.out.println("Data saved to files successfully!");
    }
    
   
}
