package lk.gdse.hotelmanagement.crystalwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RoomReserveDTO {
    String reservationId;
    String roomId;
    String roomPrice;

}
