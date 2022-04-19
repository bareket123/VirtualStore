public class Product {
    private String description;
    private double price;
    private int amount;
    private double discountForClubMember;



    public Product(String description, double price,double discountForClubMember) {
        this.description = description;
        this.amount=1;//default amount when product created
        this.price = price;
        this.discountForClubMember=discountForClubMember;
    }

    @Override
    public String toString() {
        return " name: " +this.description + " ,price:" +this.price +" " ;
    }

    public double getDiscountForClubMember() {
        return discountForClubMember;
    }

    public void setDiscountForClubMember(double discountForClubMember) {
        this.discountForClubMember = discountForClubMember;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
