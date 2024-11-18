package lk.gdse.hotelmanagement.crystalwave.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ReservationTM {
    String roomId;
    String guestId;
    String CheckInDate;
    String CheckOutDate;
    String roomPrice;
}
