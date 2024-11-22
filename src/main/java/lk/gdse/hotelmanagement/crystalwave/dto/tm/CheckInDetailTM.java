package lk.gdse.hotelmanagement.crystalwave.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CheckInDetailTM {
    String reservationId;
    String guestId;
    String roomId;
    String checkInDate;
    String checkOutDate;
    String amount;
}
