package bbr.b2b.soa.mercadolibre.repository.classes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bbr.b2b.soa.mercadolibre.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	public Optional<Company> findByRut(String rut);

	public Optional<Company> findByUserId(Long userId);

}
