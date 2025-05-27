package Entities;

public class Person {
    private int personId;
    private String fideId;
    private String firstName, lastName;

    public Person(){}

    public Person(int personId){
        this.personId=personId;
    }

    public Person(String fideId){
        this.fideId=fideId;
    }

    public Person(String fideId, String fN, String lN){
        this.fideId=fideId;
        firstName=fN;
        lastName=lN;
    }

    public Person(int personId,String fN, String lN, String fideId){
        this.personId=personId;
        firstName=fN;
        lastName=lN;
        this.fideId=fideId;
    }

    public Person(int id,String fN, String lN){
        personId=id;
        firstName=fN;
        lastName=lN;
    }

    public Person(String fN, String lN){
        firstName=fN;
        lastName=lN;
    }

    public String getFideId() {
        return fideId;
    }

    public void setFideId(String fideId) {
        this.fideId = fideId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }

    public int getPersonId() {
        return personId;
    }
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String toString(){
        return lastName+" "+firstName;
    }
}
