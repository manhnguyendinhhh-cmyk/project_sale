
package salesmanagement.report;
import salesmanagement.transaction.TransactionManager; 
import salesmanagement.util.DataInput;

public class ReportTest {
  public void processReportMenu(ReportManager reportManager, TransactionManager transManager) {
        int choice;
        do {
            System.out.println("\n--- REPORT & STATISTICS ---");
            System.out.println("1. Best-Selling Products");
            System.out.println("2. Revenue Report");
            System.out.println("3. Back to Main Menu");
            System.out.println("---------------------------------");
            choice = DataInput.getIntegerNumber("Choose an option: ");

            switch (choice) {
                case 1:
                   
                    reportManager.generateBestSellingReport(transManager);
                    break;
                case 2:
                   
                    reportManager.generateRevenueReport(transManager);
                    break;
                case 3:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        } while (choice != 3);
    }
}
