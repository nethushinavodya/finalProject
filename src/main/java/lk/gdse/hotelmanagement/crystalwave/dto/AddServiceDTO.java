package lk.gdse.hotelmanagement.crystalwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AddServiceDTO {
    private String serviceId;
    private String serviceName;
    private String serviceDescription;
    private String servicePrice;
}
