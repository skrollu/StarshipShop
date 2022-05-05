package requestDto;

import com.example.starshipShop.jpa.Manufacturer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerRequestDTO {

	private String name;
}
