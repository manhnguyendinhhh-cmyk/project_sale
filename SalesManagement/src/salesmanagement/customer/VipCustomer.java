
package salesmanagement.customer;

import salesmanagement.customer.Customer;

public class VipCustomer extends Customer {
    private String vipRank; 

    public VipCustomer() {
    }
    public VipCustomer(String id, String name, String phoneNumber, String address, String email, String vipRank) {
        super(id, name, phoneNumber, address, email); 
        this.vipRank = vipRank;
    }

    

    public String getVipRank() { return vipRank; }
    public void setVipRank(String vipRank) { this.vipRank = vipRank; }

    @Override
    public double calculateDiscount(double totalAmount) {
        return totalAmount * 0.10; 
    }
 @Override
    public String toFileString() {
        return "VIP;" + id + ";" + name + ";" + phoneNumber + ";" + address + ";" + email + ";" + vipRank;
    }
    @Override
    public String toString() {
        String typeWithRank = "VIP (" + vipRank + ")";
        return super.toString() + String.format(" %-15s | %-13s |", typeWithRank, "10%");
    }
}

