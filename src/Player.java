import java.util.Objects;

public class Player extends Person implements Comparable<Player>{
    private PlayerTitle title;
    private String fideID;
    private int rating;


    public Player(){
        super("","Bye");
    }
    public Player(String firstName, String lastName, int rating){
        super(firstName,lastName);
        this.rating=rating;
        fideID=String.valueOf(IDGenerator.getFideId());
    }
    public Player(String firstName, String lastName, int rating, PlayerTitle title, String fideID){
        super(firstName,lastName);
        this.rating=rating;
        this.title=title;
        this.fideID=fideID;
    }
    public Player(Player original) {
        super(original.getFirstName(), original.getLastName());
        this.rating = original.getRating();
        this.title = original.getTitle();
        this.fideID=original.getFideID();
    }

    public String getFideID() {
        return fideID;
    }

    public void setFideID(String fideID) {
        this.fideID = fideID;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player other = (Player) o;
        return Objects.equals(this.fideID, other.fideID);
    }

    @Override
    public int compareTo(Player p) {

        int result=p.rating-this.rating;
        if(result!=0){
            return result;
        }
        else{
            return Integer.parseInt(this.fideID)-Integer.parseInt(p.fideID);
        }
    }

}
