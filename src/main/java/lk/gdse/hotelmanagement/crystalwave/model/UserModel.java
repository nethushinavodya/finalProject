package lk.gdse.hotelmanagement.crystalwave.model;

public class UserModel {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;
    private String password; // hashed password
    private String role;

    // Constructor without id (for new users)
    public UserModel(String firstName, String lastName, String phoneNumber, String address, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Constructor with id (for existing users)
    public UserModel(int id, String firstName, String lastName, String phoneNumber, String address, String email, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}
