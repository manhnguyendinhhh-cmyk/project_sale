
package salesmanagement.transaction;
import salesmanagement.customer.Customer;
import salesmanagement.product.Product;
public class Transaction {
    private String transactionId;
    private Customer customer;
    private String date;
    private Product[] productList;
    private int[] quantityList;
    private int productCount;
    private double totalBill;
    private String status; 

    public Transaction(String transactionId, Customer customer, String date, Product[] productList, int[] quantityList, int productCount, double totalBill) {
        this.transactionId = transactionId;
        this.customer = customer;
        this.date = date;
        this.productList = productList;
        this.quantityList = quantityList;
        this.productCount = productCount;
        this.totalBill = totalBill;
        this.status = "Completed";
    }
    
   
    public String getTransactionId() { return transactionId; }
    public Customer getCustomer() { return customer; }
    public String getDate() { return date; }
    public Product[] getProductList() { return productList; }
    public int[] getQuantityList() { return quantityList; }
    public int getProductCount() { return productCount; }
    public double getTotalBill() { return totalBill; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setDate(String date) {
        this.date = date;
    }
  
    public void setProductList(Product[] productList) {
        this.productList = productList;
    }

    public void setQuantityList(int[] quantityList) {
        this.quantityList = quantityList;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(transactionId).append(";")
          .append(customer.getId()).append(";")
          .append(date).append(";")
          .append(String.format(java.util.Locale.US, "%.2f", totalBill)).append(";")
          .append(status).append(";")
          .append(productCount).append(";");
 
        for (int i = 0; i < productCount; i++) {
            sb.append(productList[i].getId()).append(":").append(quantityList[i]);
            if (i < productCount - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    @Override
   public String toString() {
        return String.format("| %-15s | %-12s | %-20s | %,16.0f | %-10s |", 
                transactionId, date, customer.getName(), totalBill, status);
    }
   

}
