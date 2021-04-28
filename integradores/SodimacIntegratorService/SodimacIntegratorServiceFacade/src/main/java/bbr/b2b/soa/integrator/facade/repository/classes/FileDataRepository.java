package bbr.b2b.soa.integrator.facade.repository.classes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.integrator.facade.entities.FileData;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {

	public Optional<FileData> findByVendorCode(String code);
}
