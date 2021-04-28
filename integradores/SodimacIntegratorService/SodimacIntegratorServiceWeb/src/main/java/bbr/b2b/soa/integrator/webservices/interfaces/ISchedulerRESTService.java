package bbr.b2b.soa.integrator.webservices.interfaces;

import org.springframework.http.ResponseEntity;

public interface ISchedulerRESTService {

	public ResponseEntity<String> doDownloadEocSales();
	public ResponseEntity<String> doDownloadEodSales();
	public ResponseEntity<String> doDownloadEoeSales();
	public ResponseEntity<String> doDownloadEvtaSales();

}
