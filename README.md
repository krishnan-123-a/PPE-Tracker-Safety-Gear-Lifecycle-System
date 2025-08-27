# PPE-Tracker-Safety-Gear-Lifecycle-System
**PPE TRACKER – SAFETY GEAR LIFECYCLE SYSTEM**

The PPE Tracker – Safety Gear Lifecycle System is a Java-based application integrated with a MySQL database to simplify and automate the management of personal protective equipment (PPE) throughout its lifecycle. It manages employees, safety gear issuance, usage history, inspections, and replacement tracking to ensure workplace safety and compliance.

**PROBLEM STATEMENT**

Many industries face challenges in tracking issued PPE, monitoring usage, ensuring timely replacement, and maintaining compliance with safety regulations. Manual tracking often leads to misplaced records, unsafe usage of expired gear, and increased safety risks.

**SOLUTION**

This system provides a centralized software platform to manage employee PPE issuance, inspections, and replacement schedules seamlessly. It ensures efficient resource utilization, improved safety compliance, and transparent tracking of PPE usage history.

**TARGET USERS**

**Administrators**: Manage PPE inventory, allocate equipment, and generate reports.

**Employees:** View assigned PPE, check validity, and request replacements.

**Safety Officers:** Inspect PPE, update condition status, and ensure compliance.

**Managers:** Oversee operations, track PPE distribution, and ensure timely replacement.

**FEATURES**

Employee registration and PPE issuance tracking

PPE registration and lifecycle management (issue → usage → inspection → replacement)

Inspection scheduling and compliance updates

Notifications for expiring/expired PPE

PPE replacement and history tracking

Report generation for safety audits

OOP CONCEPT
Main Classes

1. User (Base Class)

Attributes: userId, name, email, phone

Methods: login(), logout()

2. Employee (extends User)

Attributes: department, assignedPPE

Methods: requestPPE(), viewPPEHistory()

3. PPE

Attributes: ppeId, ppeType, status, issueDate, expiryDate

Methods: assignToEmployee(), updateStatus(), markExpired()

4. Inspection

Attributes: inspectionId, ppeId, inspectorId, inspectionDate, condition

Methods: scheduleInspection(), updateCondition()

5. LifecycleRecord

Attributes: recordId, ppeId, employeeId, issueDate, returnDate, status

Methods: createRecord(), updateRecord(), generateReport()

6. Admin (extends User)

Methods: registerPPE(), managePPE(), generateReports()

OOP IMPLEMENTATION

Inheritance: Employees and Admins inherit common user features (like login, logout, and contact details) from the base User class.

Polymorphism: Different user roles (Employee, Admin, Safety Officer) can perform the same method (e.g., generateReports() or updateStatus()) in their own way depending on their role.

Association: PPE is associated with Employees (who use it) and Inspections (which ensure safety compliance). Lifecycle records link PPE and Employees together for usage tracking.

Encapsulation: Data like ppeId, inspectionDetails, and employeeAssignment are declared as private to ensure secure data handling.
