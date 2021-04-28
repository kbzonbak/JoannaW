package bbr.b2b.soa.integrator.facade.repository.classes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.integrator.facade.entities.SoaState;

@Repository
public interface SoaStateRepository extends JpaRepository<SoaState, Long> {

	public Optional<SoaState> findFirstByOrderIdAndSoaStateTypeIdOrderByWhenDesc(Long orderId, Long soaStateTypeId);
}
