package lk.gdse.hotelmanagement.crystalwave.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class InventoryTm {

    private String itemId;
    private String itemName;
    private String itemQuantity;
    private String itemPrice;
}
