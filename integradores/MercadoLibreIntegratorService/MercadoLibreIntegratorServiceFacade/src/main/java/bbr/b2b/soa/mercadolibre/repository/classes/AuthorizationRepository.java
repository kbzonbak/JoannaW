package bbr.b2b.soa.mercadolibre.repository.classes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.mercadolibre.entities.Authorization;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

	public Optional<Authorization> findByCompanyId(Long companyId);

	public Optional<Authorization> findByCompanyRut(String companyRut);

	public Optional<Authorization> findByCompanyUserId(Long userId);

	public List<Authorization> findByValidAndExpirationLessThan(boolean valid, LocalDateTime expiration);

}
