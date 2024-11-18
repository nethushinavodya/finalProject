package lk.gdse.hotelmanagement.crystalwave.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddServiceTM {
    private String  serviceId;
    private String serviceName;
    private String serviceDescription;
    private String servicePrice;
}
