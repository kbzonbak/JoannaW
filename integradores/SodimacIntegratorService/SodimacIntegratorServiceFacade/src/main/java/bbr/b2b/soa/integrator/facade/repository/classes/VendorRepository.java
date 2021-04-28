package bbr.b2b.soa.integrator.facade.repository.classes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.integrator.facade.entities.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

	public Optional<Vendor> findByCode(String code);	
	public List<Vendor> findByActive(Boolean active);
	
}
