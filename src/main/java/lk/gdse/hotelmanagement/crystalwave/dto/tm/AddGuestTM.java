package lk.gdse.hotelmanagement.crystalwave.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AddGuestTM {

    private String guestId;
    private String guestName;
    private String guestPhone;
    private String guestAddress;
    private String guestEmail;

}
