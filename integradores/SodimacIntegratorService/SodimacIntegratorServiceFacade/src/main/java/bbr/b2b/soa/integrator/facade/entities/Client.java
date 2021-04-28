package bbr.b2b.soa.integrator.facade.entities;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Client {

	private Long id;
	private int version;
	
	private Long reserveNumber;
	private String buyerName;
	private String buyerDni;
	private String buyerPhone;
	private String receiverName;
	private String receiverAddress;
	private String receiverCommune;
	private String receiverCity;
	private String receiverCp;
	private String receiverPhone;
	private String attention;
	private String observation;
	private LocalDate schedule;
	private String houseOffice;
	private String email;
	private String street;
	private LocalDate clientDeliveryDate;
	private String region;
	private String clientIdentification;
	
	private Order order;

}