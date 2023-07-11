package guruspringframework.sdjpamultidb.services;

import org.springframework.stereotype.Service;

import guruspringframework.sdjpamultidb.domain.creditcard.CreditCard;
import guruspringframework.sdjpamultidb.repositories.cardholder.CreditCardHolderRepository;
import guruspringframework.sdjpamultidb.repositories.creditcard.CreditCardRepository;
import guruspringframework.sdjpamultidb.repositories.pan.CreditCardPANRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {
	
	private final CreditCardRepository creditCardRepository;
	
	private final CreditCardPANRepository creditCardPANRepository;
	
	private final CreditCardHolderRepository creditCardHolderRepository;
	
    @Override
    @Transactional
    public CreditCard getCreditCardById(Long id) {
    	
    	val result = creditCardRepository.findById(id)
    			.orElseThrow();
    	
    	val ccHolder = creditCardHolderRepository.findByCreditCardId(id).orElseThrow();
    	
    	val ccPan = creditCardPANRepository.findByCreditCardId(id).orElseThrow();
    	
    	result.setCreditCardHolder(ccHolder);
    	
    	result.setCreditCardPan(ccPan);
    	
        return result;
    }

	@Override
	@Transactional
	public CreditCard save(CreditCard cc) {
		val savedCC = creditCardRepository.save(cc);
		
		savedCC.getCreditCardHolder().setCreditCardId(cc.getId());
		
		savedCC.getCreditCardPan().setCreditCardId(cc.getId());
		
		creditCardPANRepository.save(cc.getCreditCardPan());
		
	    creditCardHolderRepository.save(cc.getCreditCardHolder());
		
		return savedCC;
	}
}
