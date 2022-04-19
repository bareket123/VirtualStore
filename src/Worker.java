public class Worker extends Customer {
   private Rank rank;

    public Worker(String firstName, String lastName, String userName, String password,Boolean isClubMember,Rank rank) {
        super(firstName, lastName, userName, password, isClubMember);
        this.rank=rank;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}
