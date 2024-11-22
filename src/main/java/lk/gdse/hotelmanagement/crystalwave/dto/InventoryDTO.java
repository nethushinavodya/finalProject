package lk.gdse.hotelmanagement.crystalwave.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class InventoryDTO {

    private String itemId;
    private String itemName;
    private String itemQuantity;
    private String itemPrice;

}