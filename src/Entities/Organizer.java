package Entities;

public class Organizer extends Person {
    private String phoneNumber;
    private String email;

    public Organizer(int organizerId, String phoneNumber, String email){
        super(organizerId);
        this.phoneNumber=phoneNumber;
        this.email=email;

    }

    public Organizer(int id, String fN, String lN, String phoneNumber, String email) {
        super(id, fN, lN);
        this.phoneNumber = phoneNumber;
        this.email = email;

    }


    public Organizer(String firstName, String lastName){
        super(firstName,lastName);
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }


    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }

    public String toString(){
        return super.toString();
    }
}
