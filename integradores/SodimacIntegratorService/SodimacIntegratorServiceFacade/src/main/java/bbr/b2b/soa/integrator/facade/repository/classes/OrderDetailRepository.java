package bbr.b2b.soa.integrator.facade.repository.classes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.integrator.facade.entities.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	public List<OrderDetail> findByOrderId(Long orderId); 

}
