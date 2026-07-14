
package salesmanagement.customer;
import java.io.*;
import salesmanagement.customer.Customer;
public class CustomerManager {
    private final int MAX = 50;
   
    private Customer customerList[] = new Customer[MAX];
    private int count = 0;

    
    public boolean isFull() {
        return count == MAX;
    }

    
    public int findCustomerIndex(String id) {
        for (int i = 0; i < count; i++) {
            if (customerList[i].getId().equalsIgnoreCase(id)) {
                return i; 
            }
        }
        return -1; 
    }

 
    public boolean addCustomer(Customer c) {
        if (isFull()) {
            return false; 
        }
        customerList[count] = c; 
        count++; 
        return true;
    }
    
    
    public boolean updateCustomer(int index, Customer updatedCustomer) {
        if (index >= 0 && index < count) {
            customerList[index] = updatedCustomer;
            return true;
        }
        return false;
    }

    
    public Customer getCustomer(int index) {
        if (index >= 0 && index < count) {
            return customerList[index]; 
        }
        return null; 
    }
    
  
    public boolean removeCustomer(int index) {
        if (index >= 0 && index < count) {
            for (int i = index; i < count - 1; i++) {
                customerList[i] = customerList[i + 1];
            }
            customerList[count - 1] = null; 
            count--; 
            return true;
        }
        return false;
    }
    
  
    public int getCount() {
        return count;
    }

   public Customer[] getCustomers() {
        return this.customerList;
    }
 public boolean saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < count; i++) {
                pw.println(customerList[i].toFileString());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving customer file: " + e.getMessage());
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
                String type = parts[0];
                String id = parts[1];
                String name = parts[2];
                String phone = parts[3];
                String address = parts[4];
                String email = parts[5];
 
                Customer c;
                if (type.equals("VIP")) {
                    String rank = parts[6];
                    c = new VipCustomer(id, name, phone, address, email, rank);
                } else {
                    c = new RegularCustomer(id, name, phone, address, email);
                }
                addCustomer(c);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error loading customer file: " + e.getMessage());
            return false;
        }
    }

    
}
