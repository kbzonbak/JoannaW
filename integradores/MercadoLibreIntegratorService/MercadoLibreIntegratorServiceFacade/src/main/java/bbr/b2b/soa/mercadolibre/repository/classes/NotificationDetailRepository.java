package bbr.b2b.soa.mercadolibre.repository.classes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.mercadolibre.entities.NotificationDetail;

@Repository
public interface NotificationDetailRepository extends JpaRepository<NotificationDetail, Long> {

	public List<NotificationDetail> findByNotificationId(Long notificationId);

	public List<NotificationDetail> findByInformed(boolean informed);

	@Query(name = "getNotificationDetailsByCompanyUserIdAndSent")
	public List<NotificationDetail> findByCompanyUserIdAndSent(Long userId, boolean sent);

	@Query(name = "getNotificationDetailsByCompanyUserIdAndTopicAndSent")
	public List<NotificationDetail> findByCompanyUserIdAndTopicAndSent(Long userId, String topic, boolean sent);

	@Query(name = "getNotificationDetailsByCompanyUserIdAndTopicAndResources")
	public List<NotificationDetail> findByCompanyUserIdAndTopicAndResources(Long userId, String topic, List<String> resources);

	@Query(name = "getNotificationDetailsByCompanyUserIdAndReceived")
	public List<NotificationDetail> findByCompanyUserIdAndReceived(Long userId, boolean received);

	@Query(name = "getNotificationDetailsByCompanyUserIdAndTopicAndReceived")
	public List<NotificationDetail> findByCompanyUserIdAndTopicAndReceived(Long userId, String topic, boolean received);

	@Query(name = "getNotificationDetailsByCompanyUserIdAndResource")
	public List<NotificationDetail> findByCompanyUserIdAndResource(Long userId, String resource);

}
