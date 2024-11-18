package lk.gdse.hotelmanagement.crystalwave.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RoomTypeTm {

    private String roomTypeId;
    private String roomTypeName;
    private String roomTypeDescription;
    private String roomTypePrice;


}
