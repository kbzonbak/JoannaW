package bbr.b2b.soa.integrator.facade.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Order {

	private Long id;
	private int version;
	private Long ocNumber;
	private Long odNumber;	
	private LocalDateTime eocCreatedDate;
	private LocalDateTime eoeCreatedDate;
	private LocalDateTime eodCreatedDate;
	private LocalDate eocDownloadDate;
	private LocalDate eodDownloadDate;
	private String ocState;
	private String ocType;
	private String vendorCode;
	private String rut;
	private String dvRut;
	private String tradename;
	private String address;
	private String phone;
	private String buyer;
	private String paymentType;
	private String currency;
	private Double charge;
	private Double discount;
	private Double totalGross;
	private Double totalCharge;
	private Double totalDiscount;
	private Double totalNeto;
	private Double totalIVA;
	private LocalDate emittedDate;
	private LocalDate expectedDate;
	private LocalDate expirationDate;
	private LocalDate commitmentDeliveryDate;
	private String eoeLocalNumber;
	private String eoeLocalDescription;
	private String eodLocalCode;
	private String eodLocalDescription;
	private String eodLocalAddress;
	private String deliveryTo;
	private Boolean vev;
	private Boolean complete;
	private LocalDateTime currentSoaStateTypeDate;
	
	private Vendor vendor;
	private SoaStateType currentSoaStateType;

}