import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userChoice=0;
        VirtualStore virtualStore = new VirtualStore();

        try{

        while (userChoice!= Constants.EXIT) {
            System.out.println("Hello and welcome please choose one of the following actions: ");
            System.out.println("1. create new account\n2. sign in\n3. exit ");
             userChoice = scanner.nextInt();
            switch (userChoice) {
                case Constants.OPEN_AN_ACCOUNT:
                    virtualStore.createAnAccount();
                    break;
                case Constants.SIGN_IN:
                    virtualStore.signIn();
                    break;
                case Constants.EXIT:
                    System.out.println("goodbye!");
                    break;
                default:
                    System.out.println("not a valid option");
                    break;

            }
        }

        }catch (Exception e){
           e.printStackTrace();

        }


    }
}











