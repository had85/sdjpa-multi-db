package guruspringframework.sdjpamultidb.domain.creditcard;

import guruspringframework.sdjpamultidb.domain.CreditCardConverter;
import guruspringframework.sdjpamultidb.domain.cardholder.CreditCardHolder;
import guruspringframework.sdjpamultidb.domain.pan.CreditCardPan;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CreditCardConverter.class)
    private String cvv;

    @Convert(converter = CreditCardConverter.class)
    private String expirationDate;
    
    @Transient //obavezujemo se da rucno rekreiramo ovaj objekat, hibernate ignorise transient properties
    private CreditCardHolder creditCardHolder;
    
    @Transient
    private CreditCardPan creditCardPan;

}










