package bbr.b2b.soa.integrator.facade.repository.classes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.integrator.facade.entities.PreDeliveryDetail;

@Repository
public interface PreDeliveryDetailRepository extends JpaRepository<PreDeliveryDetail, Long> {

	public List<PreDeliveryDetail> findByOrderDetailId(Long orderDetailId);
	public List<PreDeliveryDetail> findByOrderDetailOrderId(Long orderId);
	

}
