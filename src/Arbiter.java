public class Arbiter extends Person{
    ArbiterTitle title;

    public Arbiter(String fideId, ArbiterTitle title){
        super(fideId);
        this.title=title;
    }

    public Arbiter(String firstName, String lastName){
        super(firstName,lastName);
    }
    public Arbiter(String firstName, String lastName, ArbiterTitle arbiterTitle){
        super(firstName,lastName);
        title=arbiterTitle;
    }


    public ArbiterTitle getTitle() {
        return title;
    }
    public void setTitle(ArbiterTitle title) {
        this.title = title;
    }


    @Override
    public String toString(){
        String t=(title==null)? "": title+" ";
        return t+getLastName()+" "+getFirstName();
    }
}
