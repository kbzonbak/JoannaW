package bbr.b2b.soa.mercadolibre.repository.classes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.mercadolibre.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	public List<Notification> findByProcessedOrderById(boolean processed);

	public List<Notification> findByResourceAndTopicAndUserId(String resource, String topic, Long userid);

}
