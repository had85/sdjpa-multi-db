package guruspringframework.sdjpamultidb.repositories.pan;

import guruspringframework.sdjpamultidb.domain.pan.CreditCardPan;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardPANRepository extends JpaRepository<CreditCardPan, Long> {
	
	Optional<CreditCardPan> findByCreditCardId(Long id);
}
