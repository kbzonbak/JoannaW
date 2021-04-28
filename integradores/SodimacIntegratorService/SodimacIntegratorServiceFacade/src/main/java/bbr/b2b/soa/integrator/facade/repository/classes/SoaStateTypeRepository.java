package bbr.b2b.soa.integrator.facade.repository.classes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.integrator.facade.entities.SoaStateType;

@Repository
public interface SoaStateTypeRepository extends JpaRepository<SoaStateType, Long> {

	public Optional<SoaStateType> findByCode(String code);	

}
