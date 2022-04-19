import java.time.LocalDate;
public class Customer{
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean isClubMember;
    private int quantityOfPurchases;
    private double allPurchasesCost;
    private LocalDate lastPurchaseDate;

    public Customer(String firstName, String lastName, String userName, String password, boolean isClubMember) {
       this.firstName=firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.isClubMember = isClubMember;
        this.quantityOfPurchases =0;
        this.allPurchasesCost = 0;
        this.lastPurchaseDate = null;
    }

    public String toString() {
        String clubMember=isClubMember?"yes" : "no";
        String date=getLastPurchaseDate()!=null ? getLastPurchaseDate().toString():"no orders yet";

       return "\nname: "+getFirstName() +" "+ getLastName() +" ,club member: "+clubMember + " ,quantity purchase: "+getQuantityOfPurchases()+ " ,total cost of all purchase: " + getAllPurchasesCost()+" ,date of last purchase: "+ date  + "\n";
    }

    //didn't make set's for names and password so no one can change it to invalid values (name with digit or less than 6 length password).
    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isClubMember() {
        return isClubMember;
    }

    public int getQuantityOfPurchases() {
        return quantityOfPurchases;
    }

    public void setQuantityOfPurchases(int quantityOfPurchases) {
        this.quantityOfPurchases = quantityOfPurchases;
    }

    public double getAllPurchasesCost() {
        return allPurchasesCost;
    }

    public void setAllPurchasesCost(double allPurchasesCost) {
        this.allPurchasesCost = allPurchasesCost;
    }

    public LocalDate getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(LocalDate lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }


}
