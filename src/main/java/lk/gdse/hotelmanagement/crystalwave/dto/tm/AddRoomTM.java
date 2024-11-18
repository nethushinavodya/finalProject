package lk.gdse.hotelmanagement.crystalwave.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AddRoomTM {
    private String roomId;
    private String roomNumber;
    private String roomStatus;
    private String roomTypeId;
}
