
package salesmanagement;

import salesmanagement.product.ProductTest;
import salesmanagement.product.ProductManager;
import salesmanagement.util.DataInput;
import salesmanagement.customer.CustomerManager;
import salesmanagement.customer.CustomerTest;
import salesmanagement.report.ReportManager;
import salesmanagement.report.ReportTest;
import salesmanagement.transaction.TransactionManager;
import salesmanagement.transaction.TransactionTest;
import salesmanagement.file.FileManager;
public class SalesManagement {
    public static void main(String[] args) {
        CustomerManager customerManager = new CustomerManager();
        ProductManager productManager = new ProductManager();
        ProductTest productTest = new ProductTest();
        TransactionManager transactionManager = new TransactionManager();
        TransactionTest transactionTest = new TransactionTest();
        ReportManager reportManager = new ReportManager();
        ReportTest reportTest = new ReportTest();
        FileManager fileManager = new FileManager();
        fileManager.loadAllData(customerManager, productManager, transactionManager);
        int choice;

        do {
            System.out.println("=========================================");
            System.out.println("         SALES MANAGEMENT SYSTEM         ");
            System.out.println("=========================================");
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Customers");
            System.out.println("3. Manage Sales Transactions");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.println("-----------------------------------------");
            
            choice = DataInput.getIntegerNumber("Choose an option: ");
            
            switch (choice) {
                case 1:
                    productTest.processProductMenu(productManager);
                    break;
                case 2:
                   CustomerTest customerTest = new CustomerTest();
                   customerTest.processCustomerMenu(customerManager); 
                          break;
                case 3:
                   transactionTest.processTransactionMenu(transactionManager, customerManager, productManager);
                    break;
                case 4:
                   reportTest.processReportMenu(reportManager, transactionManager);
                break;
                case 5:
                    fileManager.saveAllData(customerManager, productManager, transactionManager);
                    System.out.println("Goodbye! Program exited.");
                    System.exit(0); 
                    break;
                default:
                    System.out.println("Please try again."); 
            }
        } while (true); 
    }
    }
    

