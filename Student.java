public  class Student{
    private int rollNO;
    private String  name;
    private String  address;
    private String  email;
    private  int  phoneNO;
    private String course;
    private double marks;

    public Student(int rollNo, String name, String address, String email, int phoneNO, String course, double marks){
        this.rollNO= rollNo;
        this.name= name;
        this.address = address;
        this.phoneNO = phoneNO;
        this.course= course;
        this.marks= marks;
        
    }


    public int getrollNO(){
        return rollNO;
    }
    public String getname(){
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getemail() {
        return email;
    }

    public int getphonenumber() {
        return phoneNO;
    }

    public String getcourse(){
        return course;
    }
    public double getmarks(){
        return marks;
    }

    public void setrollNO(int rollNO){
        this.rollNO=rollNO;
    }
    public void setname(String name){
        this.name=name;
    }

    public void setaddress(String address){
        this.address=address;
    }
    public void setemail(String email){
        this.email=email;
    }

    public void setphonenumber(int phoneNO){
        this.phoneNO=phoneNO;

    }

    public void setcourse(String course){
        this.course=course;
    }
    public void setmarks(double marks){
        this.marks=marks;
    }

    public void displayStudent(){
        System.out.println("Roll No.: "+rollNO);
        System.out.println("Name: "+name);
        System.out.println("Address: "+address);
        System.out.println("Email: "+email);
        System.out.println("Phone No.: "+phoneNO);
        System.out.println("Course: "+course);
        System.out.println("Marks: "+marks);
    }


}
