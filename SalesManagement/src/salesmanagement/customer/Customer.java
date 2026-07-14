
package salesmanagement.customer;



public abstract class Customer {
    protected String id;
    protected String name;
    protected String phoneNumber;
    protected String address;
    protected String email; 
    public Customer() {
    }

    // Constructor 
    public Customer(String id, String name, String phoneNumber, String address, String email) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email; 
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; } 
    public void setEmail(String email) { this.email = email; } 

    public abstract double calculateDiscount(double totalAmount);
    public String toFileString() {
        return "REGULAR;" + id + ";" + name + ";" + phoneNumber + ";" + address + ";" + email;
    }
    @Override
    public String toString() {
   
        return String.format("| %-10s | %-20s | %-12s | %-15s | %-20s |", id, name, phoneNumber, address, email);
    }
}

