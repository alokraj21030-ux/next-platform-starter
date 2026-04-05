 class Product {
     private final int stockQty;
     private final int reorderLevel;
     private String productId,name;
    private  double  price;
     private int stockQt;

     public Product(String productId,String name, double price,int stockQty, int reorderLevel){
        this.productId=productId;
        this.name=name;
        this.price=price;
        this.stockQty=stockQty;
        this.reorderLevel=reorderLevel;
        
        
        
        
        
    
    }
    public String getProductId(){return productId;}
        
    
     public String getName(){return name;}

     public double getPrice() {
         return price;
     }

     public int getStockQty() {
         return stockQty;
     }

     public int getReorderLevel() {
         return reorderLevel;
     }
     public void setStockQty(int stockQty){
        
        this.stockQt= stockQty;
     }
 }
