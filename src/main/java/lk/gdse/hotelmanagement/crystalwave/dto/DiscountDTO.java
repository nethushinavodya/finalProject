package lk.gdse.hotelmanagement.crystalwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class DiscountDTO {
        private String discountId;
        private String discountType;
        private int discountCondition;
        private String discountStartDate;
        private String discountEndDate;
}
