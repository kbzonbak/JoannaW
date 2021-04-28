package bbr.b2b.soa.integrator.facade.repository.classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.integrator.facade.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	public Optional<Order> findByOcNumber(Long ocNumber);
	public Optional<Order> findByOcNumberAndVendorId(Long ocNumber, Long vendorId);
	public List<Order> findByCurrentSoaStateTypeIdAndComplete(Long currentSoaStateTypeId, Boolean complete);
	public List<Order> findByCurrentSoaStateTypeIdAndVendorId(Long currentSoaStateTypeId, Long vendorId);
	
	@Query(name = "getPendingOrdersByVendor")
	public List<Order> getPendingOrdersByVendor(Long currentSoaStateTypeId, LocalDateTime currentSoaStateTypeDate, Long vendorId, LocalDate emittedDate);
	
	@Query(name = "getOrdersByVendorIdAndEmittedAndSoaStateTypeIds")	
	public List<Order> getOrdersByVendorIdAndEmittedAndSoaStateTypeIds(Long vendorId, LocalDate since, List<Long> soaStateTypeIds);
	
	@Query(name = "getOrdersByNumbersAndVendorIdAndEmitted")
	public List<Order> getOrdersByNumbersAndVendorIdAndEmitted(List<Long> ocNumbers, Long vendorId, LocalDate since);

}
