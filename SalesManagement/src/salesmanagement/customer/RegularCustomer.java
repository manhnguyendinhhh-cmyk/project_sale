
package salesmanagement.customer;

import salesmanagement.customer.Customer;


public class RegularCustomer extends Customer {
    public RegularCustomer() {
    }

    
    public RegularCustomer(String id, String name, String phoneNumber, String address, String email) {
        super(id, name, phoneNumber, address, email); 
    }

    @Override
    public double calculateDiscount(double totalAmount) {
        return totalAmount * 0.02; 
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" %-15s | %-13s |", "Regular", "2%");
    }
}
