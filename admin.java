import java.util.*;
import java.lang.*;

abstract class Car{
    private  int car_id;
    private  String car_name;
    private  String car_model;
    private  double price;
    private double resale_value;
    Car(int car_id,String car_name,String car_model,double price){
        this.car_id=car_id;
        this.car_name=car_name;
        this.car_model=car_model;
        this.price=price;
    }
    public void setResaleValue(double resale_value){
        this.resale_value=resale_value;
    }
    public  int getCarId(){
        return car_id;
    }
    public String getCarName(){
        return car_name;
    }
    public  String getCarModel(){
        return car_model;
    }
    public  double getPrice(){
        return price;
    }
    public  double getResaleValue(){
        resale_value=resale();
        return resale_value;
    }
    abstract double resale();   //this abstract function calculates the resale value of individual car
}

class Hyundai extends Car{
    private  int car_id;
    private  String car_name;
    private  String car_model;
    private  double price;
    private double resale_value;
    Hyundai(int car_id,String car_name,String car_model,double price){      //constructor class
        super(car_id,car_name,car_model,price);
        resale_value=resale();
        setResaleValue(resale_value);
    }
    //for calculating the resale price of the car
     double resale(){
        resale_value=price*0.80;
        return resale_value;
    }
}

class Toyota extends Car{
    private int car_id;
    private static String car_name;
    private String car_model;
    private double price;
    private double resale_value;
    Toyota(int car_id,String car_name,String car_model,double price){       //constructor class
        super(car_id,car_name,car_model,price);
        resale_value=resale();
        setResaleValue(resale_value);
    }
    //for calculating the resale price of the car
    double resale(){
        resale_value=price*0.60;
        return resale_value;
    }
}

class Maruti extends Car{
    private int car_id;
    private static String car_name;
    private String car_model;
    private double price;
    private  double resale_value;
    Maruti(int car_id,String car_name,String car_model,double price){       //constructor class
        super(car_id,car_name,car_model,price);
        resale_value=resale();
        setResaleValue(resale_value);
    }
    //for calculating the resale price of the car
    double resale(){
        resale_value=price*0.40;
        return resale_value;
    }
}

class Customer{
     int customer_id;
    String customer_name;
     ArrayList<Car> customerList = new ArrayList();  //for maintaing the cars that the customer has
     final HashMap<Integer,Customer> map = new HashMap<>();     //for maintaing the customer id as key along with the properties
                                                                // of customer(passed as object) and his cars as value 
    Customer(int id,String name, Car car){
        customer_id=id;
        customer_name=name;
        customerList.add(car);
    }
    public int getCustomer_id(){        //for returning id of the customer
        return customer_id;
    }
    public String getCustomer_name(){       //for returning name of the customer
        return customer_name;   
    }
    public ArrayList getCarList(){      //for returning the list of cars an individual costumer has
        return customerList;
    }
    
    public void addCar(Car car){     //for adding a car to the list of cars 
        customerList.add(car);
    }
}

public class admin{    //this is the main class from where is program is initiated
    static Scanner s = new Scanner(System.in);
    static final HashMap<Integer,Customer> map = new HashMap<>();
    public static int maxId=0;      //for checking the maximum id of the customer
    static String car_name;
    
//validating and returning the price of an individual car
//validation:- if user enters a string rather than int or double, error will be generated
    private static double getCarPrice(){
        double price=0;
        try{
            String input=s.next();
            price=Double.parseDouble(input);
        }catch(Exception e){
            System.out.println("Wrong input entered");
            System.out.println("Enter the price");
            price=getCarPrice();
        }
        return price;
    }
    
//validating and returning the id of the car
//validation:- if admin enters a string, it will be treated as an error
    private static int getCarId(){
        int id = 0;
        try{
            String car_id = s.next();
            id = Integer.parseInt(car_id);
        }catch(Exception e){
            System.out.println("Wrong input entered");
            System.out.println("Enter the id of the car");
            id=getCarId();
        }
        return id;
    }
    
//validating and returning the brand id 
//Validation:- if admin enters a string or a number greater than 3, then it will be treated as en error
    private static int getBrandId(){
        int id = 0;
        try{
            String car_id = s.next();
            id = Integer.parseInt(car_id);
            if(id>3){
                System.out.println("Wrong input entered");
                System.out.println("Enter the brand");
                id=getBrandId();
            }
        }catch(Exception e){
            System.out.println("Wrong input entered");
            System.out.println("Enter the brand in numerics");
            id=getBrandId();
        }
        return id;
    }
    
//this function is getting the details of car of each customer and returning the object containing the details of car
    private static Car EnterCarDetails(){
        Car car;
        String model ="";
        double price = 0;
        int flag=0;     //for checking if the input is valid or not
        System.out.println("Enter the id of the car");
        int car_id = getCarId();
        System.out.println("Enter the brand of the car in numerics");
        System.out.println("1.Hyundai");
        System.out.println("2.Toyota");
        System.out.println("3.Maruti");
        s.nextLine();
        int brandChoice = getBrandId();
        if(brandChoice==1 || brandChoice==2 || brandChoice==3){
            s.nextLine();
            System.out.println("Enter the model of the car");
            model=s.nextLine();
            System.out.println("Enter the price");
            price=getCarPrice();
            String result="";
            switch(brandChoice){
                case 1:{
                    car_name="Hyundai";
                    car = new Hyundai(car_id,car_name,model,price);
                    break;
                }
                case 2:{
                    car_name="Toyota";
                    car = new Toyota(car_id,car_name,model,price);
                    break;
                }
                case 3:{
                    car_name="Maruti";
                    car = new Maruti(car_id,car_name,model,price);
                    break;
                }
                default:{
                    car_name="Maruti";
                    car = new Maruti(car_id,car_name,model,price);
                }
            }
        }
        else{
            System.out.println("Enter correct brand choice");
            car = new Maruti(car_id,car_name,model,price);
        }
        return car;
    }
    
//validating and returning the id
//Validation:- if admin enters a string as input , then it will be considered as wrong input
    private static int getInputId(){
        int num=0;
        try{
            String id_name = s.next();
            num = Integer.parseInt(id_name);
        }catch(Exception e){
            System.out.println("Wrong id entered");
            System.out.println("Enter the id of customer");
            num=getInputId();
        }
        return num;
    }
//validating and returning the name of the customer
//Validation:- the name consist of STRING only. If number is entered at any position, it will be considered as wrong input
    private static String getCustomerName(){
        int flag=1;
       String customer_name="";
        customer_name=s.next();
        for(int i=0;i<customer_name.length();i++){
           if((customer_name.charAt(i)>='a' && customer_name.charAt(i)<='z') || (customer_name.charAt(i)>='A' && customer_name.charAt(i)<='z'))
              continue;
           else{
               flag=0;
               break;
           }
        }
        if(flag==0){
            System.out.println("Wrong input");
            System.out.println("Enter the name of the customer");
            customer_name=getCustomerName();
        }
       return customer_name;
    }

//for adding a new customer to the program, this function is used
    private static void addNewCustomer(){
        System.out.println("Enter the id of customer");
        int customer_id = getInputId();
        if(map.containsKey(customer_id)){
            System.out.println("This id already exists");
            addNewCustomer();
        }
        else{
            System.out.println("Enter the name of the customer");
            s.nextLine();   
            String customer_name=getCustomerName();
            Car car = EnterCarDetails(); //the object of the car is returned by the function mentioned
            if(!(car.getCarName().equals(""))){    //if invalid car name is entered then no further process will continue
                Customer customer = new Customer(customer_id,customer_name,car);
                if(maxId<customer_id)
                    maxId=customer_id;
                map.put(customer_id,customer);  //adding customer id as key and object customer as value which contains each and 
            }                                   //every detail of the customer containing the list of the cars that customer has
        }
    }
    
//if a customer exists in the program, then to add a new car to that customer ,this function is used
    private static void addCarToExistingCustomer(){
        Car car;
        if(map.isEmpty())
            System.out.println("No data found");
        else{
            System.out.println("Enter the id");
            int customer_id=getInputId();
            if(map.containsKey(customer_id)){   //check if the id the user entered exists in the map or not
                car = EnterCarDetails();
                Customer customer = map.get(customer_id);
                customer.addCar(car);
            }
            else{
                System.out.println("Invalid customer id");
                addCarToExistingCustomer();
            }
        }
    }

//this function sorts the names of the customers and print them along with the list of cars they are having
    private static void sortByName(){
        TreeMap<String,ArrayList<String>> sortedNames = new TreeMap();
        if(map.isEmpty())
            System.out.println("No record found");
        else{
            for(int i=0;i<map.size();i++){
                Customer customer = map.get(i+1);
                sortedNames.put(customer.getCustomer_name(),customer.getCarList());
            }
            for (Map.Entry<String, ArrayList<String>> entry : sortedNames.entrySet()) {
                    System.out.println("NAME(Customer) : " + entry.getKey());
                    System.out.println("CARS :- " + entry.getValue());
            }
        }
    }

//this function outputs the name and the list of cars the customer is having
    private static void viewDetailsOfId(){
        int customer_id;
        ArrayList<Car> carList = new ArrayList();
        if(map.size()!=0){
            System.out.println("Enter the id whose data you want");
            customer_id=getInputId();
            if(map.containsKey(customer_id)){
                Customer c = map.get(customer_id);
                carList = c.getCarList();
                System.out.print("NAME :- ");
                System.out.println(c.getCustomer_name());
                System.out.println("CARS :- ");
                carList=c.getCarList();
                for(int j=0;j<carList.size();j++){
                    Car car=carList.get(j);
                    System.out.print(car.getCarName());
                    if(j!=carList.size()-1)
                        System.out.print(" , ");
                }
            }
            else
                System.out.println("Please enter a valid customer id");
        }
        else
            System.out.println("No customer exists");
    }
    
 //this function gives the prizes to the customers whose id's(entered by the admin) match the randomly generated id's
     private static void prizes(){
         ArrayList<Integer> idlist = new ArrayList();   //for storing all the customer id's stored in map as key
         HashSet<Integer> winnerList = new HashSet();       //for storing the id's of the winners to the HashSet
                                                        //HashSet is used so that no duplicate winner exists
         ArrayList<Integer> inputList = new ArrayList();    //for storing the id's that the admin will input
         ArrayList<Integer> autoList = new ArrayList();     //for storing the id's that will be randomly generated
         if(map.size()<6)       //if the total number of id's is less than 6, then perform this function
             System.out.println("Please enter atleast 6 customer id's");
         else{
            for(int i=1;i<=maxId;i++){    //if i starts from 0, then nullPointerException occurs
                Customer customer = map.get(i);
                idlist.add(customer.customer_id);      //adding the id's of the customer to the array list
            }
            for(int i=0;i<6;i++){
                int random = (int) (Math.random()*6+1);     //generating id's randomly
                autoList.add(random);   //adding randomly generated id's to the arraylist
            }
            System.out.println("Enter first id");
            int admin1 = s.nextInt();
            System.out.println("Enter second id");
            int admin2 = s.nextInt();
             System.out.println("Enter third id");
            int admin3 = s.nextInt();
            inputList.add(admin1);
            inputList.add(admin2);
            inputList.add(admin3);
            for(int i=0;i<inputList.size();i++){    //if the id's match, customer is winner
                for(int j=0;j<autoList.size();j++){
                    if(inputList.get(i) == autoList.get(j))
                        winnerList.add(inputList.get(i));
                }
             }
            if(winnerList.isEmpty())
                System.out.println("There is no winner");
            else{
                System.out.println("The list of the winner is :");
                    Iterator it = winnerList.iterator();
                    while(it.hasNext()){
                        System.out.println(it.next());
                  }
            }
        }
     }
     
     
//this function is displaying each and every detail that exist in the program enterd by the admin
    private static void viewEverything(){
        ArrayList<Car> carList = new ArrayList();
        ArrayList<Integer> customerIdList = new ArrayList();
        if(map.size()==0)
            System.out.println("No data exists");
        else{
            for(int i=1;i<=maxId;i++){
                if(map.get(i)!=null){
                    Customer customer=map.get(i);      //here customer is the object of class customer
                    System.out.print("ID(Customer) : ");
                    System.out.println(customer.getCustomer_id());      //this is accessing the id of the customer stored in customer object
                    System.out.print("NAME(Customer) : ");
                    System.out.println(customer.getCustomer_name());    //this is accessing the name of the customer stored in customer object
                    System.out.println("CARS :-");
                    carList=customer.getCarList();     //getCarList is the getter function declared in class customer
                    for(int j=0;j<carList.size();j++){
                        Car car=carList.get(j);
                        System.out.println("Brand : " + car.getCarName());
                        System.out.println("ID(Car) : " + car.getCarId());
                        System.out.println("Model : " + car.getCarModel());
                        System.out.println("Price : " + car.getPrice());
//                        System.out.println("Resale Value: " + car.getResaleValue());
                    }
                }
            }
        }
    }
    
    
//this function is displaying the id,name and the list of cars the customer has
    private static void displayDetails(){
        ArrayList<Car> carList = new ArrayList();
        ArrayList<Integer> customerIdList = new ArrayList();
        for(int i=1;i<=maxId;i++){
            if(map.get(i)!=null){
                Customer customer=map.get(i);      //here customer is the object of class customer
                System.out.println();
                System.out.print("ID(Customer) : ");
                System.out.println(customer.getCustomer_id());      //this is accessing the id of the customer stored in customer object
                System.out.print("NAME(Customer) : ");
                System.out.println(customer.getCustomer_name());    //this is accessing the name of the customer stored in customer object
                System.out.print("Cars :- ");
                carList=customer.getCarList();     //getCarList is the getter function declared in class customer
                for(int j=0;j<carList.size();j++){
                    Car car=carList.get(j);
                    System.out.print(car.getCarName());
                    if(j!=carList.size()-1)
                        System.out.print(" , ");
                }
            }
        }
    }
    
    
    public static void main(String args[]){
        boolean flag=true;              //for initiating the menu driven program
        while (flag){
            System.out.println();
            System.out.println("----------------------------------------------------------------");
            System.out.println("1.Add new customer");
            System.out.println("2.Add new car to an existing customer");
            System.out.println("3.Sort by Name");
            System.out.println("4.View details of Id");
            System.out.println("5.Generate prizes");
            System.out.println("6.View Every detail");
            System.out.println("0.EXIT");
            System.out.println("Enter your choice");
            String choice="";
            choice= s.next();
                switch(choice){
                    case "1":{
                        addNewCustomer();   //this function is used for adding a new customer and moreover adding its details
                        displayDetails();       //used for displaying the details of the customer
                        break;
                    }
                    case "2":{
                        addCarToExistingCustomer();     //this function is used to add a new car to an existing customer
                        displayDetails();
                        break;
                    }
                    case "3":{
                        sortByName();   //used for sorting the customers based on names
                        break;
                    }
                    case "4":{
                        viewDetailsOfId();       //used to show the details of the customer whose id the admin will enter
                        break;
                    }
                    case "5":{
                        prizes();       //give the prizes to the customers 
                        break;
                    }
                    case "6":{
                        viewEverything();
                        break;
                    }
                    case "0":{
                        flag=false;     //case exists if admin wants to exit the program
                        break;
                    }
                    default:{
                        System.out.println("Please enter a valid choice");  //if the choice entered is wrong
                        break;
                    }
                }
        }
    }
}