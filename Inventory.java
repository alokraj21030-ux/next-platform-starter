import OutOfStocekException.OutOfException;

import java.util.*;
 class Inventory {
      private  ArrayList<Product>products=new ArrayList<>();

      public void addProduct(Product p){
         products.add(p);
      }
      public Product findById(String id){
          for (Product p:products){
              if (p.getProductId().equals(id))
                  return p;
          }

          return null;
      }
      public void searchByName(String name){
          for(Product p:products){
              if(p.getName().equalsIgnoreCase(name)){
                  System.out.println("Found:"+p.getName());
                  return;
         }

      }
          System.out.println("Product not found:");
     }
     public void sellProduct(String id, int qty )throws OutOfException {
          Product p=findById(id);
          if (p==null){
              System.out.println("Product not found");
              return;
          }
          if (p.getStockQty()<qty)throw new OutOfException("Insufficient stock for"+p.getName());
          p.setStockQty(p.getStockQty()-qty);
          System.out.println("Sold successfully");


     }
     public void restockProduct(String id, int qty){
          Product p=findById(id);
          if (p!=null){
              p.setStockQty(p.getStockQty()+qty);
              System.out.println("Restocked successfully");
          }
     }
     public void removeProduct(String id){
          products.removeIf(p->p.getProductId().equals(id));
         System.out.println("Product removed");
     }
     public void generateReport(){
         System.out.println("\n---Inventory Report---");

         System.out.printf("%-10s %-10s %-10s %-10s\n","Name","Price","Storck");
          for (Product p:products){
              System.out.printf("%-10s %-10s %-10.2f %-10d\n",p.getProductId(),p.getName(),p.getPrice(),p.getStockQty()) ;
              if (p.getStockQty()<p.getReorderLevel()){
                  System.out.println("Low stock alert for"+p.getName());
              }
          }
     }



}
