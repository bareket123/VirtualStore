import java.time.LocalDate;
import java.util.*;

public class VirtualStore {

    private LinkedList<Customer> usersList;
    private ArrayList <Product> productsList;

    public VirtualStore() {
        usersList = new LinkedList<>();
        productsList =new ArrayList<>();
    }


    public void createAnAccount() {
        Scanner scanner = new Scanner(System.in);
        Rank workerRank;
        boolean isClubMember = false;
        String password, username, clubMember;
        int userChoice, rank;
        do {
            System.out.println("which account you like to create?  1-Customer 2-Worker");
            userChoice = scanner.nextInt();
        } while (userChoice < 1 || userChoice > 2);

        System.out.println("please enter your first name");
        String firstName = returnValidName();
        System.out.println("please enter your last name");
        String lastName = returnValidName();
        username= returnValidUsername();
        password= returnValidPassword();
        System.out.println("are you a club member? enter only yes or no");
        clubMember = returnAnswerYesOrNo();
        if (clubMember.equals("yes")) {
            isClubMember = true;
        }
        switch (userChoice) {
            case Constants.CUSTOMER_ACCOUNT:
                Customer newCustomer = new Customer(firstName, lastName, username, password, isClubMember);
                usersList.add(newCustomer);
                System.out.println("customer was added");
                break;

            case Constants.WORKER_ACCOUNT:
                do {
                    System.out.println("Hello " + firstName + " What is your rank? 1-regular,2-manager 3-memberOfManagementTeam ");
                    rank = scanner.nextInt();

                } while (rank < 1 || rank > 3);
                if (rank == 1) {
                    workerRank = Rank.regular;
                } else if (rank == 2) {
                    workerRank = Rank.manager;
                } else {
                    workerRank = Rank.memberOfManagementTeam;
                }

                Worker newWorker = new Worker(firstName, lastName, username, password,isClubMember,workerRank);
                usersList.add(newWorker);
                System.out.println("worker was added!");

                break;
        }


    }
    private String returnAnswerYesOrNo(){
        Scanner scanner=new Scanner(System.in);
        String answer;
        do {
            answer = scanner.nextLine();
            if (!answer.equals("yes") && !answer.equals("no"))
                System.out.println("not valid, enter only yes or no");

        }while (!answer.equals("yes") && !answer.equals("no") );
       return answer;
    }


    private Boolean isNameValid(String name) {
        boolean containDigit = false;
        for (char character : name.toCharArray()) {
            if (Character.isDigit(character)) {
                containDigit = true;
                break;
            }
        }
        return containDigit;
    }

    private String returnValidName() {
        Scanner scanner = new Scanner(System.in);
        String name;
        do {
            name = scanner.nextLine();
            if (isNameValid(name)) {
                System.out.println("your name isn't valid, please enter again without numbers");
            }
        } while (isNameValid(name));

        return name;
    }

    private String returnValidPassword() {
        Scanner scanner=new Scanner(System.in);
        String password;
        do {
            System.out.println("please enter your password ( at lease 6 characters long!!)");
            password = scanner.nextLine();
            if (password.length() < Constants.VALID_LENGTH){
                System.out.println("not a strong password,try again");
            }

        } while (password.length() < Constants.VALID_LENGTH);

        return password;
    }

    private boolean isUsernameExist(String usernameToCheck) {
        boolean exist = false;
        for (Customer currentUser : this.usersList) {
            if (currentUser.getUserName().equals(usernameToCheck)) {
                exist = true;
                break;
            }
        }
        return exist;
    }
    private String returnValidUsername(){
        Scanner scanner=new Scanner(System.in);
        String username;
        do {
            System.out.println("please enter username");
            username = scanner.nextLine();
            if (isUsernameExist(username))
                System.out.println("already exist in our system, please choose a different name");
        } while (isUsernameExist(username));
     return username;
    }

    public void signIn() {
        Scanner scanner = new Scanner(System.in);
        int userChoice;
        String username,password;
        ShoppingCart shoppingCart= new ShoppingCart();
        do {
            System.out.println("Which account you like to sign in? 1-Customer 2-Worker");
            userChoice = scanner.nextInt();

        } while (userChoice < 1 || userChoice > 2);
        System.out.println("Please enter your username");
        scanner.nextLine();
        username = scanner.nextLine();
        System.out.println("Please enter your password");
        password = scanner.nextLine();


        switch (userChoice) {
                case Constants.CUSTOMER_ACCOUNT:
                   Customer customer= isUserExist(username,password);
                    if (customer!=null) {
                        System.out.print("Hello " + customer.getFirstName() +" " + customer.getLastName());
                        if (customer.isClubMember()){
                            System.out.print("(VIP)!");
                        }
                        makeAnOrder(shoppingCart,customer.isClubMember(),null);
                        //update customer data
                        customer.setQuantityOfPurchases(customer.getQuantityOfPurchases()+1);
                        customer.setAllPurchasesCost(customer.getAllPurchasesCost()+returnTotalCost(shoppingCart,customer.isClubMember(),null));
                        customer.setLastPurchaseDate(LocalDate.now());
                    } else {
                        System.out.println("unfortunately, we couldn't found you ");
                    }

                    break;

                case Constants.WORKER_ACCOUNT:
                    Worker worker=null;
                    try {
                         worker= (Worker) isUserExist(username,password);
                    }catch (ClassCastException e){
                        System.out.println("Error!! can't login in as a worker");
                    }

                    if (worker!=null){
                        System.out.println("Hello, "+ worker.getFirstName() +" "+worker.getLastName() +" (" + worker.getRank() +")!");
                        workerMenu(shoppingCart, worker);
                    }else {
                        System.out.println("no such worker");
                    }

                    break;

            }


        }
        private void makeAnOrder( ShoppingCart shoppingCart,Boolean isClubMember,Rank rank){
        int selectedProduct,amountOfItems;
            do{
                printListOfInStockProduct();
                selectedProduct= returnValidProductNumber();
                if (selectedProduct!=Constants.END_PURCHASE ) {
                    Product currentProduct = productsList.get(selectedProduct-1);
                    if (currentProduct.getAmount()>0){
                    System.out.println("how many items do you want? ");
                    amountOfItems = returnValidPositiveAmount();
                    if (amountOfItems <= currentProduct.getAmount()) { //check to see if there is enough items from the selected product
                        currentProduct.setAmount(currentProduct.getAmount()-amountOfItems);
                        shoppingCart.getListOfProducts().put(currentProduct, amountOfItems);
                        System.out.println(currentProduct.getDescription() + " added successfully ");
                        printShoppingCartCosts(shoppingCart, isClubMember, rank);
                    } else {
                        System.out.println("unfortunately,there isn't enough " + currentProduct.getDescription() + " in stock, we currently have only " + currentProduct.getAmount() + " units, please choose accordingly");

                    }
                    }else
                    System.out.println((currentProduct.getAmount() == 0) ? "not valid number, choose from the list" : "");
                }

            }while ((selectedProduct!= Constants.END_PURCHASE));
            System.out.println("Thank you for your order, your final price is "+ returnTotalCost(shoppingCart,isClubMember,rank)+" ,goodbye");

        }

    private Customer isUserExist(String username, String password){
        Customer found=null;
        for (Customer currentCustomer :this.usersList) {
            if (currentCustomer.getUserName().equals(username) && currentCustomer.getPassword().equals(password)){
                found= currentCustomer;
                break;
            }
        }
        return found;
    }
    private void printListOfInStockProduct(){
        System.out.println("\nHere are all the products that in stock: ");
        for (int i = 1; i <= productsList.size(); i++) {
            if (productsList.get(i-1).getAmount()>0)
            System.out.println(i +". " + productsList.get(i-1).getDescription());

        }

    }
    private boolean isValidProductNumber(int selectedProductNumber) {
        return selectedProductNumber < 1 || selectedProductNumber > productsList.size();
    }

    private int returnValidProductNumber(){
     Scanner scanner= new Scanner(System.in);
     int selectedProductItem;
        do {
            System.out.println("please choose the product you would like to order, in the end press -1 ");
            selectedProductItem= scanner.nextInt();
           if (isValidProductNumber(selectedProductItem) && selectedProductItem!=Constants.END_PURCHASE)
               System.out.println("the number you chose isn't on the list, please try again");

        }while (isValidProductNumber(selectedProductItem) && selectedProductItem!=Constants.END_PURCHASE );

        return selectedProductItem;
    }
    private int returnValidPositiveAmount(){
        Scanner scanner= new Scanner(System.in);
        int amount;
        do {
             amount=scanner.nextInt();
             if (amount<0)
                 System.out.println("you can't add this amount");
        }while (amount<0);
     return amount;
    }
    private void printShoppingCartCosts(ShoppingCart shoppingCart,Boolean isClubMember,Rank rank){
        String discountForClubMember;
        System.out.println("Here is all the products you chose to add your shopping-cart:");
        System.out.print("name: \t amount: \t price:");
        if (isClubMember){
            System.out.println("\t club member discount:(%)");
        }else
            System.out.println();//<--it's for the space between the columns (name,amount) and the products when the customer isn't a club member
        for (Product currentProduct:shoppingCart.getListOfProducts().keySet()) {
            discountForClubMember=isClubMember? Double.toString(currentProduct.getDiscountForClubMember()):"";
            System.out.println(currentProduct.getDescription() +"\t     " +shoppingCart.getListOfProducts().get(currentProduct) +"\t       " +currentProduct.getPrice() +"\t        " +discountForClubMember  );


        }
        System.out.println("total cost so far: " + returnTotalCost(shoppingCart,isClubMember,rank) + (rank!=null? "  (include the discount for workers according to the worker rank)":""));

        }
     private double returnTotalCost(ShoppingCart shoppingCart,Boolean isClubMember,Rank rank ){
        double totalCost=0,currentCost,clubMemberDiscount;
        Set<Product> productsInShoppingCart= shoppingCart.getListOfProducts().keySet();
             for (Product currentProduct:productsInShoppingCart) {
                  clubMemberDiscount=currentProduct.getDiscountForClubMember();
                  currentCost=currentProduct.getPrice()*shoppingCart.getListOfProducts().get(currentProduct);
                 if (isClubMember){
                     double costAfterDiscount=(100-clubMemberDiscount)/100 * currentCost;
                     totalCost+=costAfterDiscount;
                 }else {
                     totalCost+=currentCost;
                 }

             }
             if (rank!=null){
                 switch (rank){
                     case regular:
                         totalCost*=0.9;
                         break;
                     case manager:
                         totalCost*=0.8;
                         break;
                     case memberOfManagementTeam:
                         totalCost*=0.7;
                         break;
                 }

             }

        return totalCost;
     }
    private void workerMenu(ShoppingCart shoppingCart,Worker worker){
        int workerChoice;
        Scanner scanner=new Scanner(System.in);
        do {
            System.out.println("what you would like to do? ");
            System.out.println("1-Show list of all customers\n2-show list of all club member customers \n3-print list of customers who made at least one purchase\n" + "4-show the customer with the highest purchases" + "\n5-add new product\n" +
                               "6-change inventory status for product\n7-go shopping\n8-exit");
            workerChoice=scanner.nextInt();
            switch (workerChoice){

                case Constants.SHOW_ALL_CUSTOMERS_LIST:
                    System.out.println(this.usersList.toString());

                    break;

                    case Constants.SHOW_CLUB_MEMBER_CUSTOMERS_LIST:
                          Set<Customer> clubMemberCustomers=new HashSet<>();
                        for (Customer currentCustomer: usersList) {
                            if (currentCustomer.isClubMember()){
                                clubMemberCustomers.add(currentCustomer);
                            }
                        }
                        System.out.println(clubMemberCustomers);

                        break;

                        case Constants.SHOW_AT_LEAST_ONE_PURCHASE_CUSTOMERS_LIST:
                            Set<Customer>  customersWhoHaveMadePurchase=new HashSet<>();

                            for (Customer currentCustomer: usersList) {
                                if (currentCustomer.getQuantityOfPurchases()>=1){
                                    customersWhoHaveMadePurchase.add(currentCustomer);
                                }
                            }
                            System.out.println(customersWhoHaveMadePurchase);

                            break;

                            case Constants.HIGHEST_PURCHASES_CUSTOMER:
                                Customer highestPurchasesCustomer = null;
                                double highestPurchasesCost=0;
                                for (Customer currentCustomer: usersList) {
                                    if (highestPurchasesCost<currentCustomer.getAllPurchasesCost()){
                                        highestPurchasesCost=currentCustomer.getAllPurchasesCost();
                                        highestPurchasesCustomer=currentCustomer;
                                    }
                                }
                                assert highestPurchasesCustomer!=null;
                                System.out.println(highestPurchasesCustomer);

                                break;

                                case Constants.ADD_NEW_PRODUCT:
                                    System.out.println("please enter product description ");
                                    scanner.nextLine();
                                    String productDescription=scanner.nextLine();
                                    System.out.println("enter the price of the product per unit ");
                                    double productPrice=scanner.nextDouble();
                                    System.out.println("please enter Percentage discount for club member");
                                    double discountForClubMember=scanner.nextDouble();
                                    //the default for stock product is 1,the product is automatically placed in stock when amount > 0
                                    Product newProductToAdd=new Product(productDescription,productPrice,discountForClubMember);
                                    productsList.add(newProductToAdd);
                                    System.out.println("currently in stock there is " + newProductToAdd.getDescription() + " with only 1 unit, if you want to update stock press 6");

                                    break;

                                    case Constants.CHANGE_PRODUCT_STOCK_STATUS:
                                        String isInStock;
                                        int chosenProduct;
                                       do {
                                           System.out.println("choose a number of product you would like to updated his inventory");
                                           System.out.println("Here are all the products in the store: ");
                                           for (int i = 1; i <= productsList.size(); i++) {
                                               System.out.println(i +". "+ productsList.get(i-1).getDescription());
                                           }
                                           chosenProduct=scanner.nextInt();
                                           if (isValidProductNumber(chosenProduct))
                                               System.out.println("invalid number, please choose from the list");
                                       }while (isValidProductNumber(chosenProduct));
                                            System.out.println("is the product you choose in stock? enter only yes or no");
                                             isInStock=returnAnswerYesOrNo();
                                        if (isInStock.equals("yes")) {
                                            System.out.println("How many units are in stock? ");
                                            int productAmount = returnValidPositiveAmount();
                                            productsList.get(chosenProduct-1).setAmount(productAmount);
                                        }else {
                                            productsList.get(chosenProduct-1).setAmount(0);
                                            System.out.println("change amount of " + productsList.get(chosenProduct-1).getDescription()+" to 0 ");

                                        }
                                        System.out.println("updated successfully!");
                                        break;

                                        case Constants.PURCHASING:
                                            makeAnOrder(shoppingCart,worker.isClubMember(),worker.getRank());
                                            worker.setQuantityOfPurchases(worker.getQuantityOfPurchases()+1);
                                            worker.setAllPurchasesCost(worker.getAllPurchasesCost()+returnTotalCost(shoppingCart,worker.isClubMember(),worker.getRank()));
                                            worker.setLastPurchaseDate(LocalDate.now());
                                            break;
                                            case Constants.SIGN_OUT:
                                                break;
                                         default:
                                             System.out.println("not a valid option");
                                             break;


            }
        }while (workerChoice!= Constants.SIGN_OUT);



    }




}



