package guruspringframework.sdjpamultidb.domain.pan;

import guruspringframework.sdjpamultidb.domain.CreditCardConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreditCardPan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CreditCardConverter.class)
    private String creditCardNumber;
    
    private Long creditCardId; //u credit card entitetu se radi inkrementiranje id-ja ostali entiteti samo
                               //perzistiraju taj id kako bi imakli referencu

}
