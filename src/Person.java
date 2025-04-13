public class Person {
    private String firstName, lastName;


    public Person(String fN, String lN){
        firstName=fN;
        lastName=lN;
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

    public String toString(){
        return lastName+" "+firstName;
    }
}
