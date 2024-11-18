package lk.gdse.hotelmanagement.crystalwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AddRoomDTO {
    private String roomId;
    private String roomNumber;
    private String roomStatus;
    private String roomTypeId;


}
