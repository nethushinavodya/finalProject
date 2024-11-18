package lk.gdse.hotelmanagement.crystalwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AddGuestDTO {

    private String guestId;
    private String guestName;
    private String guestPhone;
    private String guestAddress;
    private String guestEmail;

}
