import java.util.Objects;

public class Player extends Person {
    private PlayerTitle title;
    //private String fideID;
    protected int rating;


    public Player(){
        super("","Bye");
    }

    public Player(String fideId, PlayerTitle title, int rating){
        super(fideId);
        this.title=title;
        this.rating=rating;
    }

    public Player(String firstName, String lastName, int rating){
        super(String.valueOf(IDGenerator.getFideId()),firstName,lastName);
        this.rating=rating;

    }
    public Player(String firstName, String lastName, int rating, PlayerTitle title, String fideID){
        super(fideID,firstName,lastName);
        this.rating=rating;
        this.title=title;
    }
    public Player(Player original) {
        super(original.getFideId(),original.getFirstName(), original.getLastName());
        this.rating = original.getRating();
        this.title = original.getTitle();
    }


    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public PlayerTitle getTitle() {
        return title;
    }
    public void setTitle(PlayerTitle title) {
        this.title = title;
    }


    @Override
    public String toString(){
        String t=(title==null)? "": title+" ";
        return t+getLastName()+" "+getFirstName()+" Rating: "+getRating();
    }


    @Override
    public int hashCode() {
        return getFideId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Player other = (Player) o;
        return Objects.equals(this.getFideId(), other.getFideId());
    }

//    @Override
//    public int compareTo(Player p) {
//
//        int result=p.rating-this.rating;
//        if(result!=0){
//            return result;
//        }
//        else{
//            return fideID.compareTo(p.fideID);
//        }
//    }

}
