package lk.gdse.hotelmanagement.crystalwave.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class DiscountTM {
    private String discountId;
    private String discountType;
    private int discountCondition;
    private String discountStartDate;
    private String discountEndDate;
}
