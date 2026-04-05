import OutOfStocekException.OutOfException;

import java.util.Scanner;
public class Main{
    static void main(String[] args) {
         Scanner sc=new Scanner(System.in);
         Inventory inv =new Inventory();
         while (true){
             System.out.println("\n1.Add 2.sell 3.Restock 4.Remove 5.Search 6.Report 7.Exit");
             int choice=sc.nextInt();
             try{
                 switch (choice){

                     case 1:
                         System.out.println("Enter ID, Name , Price, Stock, Reorder Level: ");
                         inv.addProduct(new Product(sc.next(),sc.next(),sc.nextDouble(),sc.nextInt(),sc.nextInt()));
                         break;

                     case 2:
                         System.out.println("Enter ID & Qty:");
                         inv.sellProduct(sc.next(),sc.nextInt());
                         break;

                     case 3:
                         System.out.println("Enter ID & Qty:");
                         inv.restockProduct(sc.next(),sc.nextInt());
                         break;

                     case 4:
                         System.out.println("Enter ID:");
                         inv.removeProduct(sc.next());
                         break;

                     case 5:
                         System.out.println("Enter Name:");
                         inv.searchByName(sc.next());
                         break;

                     case 6:
                        inv.generateReport();
                        break;

                     case 7:
                         System.exit(0);




                 }
             } catch (OutOfException e) {
                 throw new RuntimeException(e);
             }
         }
    }
}