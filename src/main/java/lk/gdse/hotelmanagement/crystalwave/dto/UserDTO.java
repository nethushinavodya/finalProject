package lk.gdse.hotelmanagement.crystalwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;
    private String password;
    private String role;
}