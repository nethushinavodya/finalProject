package lk.gdse.hotelmanagement.crystalwave.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceCartTM {
   private String rid;
   private String sid;
   private Double price;
   private Button cancelBtn;


}
