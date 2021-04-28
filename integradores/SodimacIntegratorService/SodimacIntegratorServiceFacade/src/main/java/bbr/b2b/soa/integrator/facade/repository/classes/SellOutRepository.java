package bbr.b2b.soa.integrator.facade.repository.classes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.integrator.facade.entities.SellOut;

@Repository
public interface SellOutRepository extends JpaRepository<SellOut, Long> {

	public List<SellOut> findBySoaStateTypeIdAndSellOutTypeId(Long soaStateTypeId, Long sellOutTypeId);
	public List<SellOut> findBySoaStateTypeIdAndSellOutTypeIdAndVendorId(Long soaStateTypeId, Long sellOutTypeId, Long vendorId);

}
