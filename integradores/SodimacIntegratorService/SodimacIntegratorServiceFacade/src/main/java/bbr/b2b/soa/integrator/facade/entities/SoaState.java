package bbr.b2b.soa.integrator.facade.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SoaState {

	private Long id;
	private int version;
	private LocalDateTime when;
	private String comment;
	private String flowId;
	
	private Order order;
	private SellOut sellOut;
	private SoaStateType soaStateType;

}