package lk.gdse.hotelmanagement.crystalwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ReservationDTO {
    String reservationId;
    String guestId;
    String roomId;
    String CheckInDate;
    String CheckOutDate;

    private ArrayList<RoomReserveDTO>roomReserves;
}
