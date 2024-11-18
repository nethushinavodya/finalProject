package lk.gdse.hotelmanagement.crystalwave.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class InventoryDTO {

    private int itemId;
    private String itemName;
    private int itemQuantity;
    private double itemPrice;

}