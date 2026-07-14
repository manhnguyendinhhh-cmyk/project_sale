
package salesmanagement.transaction;
import salesmanagement.customer.CustomerManager;
import salesmanagement.product.ProductManager;
import salesmanagement.util.DataInput;

public class TransactionTest {
    public void processTransactionMenu(TransactionManager transManager, CustomerManager custManager, ProductManager prodManager) {
        int choice;
        do {
            System.out.println("\n--- TRANSACTION MANAGEMENT ---");
            System.out.println("1. Create New Transaction");
            System.out.println("2. Cancel Transaction");
            System.out.println("3. View Transaction History");
            System.out.println("4. Update Transaction");
            System.out.println("5. Back to Main Menu");
            System.out.println("---------------------------------");
            choice = DataInput.getIntegerNumber("Choose an option: ");

            switch (choice) {
                case 1:
                    transManager.createTransaction(custManager, prodManager);
                    break;
                case 2:
                  transManager.cancelTransaction(); 
                    break;
                case 3:
                    transManager.viewTransactionHistory();
                    break;
                case 4:
                    transManager.updateTransaction(custManager, prodManager);
                    break;
                    case 5:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        } while (choice != 5);
    }
}
