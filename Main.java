import java.util.*;
import java.time.LocalDate;

class Person {
    int uid;
    String uname;
    String mailid;
    String phone;

    Person(int id, String name, String mail, String ph) {
        uid = id;
        uname = name;
        mailid = mail;
        phone = ph;
    }

    void doLogin() {
        System.out.println(uname + " logged in :)");
    }

    void doLogout() {
        System.out.println(uname + " logged out...");
    }
}

class Staff extends Person {
    String dept;
    ArrayList<Gear> myppe = new ArrayList<>();

    Staff(int id, String name, String mail, String ph, String d) {
        super(id, name, mail, ph);
        dept = d;
    }

    void takePpe(Gear g) {
        myppe.add(g);
        System.out.println(uname + " got PPE -> " + g.type);
    }

    void showHistory() {
        if (myppe.size() == 0) {
            System.out.println(uname + " has no PPE history yet.");
        } else {
            System.out.println("History of " + uname + ":");
            for (int i = 0; i < myppe.size(); i++) {
                System.out.println(myppe.get(i));
            }
        }
    }
}

class Gear {
    int gid;
    String type;
    String stat;
    LocalDate given;
    LocalDate expiry;

    Gear(int id, String t, LocalDate issue, LocalDate exp) {
        gid = id;
        type = t;
        given = issue;
        expiry = exp;
        stat = "Active";
    }

    void checkStatus() {
        if (expiry.isBefore(LocalDate.now())) {
            stat = "Expired";
        }
    }

    public String toString() {
        return "[Gear#" + gid + " " + type + " | " + stat + " till " + expiry + "]";
    }
}

class Record {
    int rid;
    int gid;
    int staffId;
    LocalDate date;
    String status;

    Record(int r, int g, int s, LocalDate d) {
        rid = r;
        gid = g;
        staffId = s;
        date = d;
        status = "Issued";
    }

    public String toString() {
        return "[Rec#" + rid + " Gear#" + gid + " Staff#" + staffId + " | " + status + "]";
    }
}

class Admin extends Person {
    ArrayList<Gear> store = new ArrayList<>();
    ArrayList<Record> recs = new ArrayList<>();

    Admin(int id, String n, String m, String p) {
        super(id, n, m, p);
    }

    Gear addPpe(String t, LocalDate iss, LocalDate exp) {
        Gear g = new Gear(store.size() + 1, t, iss, exp);
        store.add(g);
        System.out.println("Added new PPE -> " + g);
        return g;
    }

    void showPpe() {
        if (store.size() == 0) {
            System.out.println("No items in store.");
        } else {
            System.out.println("=== PPE Store ===");
            for (int i = 0; i < store.size(); i++) {
                store.get(i).checkStatus();
                System.out.println(store.get(i));
            }
        }
    }

    void givePpe(Staff st, Gear g) {
        st.takePpe(g);
        Record r = new Record(recs.size() + 1, g.gid, st.uid, LocalDate.now());
        recs.add(r);
    }

    void showRecs() {
        if (recs.size() == 0) {
            System.out.println("No records yet.");
        } else {
            System.out.println("== All Records ==");
            for (Record r : recs) {
                System.out.println(r);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Admin adm = new Admin(1, "Admin1", "adm@cmp.com", "9090909090");
        Staff st1 = new Staff(100, "Ravi", "ravi@cmp.com", "8080808080", "Workshop");

        boolean run = true;
        while (run) {
            System.out.println("\n==== PPE Menu ====");
            System.out.println("1. Add PPE");
            System.out.println("2. Give PPE");
            System.out.println("3. Show Staff History");
            System.out.println("4. Show Store");
            System.out.println("5. Show Records");
            System.out.println("6. Exit");
            System.out.print("Choice: ");
            int c = sc.nextInt();
            sc.nextLine();

            if (c == 1) {
                System.out.print("Enter type: ");
                String t = sc.nextLine();
                System.out.print("Enter expiry months: ");
                int m = sc.nextInt();
                adm.addPpe(t, LocalDate.now(), LocalDate.now().plusMonths(m));
            } else if (c == 2) {
                if (adm.store.isEmpty()) {
                    System.out.println("No PPE available now!");
                } else {
                    System.out.println("Available:");
                    for (int i = 0; i < adm.store.size(); i++) {
                        System.out.println((i + 1) + ". " + adm.store.get(i));
                    }
                    System.out.print("Enter id: ");
                    int id = sc.nextInt();
                    if (id > 0 && id <= adm.store.size()) {
                        adm.givePpe(st1, adm.store.get(id - 1));
                    } else {
                        System.out.println("Wrong id!");
                    }
                }
            } else if (c == 3) {
                st1.showHistory();
            } else if (c == 4) {
                adm.showPpe();
            } else if (c == 5) {
                adm.showRecs();
            } else if (c == 6) {
                run = false;
                System.out.println("Bye Bye...");
            } else {
                System.out.println("Invalid input!");
            }
        }

        sc.close();
    }
}