package nl.fontys.ais.datarecords;


import java.time.LocalDate;

public record CustomerData (int ID, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String address, String email, String IBAN){

    public CustomerData(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, String address, String email, String IBAN){
        this(1,firstName,lastName,dateOfBirth,phoneNumber,address, email,IBAN);
        System.out.println("Customer constructor with the default ID 1, called");
    }

    @Override
    public int ID() {
        return ID;
    }


    public String getFirstName() {
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }

    public LocalDate getDOB() {
        return dateOfBirth;
    }

    public String getEmail(){
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getIBAN(){
        return IBAN;
    }
    @Override
    public String toString() {
        return "CustomerData{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", IBAN='" + IBAN + '\'' +
                '}';
    }
}