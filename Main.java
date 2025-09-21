import java.util.*;
import java.time.LocalDate;
class User {
  protected int userId;
  protected String name;
  protected String email;
  protected String phone;
  public User(int userId, String name, String email, String phone) {
    this.userId = userId;
    this.name = name;
    this.email = email;
    this.phone = phone;
  }
  public void login() {
    System.out.println(name + " logged in successfully.");
  }
  public void logout() {
    System.out.println(name + " logged out.");
  }
}
class Employee extends User {
  private String department;
  private List<PPE> assignedPPE = new ArrayList<>();
  public Employee(int userId, String name, String email, String phone, String department) {
    super(userId, name, email, phone);
    this.department = department;
  }
  public void assignPPE(PPE ppe) {
    assignedPPE.add(ppe);
    System.out.println("PPE " + ppe.getPpeType() + " assigned to " + name);
  }
  public void viewPPEHistory() {
    if (assignedPPE.isEmpty()) {
      System.out.println(name + " has no PPE history.");
    } else {
      System.out.println("PPE History for " + name + ":");
      for (PPE ppe : assignedPPE) {
        System.out.println(" - " + ppe);
      }
    }
  }
}
class PPE {
  private int ppeId;
  private String ppeType;
  private String status;
  private LocalDate issueDate;
  private LocalDate expiryDate;
  public PPE(int ppeId, String ppeType, LocalDate issueDate, LocalDate expiryDate) {
    this.ppeId = ppeId;
    this.ppeType = ppeType;
    this.issueDate = issueDate;
    this.expiryDate = expiryDate;
    this.status = "Active";
  }
  public int getPpeId() { return ppeId; }
  public String getPpeType() { return ppeType; }
  public LocalDate getExpiryDate() { return expiryDate; }
  public String getStatus() { return status; }
  public void markExpired() {
    if (expiryDate.isBefore(LocalDate.now())) {
      status = "Expired";
    }
  }
  public String toString() {
    return "[PPE#" + ppeId + " " + ppeType + " | Status=" + status +
        " | Expiry=" + expiryDate + "]";
  }
}
class LifecycleRecord {
  private int recordId;
  private int ppeId;
  private int employeeId;
  private LocalDate issueDate;
  private String status;
  public LifecycleRecord(int recordId, int ppeId, int employeeId, LocalDate issueDate) {
    this.recordId = recordId;
    this.ppeId = ppeId;
    this.employeeId = employeeId;
    this.issueDate = issueDate;
    this.status = "Issued";
  }
  public String toString() {
    return "[Record#" + recordId + " PPE#" + ppeId +
        " Employee#" + employeeId + " | Status=" + status + "]";
  }
}
class Admin extends User {
  private List<PPE> ppeInventory;
  private List<LifecycleRecord> records;
  public Admin(int id, String name, String email, String phone) {
    super(id, name, email, phone);
    this.ppeInventory = new ArrayList<>();
    this.records = new ArrayList<>();
  }
  public PPE registerPPE(String type, LocalDate issueDate, LocalDate expiryDate) {
    PPE ppe = new PPE(ppeInventory.size() + 1, type, issueDate, expiryDate);
    ppeInventory.add(ppe);
    System.out.println("Registered PPE: " + ppe);
    return ppe;
  }
  public void managePPE() {
    System.out.println("Current PPE Inventory:");
    for (PPE ppe : ppeInventory) {
      ppe.markExpired();
      System.out.println(" - " + ppe);
    }
  }
  public void issuePPE(Employee emp, PPE ppe) {
    emp.assignPPE(ppe);
    LifecycleRecord rec = new LifecycleRecord(records.size() + 1, ppe.getPpeId(), emp.userId, LocalDate.now());
    records.add(rec);
  }

  public void generateReports() {
    System.out.println("=== PPE Lifecycle Records ===");
    for (LifecycleRecord rec : records) {
      System.out.println(rec);
    }
  }
  public List<PPE> getInventory() { return ppeInventory; }
}
public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Admin admin = new Admin(1, "Admin", "admin@company.com", "9999999999");
    Employee emp = new Employee(101, "Employee", "emp@company.com", "8888888888", "Maintenance");
    boolean running = true;
    while (running) {
      System.out.println("\n===== PPE Tracker Menu =====");
      System.out.println("1. Register PPE");
      System.out.println("2. Issue PPE to Employee");
      System.out.println("3. View Employee PPE History");
      System.out.println("4. View PPE Inventory");
      System.out.println("5. Generate Reports");
      System.out.println("6. Exit");
      System.out.print("Choose an option: ");
      int choice = sc.nextInt();
      sc.nextLine();
      switch (choice) {
        case 1:
          System.out.print("Enter PPE Type: ");
          String type = sc.nextLine();
          System.out.print("Enter expiry months: ");
          int months = sc.nextInt();
          PPE newPPE = admin.registerPPE(type, LocalDate.now(), LocalDate.now().plusMonths(months));
          break;
        case 2:
          if (admin.getInventory().isEmpty()) {
            System.out.println("No PPE available to issue!");
            break;
          }
          System.out.println("Available PPE:");
          for (PPE p : admin.getInventory()) {
            System.out.println(p.getPpeId() + ". " + p);
          }
          System.out.print("Enter PPE ID to issue: ");
          int pid = sc.nextInt();
          if (pid > 0 && pid <= admin.getInventory().size()) {
            admin.issuePPE(emp, admin.getInventory().get(pid - 1));
          } else {
            System.out.println("Invalid PPE ID!");
          }
          break;
        case 3:
          emp.viewPPEHistory();
          break;
        case 4:
          admin.managePPE();
          break;
        case 5:
          admin.generateReports();
          break;
        case 6:
          running = false;
          System.out.println("Exiting PPE Tracker...");
          break;
        default:
          System.out.println("Invalid choice!");
      }
    }
    sc.close();
  }
}