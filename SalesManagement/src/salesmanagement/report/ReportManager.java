/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesmanagement.report;
import salesmanagement.product.Product;
import salesmanagement.transaction.Transaction;
import salesmanagement.transaction.TransactionManager;

/**
 *
 * @author Admin
 */
public class ReportManager {
  public void generateRevenueReport(TransactionManager transManager) {
        System.out.println("\n>> --- REVENUE REPORT ---");
        
        int totalTransactions = transManager.getTransactionCount();
        if (totalTransactions == 0) {
            System.out.println("No transactions recorded yet to calculate revenue!");
            return;
        }

        double totalRevenue = 0;
        int completedCount = 0;
        int canceledCount = 0;

        for (int i = 0; i < totalTransactions; i++) {
            Transaction trans = transManager.getTransactionList()[i];
            if (trans.getStatus().equalsIgnoreCase("Completed")) {
                totalRevenue += trans.getTotalBill();
                completedCount++;
            } else if (trans.getStatus().equalsIgnoreCase("Canceled")) {
                canceledCount++;
            }
        }

        System.out.println("+--------------------------------------------------+");
        System.out.println("|               STORE PERFORMANCE SUMMARY          |");
        System.out.println("+--------------------------------------------------+");
        System.out.format("| Total Transactions Recorded : %-18d |\n", totalTransactions);
        System.out.format("| - Completed Transactions    : %-18d |\n", completedCount);
        System.out.format("| - Canceled Transactions     : %-18d |\n", canceledCount);
        System.out.println("+--------------------------------------------------+");
        System.out.format("| TOTAL REVENUE               : %,14.0f VND |\n", totalRevenue);
        System.out.println("+--------------------------------------------------+");
    }

   
    public void generateBestSellingReport(TransactionManager transManager) {
        System.out.println("\n>> --- BEST-SELLING PRODUCTS REPORT ---");

        int totalTransactions = transManager.getTransactionCount();
        if (totalTransactions == 0) {
            System.out.println("No transactions recorded yet to analyze product sales!");
            return;
        }

        Product[] uniqueProducts = new Product[200];
        int[] totalSoldQuantities = new int[200];
        int uniqueCount = 0;

        for (int i = 0; i < totalTransactions; i++) {
            Transaction trans = transManager.getTransactionList()[i];
            
            if (trans.getStatus().equalsIgnoreCase("Canceled")) {
                continue; 
            }

            Product[] pList = trans.getProductList();
            int[] qList = trans.getQuantityList();
            int pCount = trans.getProductCount();

            for (int j = 0; j < pCount; j++) {
                Product prod = pList[j];
                int qty = qList[j];

                int foundIdx = -1;
                for (int k = 0; k < uniqueCount; k++) {
                    if (uniqueProducts[k].getId().equalsIgnoreCase(prod.getId())) {
                        foundIdx = k;
                        break;
                    }
                }

                if (foundIdx != -1) {
                    totalSoldQuantities[foundIdx] += qty;
                } else {
                    uniqueProducts[uniqueCount] = prod;
                    totalSoldQuantities[uniqueCount] = qty;
                    uniqueCount++;
                }
            }
        }

        if (uniqueCount == 0) {
            System.out.println("No products have been successfully sold yet!");
            return;
        }

        for (int i = 0; i < uniqueCount - 1; i++) {
            for (int j = 0; j < uniqueCount - i - 1; j++) {
                if (totalSoldQuantities[j] < totalSoldQuantities[j + 1]) {
                    int tempQty = totalSoldQuantities[j];
                    totalSoldQuantities[j] = totalSoldQuantities[j + 1];
                    totalSoldQuantities[j + 1] = tempQty;

                    Product tempProd = uniqueProducts[j];
                    uniqueProducts[j] = uniqueProducts[j + 1];
                    uniqueProducts[j + 1] = tempProd;
                }
            }
        }

        System.out.println("+------------+----------------------+-----------------+----------------+");
        System.out.println("| Product ID | Product Name         | Category        | Total Quantity |");
        System.out.println("+------------+----------------------+-----------------+----------------+");
        
        for (int i = 0; i < uniqueCount; i++) {
            System.out.format("| %-10s | %-20s | %-15s | %-14d |\n",
                    uniqueProducts[i].getId(),
                    uniqueProducts[i].getName(),
                    uniqueProducts[i].getCategory(),
                    totalSoldQuantities[i]);
        }
        System.out.println("+------------+----------------------+-----------------+----------------+");
        System.out.println("⭐ BEST SELLER: " + uniqueProducts[0].getName() + " (" + totalSoldQuantities[0] + " units sold!)");
    }

    void generateBestSellingreport(TransactionManager transManager) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
