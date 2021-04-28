package bbr.b2b.soa.integrator.facade.repository.classes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.integrator.facade.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	public List<Client> findByOrderId(Long orderId);
	

}
