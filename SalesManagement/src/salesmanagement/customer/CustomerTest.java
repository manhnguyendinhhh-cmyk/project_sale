
package salesmanagement.customer;

import salesmanagement.util.DataInput;
import salesmanagement.customer.Customer;


public class CustomerTest {
   
    public void processCustomerMenu(CustomerManager customerManager) {
        int subChoice;
        do {
            System.out.println("\n--- CUSTOMER MANAGEMENT ---");
            System.out.println("1. Add Customer");
            System.out.println("2. Update Customer");
            System.out.println("3. Remove Customer");
            System.out.println("4. View All Customers");
            System.out.println("5. Search Customer By ID");
            System.out.println("6. Back to Main Menu");
            System.out.println("---------------------------");

            subChoice = DataInput.getIntegerNumber("Choose a sub-option: ");

            switch (subChoice) {
                case 1:
                    processCase01(customerManager); 
                    break;
                case 2:
                    processCase02(customerManager); 
                    break;
                case 3:
                    processCase03(customerManager); 
                    break;
                case 4:
                    processCase04(customerManager); 
                    break;
                case 5:
                    processCase05(customerManager); 
                    break;
                case 6:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid option! Please choose from 1 to 6.");
            }
        } while (subChoice != 6);
    }

    
    public void processCase01(CustomerManager customerManager) {
        System.out.println("\n>> Task C1 - Add New Customer");

        if (customerManager.isFull()) {
            System.out.println("Cannot add. Customer database is full!");
            return;
        }

        //Auto ID
        int nextIdNumber = customerManager.getCount() + 1;
        String id = "C" + String.format("%02d", nextIdNumber);
        System.out.println("Generated Customer ID: " + id);

        // type of customer
        int type;
        while (true) {
            type = DataInput.getIntegerNumber("Choose Customer Type (1. Regular / 2. VIP): ");
            if (type == 1 || type == 2) {
                break; 
            }
            System.out.println("Error: Invalid type! Please choose 1 or 2.");
        }

        // input name
        String name;
        while (true) {
            name = DataInput.getString("Enter Customer Name: ");
            if (name.trim().isEmpty()) {
                System.out.println("Error: Customer Name cannot be empty! Please enter again.");
                continue; 
            }
            if (!name.matches("^[a-zA-Z\\s]+$")) {
                System.out.println("Error: Customer Name must contain alphabetic characters only (no numbers)! Please enter again.");
                continue; 
            }
            break; 
        }

        //input phonenumber
        String phone;
        while (true) {
            phone = DataInput.getString("Enter Phone Number: ");
            if (phone.trim().isEmpty()) {
                System.out.println("Error: Phone Number cannot be empty! Please enter again.");
                continue;
            }
            if (!phone.matches("^\\d{9,11}$")) {
                System.out.println("Error: Phone Number must contain digits only (9 to 11 digits)! Please enter again.");
                continue;
            }
            break;
        }

        // input address
        String address;
        while (true) {
            address = DataInput.getString("Enter Address (e.g., 123/45 Nguyen Hue): ");
            if (address.trim().isEmpty()) {
                System.out.println("Error: Address cannot be empty! Please enter again.");
                continue;
            }
            if (!address.matches("^[a-zA-Z0-9\\s/]+$")) {
                System.out.println("Error: Address contains invalid characters! Only letters, numbers, spaces, and '/' are allowed.");
                continue;
            }
            break;
        }

        //input email
        String email;
        while (true) {
            email = DataInput.getString("Enter Email (e.g., alex@gmail.com): ");
            if (email.trim().isEmpty()) {
                System.out.println("Error: Email cannot be empty! Please enter again.");
                continue;
            }
            if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                System.out.println("Error: Invalid email format! It must follow 'example@domain.com'. Please enter again.");
                continue;
            }
            break;
        }

        
        Customer newCustomer = null;
        if (type == 1) {
            newCustomer = new RegularCustomer(id, name, phone, address, email);
        } else {
            String rank;
            while (true) {
                rank = DataInput.getString("Enter VIP Rank (Gold/Silver): ");
                if (rank.trim().isEmpty()) {
                    System.out.println("Error: VIP Rank cannot be empty! Please enter again.");
                    continue;
                }
                if (rank.equalsIgnoreCase("Gold") || rank.equalsIgnoreCase("Silver")) {
                    break;
                }
                System.out.println("Error: VIP Rank must be 'Gold' or 'Silver'! Please enter again.");
            }
            newCustomer = new VipCustomer(id, name, phone, address, email, rank.toUpperCase());
        }

        // add to array
        if (customerManager.addCustomer(newCustomer)) {
            System.out.println("Customer " + id + " added successfully!");
        } else {
            System.out.println("Customer added failed!");
        }
    }
    // Task 2: Update Customer
    public void processCase02(CustomerManager customerManager) {
        System.out.println("\n>> Task C2 - Update Customer");

        //empty customer
        if (customerManager.getCount() == 0) {
            System.out.println("No customers available to update!");
            return;
        }

        int foundIndex = -1;
        String id = "";

        // check ID
        while (true) {
            id = DataInput.getString("Enter Customer ID to update: ");
            if (id.trim().isEmpty()) {
                System.out.println("Error: Customer ID cannot be empty! Please enter again.");
                continue;
            }

            foundIndex = customerManager.findCustomerIndex(id);
            if (foundIndex == -1) {
                System.out.println("Error: Customer with ID '" + id + "' does not exist! Please try another ID.");
                continue; 
            }
            break; 
        }

        
        Customer oldCustomer = customerManager.getCustomer(foundIndex);
        System.out.println("Customer found! Enter new details (All fields are required):");

        //update name
        String name;
        while (true) {
            name = DataInput.getString("Enter New Customer Name: ");
            if (name.trim().isEmpty()) {
                System.out.println("Error: Customer Name cannot be empty! Please enter again.");
                continue;
            }
            if (!name.matches("^[a-zA-Z\\s]+$")) {
                System.out.println("Error: Customer Name must contain alphabetic characters only (no signs/numbers)! Please enter again.");
                continue;
            }
            break;
        }

        // update phonenumber
        String phone;
        while (true) {
            phone = DataInput.getString("Enter New Phone Number: ");
            if (phone.trim().isEmpty()) {
                System.out.println("Error: Phone Number cannot be empty! Please enter again.");
                continue;
            }
            if (!phone.matches("^\\d{9,11}$")) {
                System.out.println("Error: Phone Number must contain digits only (9 to 11 digits)! Please enter again.");
                continue;
            }
            break;
        }

        // update address
        String address;
        while (true) {
            address = DataInput.getString("Enter New Address (e.g., 123/45 Nguyen Hue): ");
            if (address.trim().isEmpty()) {
                System.out.println("Error: Address cannot be empty! Please enter again.");
                continue;
            }
            if (!address.matches("^[a-zA-Z0-9\\s/]+$")) {
                System.out.println("Error: Address contains invalid characters! Only letters (no signs), numbers, spaces, and '/' are allowed.");
                continue;
            }
            break;
        }

        // update email
        String email;
        while (true) {
            email = DataInput.getString("Enter New Email: ");
            if (email.trim().isEmpty()) {
                System.out.println("Error: Email cannot be empty! Please enter again.");
                continue;
            }
            if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                System.out.println("Error: Invalid email format! Please enter again.");
                continue;
            }
            break;
        }

        
        Customer updatedCustomer = null;
        if (oldCustomer instanceof VipCustomer) {
            
            String rank;
            while (true) {
                rank = DataInput.getString("Enter New VIP Rank (Gold/Silver): ");
                if (rank.trim().isEmpty()) {
                    System.out.println("Error: VIP Rank cannot be empty! Please enter again.");
                    continue;
                }
                if (rank.equalsIgnoreCase("Gold") || rank.equalsIgnoreCase("Silver")) {
                    break;
                }
                System.out.println("Error: VIP Rank must be 'Gold' or 'Silver'! Please enter again.");
            }
            
            updatedCustomer = new VipCustomer(oldCustomer.getId(), name, phone, address, email, rank.toUpperCase());
        } else {
            
            updatedCustomer = new RegularCustomer(oldCustomer.getId(), name, phone, address, email);
        }

       
        if (customerManager.updateCustomer(foundIndex, updatedCustomer)) {
            System.out.println("Customer " + oldCustomer.getId() + " updated successfully!");
        } else {
            System.out.println("Customer updated failed!");
        }
    }
    //remove customer
    public void processCase03(CustomerManager customerManager) {
        System.out.println("\n>> Task C3 - Remove Customer");

      
        if (customerManager.getCount() == 0) {
            System.out.println("No customers available to remove!");
            return;
        }

        int foundIndex = -1;
        String id = "";

       
        while (true) {
            id = DataInput.getString("Enter Customer ID to remove: ");
            if (id.trim().isEmpty()) {
                System.out.println("Error: Customer ID cannot be empty! Please enter again.");
                continue;
            }

            foundIndex = customerManager.findCustomerIndex(id);
            if (foundIndex == -1) {
                System.out.println("Error: Customer with ID '" + id + "' does not exist! Please try another ID.");
                continue; 
            }
            break; 
        }
        Customer targetCustomer = customerManager.getCustomer(foundIndex);
        System.out.println("Customer Found: [ID: " + targetCustomer.getId() + " | Name: " + targetCustomer.getName() + "]");

    
        String confirm;
        while (true) {
            confirm = DataInput.getString("Are you sure you want to remove this customer? (Y/N): ");
            if (confirm.trim().isEmpty()) {
                System.out.println("Error: Confirmation cannot be empty! Please enter Y or N.");
                continue;
            }
            if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")) {
                break; 
            }
            System.out.println("Error: Invalid choice! Please enter 'Y' for Yes or 'N' for No.");
        }

       
        if (confirm.equalsIgnoreCase("N")) {
            System.out.println("Removal canceled. Returning to Customer Menu.");
            return; 
        }
   
        if (customerManager.removeCustomer(foundIndex)) {
            System.out.println("Customer " + id + " has been removed successfully!");
        } else {
            System.out.println("Customer removal failed!");
        }
    }
    
    public void processCase04(CustomerManager customerManager) {
        System.out.println("\n>> Task C4 - View All Customers");

        int total = customerManager.getCount();
        if (total == 0) {
            System.out.println("Customer database is currently empty!");
            return;
        }

        System.out.println("Total customers registered: " + total);
        
        System.out.println("===========================================================================================================");
        System.out.format("| %-5s | %-20s | %-12s | %-25s | %-25s | %-13s |\n", 
                      "ID", "Customer Name", "Phone", "Address", "Email", "Type/Rank");
        System.out.println("===========================================================================================================");

        Customer[] list = customerManager.getCustomers(); 
        for (int i = 0; i < total; i++) {
            Customer c = list[i];
            
            String typeOrRank = "Regular";
            if (c instanceof VipCustomer) {
                typeOrRank = "VIP (" + ((VipCustomer) c).getVipRank() + ")";
            }

            
            System.out.format("| %-5s | %-20s | %-12s | %-25s | %-25s | %-13s |\n", 
                          c.getId(), c.getName(), c.getPhoneNumber(), c.getAddress(), c.getEmail(), typeOrRank);
        }

       
        System.out.println("===========================================================================================================");
    }
   
    public void processCase05(CustomerManager customerManager) {
        System.out.println("\n>> Task C5 - Search Customer By ID");

       
        if (customerManager.getCount() == 0) {
            System.out.println("Customer database is empty!");
            return;
        }

        int foundIndex = -1;
        String id = "";

       
        while (true) {
            id = DataInput.getString("Enter Customer ID to search: ");
            if (id.trim().isEmpty()) {
                System.out.println("Error: Search ID cannot be empty! Please enter again.");
                continue;
            }
            
            foundIndex = customerManager.findCustomerIndex(id);
            break; 
        }

      
        if (foundIndex == -1) {
            System.out.println("Customer with ID '" + id + "' does not exist in the system!");
        } else {
            
            Customer c = customerManager.getCustomer(foundIndex);
            System.out.println("\nCustomer Found:");
            System.out.println("===========================================================================================================");
            System.out.format("| %-5s | %-20s | %-12s | %-25s | %-25s | %-13s |\n", 
                          "ID", "Customer Name", "Phone", "Address", "Email", "Type/Rank");
            System.out.println("===========================================================================================================");
            
            String typeOrRank = "Regular";
            if (c instanceof VipCustomer) {
                typeOrRank = "VIP (" + ((VipCustomer) c).getVipRank() + ")";
            }
            
            System.out.format("| %-5s | %-20s | %-12s | %-25s | %-25s | %-13s |\n", 
                          c.getId(), c.getName(), c.getPhoneNumber(), c.getAddress(), c.getEmail(), typeOrRank);
            System.out.println("===========================================================================================================");
        }
    }
}
