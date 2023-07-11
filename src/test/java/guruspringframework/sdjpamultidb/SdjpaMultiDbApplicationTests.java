package guruspringframework.sdjpamultidb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guruspringframework.sdjpamultidb.domain.cardholder.CreditCardHolder;
import guruspringframework.sdjpamultidb.domain.creditcard.CreditCard;
import guruspringframework.sdjpamultidb.domain.pan.CreditCardPan;
import guruspringframework.sdjpamultidb.services.CreditCardService;
import jakarta.transaction.Transactional;
import lombok.val;

@SpringBootTest
@Transactional
class SdjpaMultiDbApplicationTests {
	
	@Autowired
	CreditCardService creditCardService;

    @Test
    void contextLoads() {
    }
    
    @Test
    void testSaveAndGetCreditCard() {
    	val ccHolder = CreditCardHolder.builder()
    					.firstName("John")
    					.lastName("Thompson")
    					.zipCode("12345")
    					.build();
    	
    	val ccPan = CreditCardPan.builder()
    					.creditCardNumber("123456")
    					.build();
    	
    	val cc = CreditCard.builder()
    			 .creditCardHolder(ccHolder)
    			 .creditCardPan(ccPan)
    			 .cvv("123")
    			 .expirationDate("12/26")
    			 .build();
    	
    	creditCardService.save(cc);
    	
    	val foundCC = creditCardService.getCreditCardById(cc.getId());
    	
    	assertThat(foundCC).isNotNull();
    	
    	assertThat(foundCC.getId()).isNotNull();
    	
    	assertThat(foundCC.getCreditCardHolder()).isNotNull();
    	
    	assertThat(foundCC.getCreditCardPan()).isNotNull();
    				
    }

}
