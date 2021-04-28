package bbr.b2b.soa.integrator.facade.repository.classes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.integrator.facade.entities.SellOutType;

@Repository
public interface SellOutTypeRepository extends JpaRepository<SellOutType, Long> {

	public Optional<SellOutType> findByCode(String code);	

}
