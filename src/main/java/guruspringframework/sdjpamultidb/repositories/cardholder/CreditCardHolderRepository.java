package guruspringframework.sdjpamultidb.repositories.cardholder;

import guruspringframework.sdjpamultidb.domain.cardholder.CreditCardHolder;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CreditCardHolderRepository extends JpaRepository<CreditCardHolder, Long> {
	
	Optional<CreditCardHolder> findByCreditCardId (Long id);
}
