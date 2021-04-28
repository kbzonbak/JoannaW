package bbr.b2b.soa.mercadolibre.dto.classes;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class Person implements Serializable {

	private String id;

	private String nickname;

	private String email;

	private String first_name;

	private String last_name;

}
