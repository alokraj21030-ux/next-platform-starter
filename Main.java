import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        int choice;

        do {
            showMenu();

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addstudent();
                    break;

                case 2:
                    viewstudent();
                    break;

                case 3:
                    searchstudent();
                    break;

                case 4:
                    updatestudent();
                    break;

                case 5:
                    deletestudent();
                    break;

                case 6:
                    saveToCSV();
                    break;

                case 7:
                    System.out.println("Thank you for using my Project!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 7);

        sc.close();
    }


    // ================= MENU =================

    public static void showMenu() {

        System.out.println("\n======== Student Management System ========");

        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Save Students to CSV");
        System.out.println("7. Exit");

        System.out.println("===========================================");
    }


    // ================= ADD STUDENT =================

    public static void addstudent() {

        System.out.println("\n----- Add Student -----");

        System.out.print("Enter roll number: ");
        int rollNo = sc.nextInt();

        // Check duplicate roll number
        for (Student student : students) {

            if (student.getrollNO() == rollNo) {

                System.out.println("Roll number already exists!");
                return;
            }
        }

        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Phone Number: ");
        int phoneNo = sc.nextInt();

        sc.nextLine();

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();


        Student student = new Student(
                rollNo,
                name,
                address,
                email,
                phoneNo,
                course,
                marks
        );

        students.add(student);

        System.out.println("Student added successfully!");
    }


    // ================= VIEW STUDENTS =================

    public static void viewstudent() {

        System.out.println("\n----- All Students -----");

        if (students.isEmpty()) {

            System.out.println("Record not found!");
            return;
        }

        for (Student student : students) {

            student.displayStudent();

            System.out.println("-------------------------");
        }
    }


    // ================= SEARCH STUDENT =================

    public static void searchstudent() {

        System.out.println("\n----- Search Student -----");

        System.out.print("Enter roll number: ");
        int rollNo = sc.nextInt();

        for (Student student : students) {

            if (student.getrollNO() == rollNo) {

                System.out.println("\nStudent found successfully!");

                student.displayStudent();

                return;
            }
        }

        System.out.println("Student not found!");
    }


    // ================= UPDATE STUDENT =================

    public static void updatestudent() {

        System.out.println("\n----- Update Student -----");

        System.out.print("Enter roll number: ");
        int rollNo = sc.nextInt();

        for (Student student : students) {

            if (student.getrollNO() == rollNo) {

                sc.nextLine();

                System.out.print("Enter new name: ");
                String name = sc.nextLine();

                System.out.print("Enter new address: ");
                String address = sc.nextLine();

                System.out.print("Enter new Email: ");
                String email = sc.nextLine();

                System.out.print("Enter new Phone Number: ");
                int phoneNo = sc.nextInt();

                sc.nextLine();

                System.out.print("Enter new Course: ");
                String course = sc.nextLine();

                System.out.print("Enter new Marks: ");
                double marks = sc.nextDouble();


                student.setname(name);
                student.setaddress(address);
                student.setemail(email);
                student.setphonenumber(phoneNo);
                student.setcourse(course);
                student.setmarks(marks);

                System.out.println("Student updated successfully!");

                return;
            }
        }

        System.out.println("Student not found!");
    }


    // ================= DELETE STUDENT =================

    public static void deletestudent() {

        System.out.println("\n----- Delete Student -----");

        System.out.print("Enter roll number: ");
        int rollNo = sc.nextInt();

        for (Student student : students) {

            if (student.getrollNO() == rollNo) {

                students.remove(student);

                System.out.println("Student deleted successfully!");

                return;
            }
        }

        System.out.println("Student not found!");
    }


    // ================= SAVE TO CSV =================

    public static void saveToCSV() {

        if (students.isEmpty()) {

            System.out.println("No student data available!");
            return;
        }

        try {

            FileWriter writer = new FileWriter("students.csv");

            // CSV HEADER
            writer.append(
                    "Roll No,Name,Address,Email,Phone No,Course,Marks\n"
            );


            // STUDENT DATA
            for (Student student : students) {

                writer.append(
                        String.valueOf(student.getrollNO())
                );

                writer.append(",");

                writer.append(student.getname());

                writer.append(",");

                writer.append(student.getAddress());

                writer.append(",");

                writer.append(student.getemail());

                writer.append(",");

                writer.append(
                        String.valueOf(student.getphonenumber())
                );

                writer.append(",");

                writer.append(student.getcourse());

                writer.append(",");

                writer.append(
                        String.valueOf(student.getmarks())
                );

                writer.append("\n");
            }


            writer.close();

            System.out.println("\n=================================");
            System.out.println("CSV file created successfully!");
            System.out.println("File name: students.csv");
            System.out.println("=================================");

        } catch (IOException e) {

            System.out.println("Error while creating CSV file!");
            System.out.println(e.getMessage());
        }
    }
}
