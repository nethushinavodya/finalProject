package lk.gdse.hotelmanagement.crystalwave.dto;

public class GuestDTO {
    private String name;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String identityNo;
    private int days;
    private double amount;
    private String paymentStatus;

    public GuestDTO(String name, String phone, String email, String address, String city, String identityNo, int days, double amount, String paymentStatus) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.identityNo = identityNo;
        this.days = days;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getIdentityNo() { return identityNo; }
    public void setIdentityNo(String identityNo) { this.identityNo = identityNo; }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
