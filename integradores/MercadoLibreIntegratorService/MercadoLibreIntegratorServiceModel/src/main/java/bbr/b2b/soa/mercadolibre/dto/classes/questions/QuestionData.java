package bbr.b2b.soa.mercadolibre.dto.classes.questions;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class QuestionData implements Serializable {

	private Long id;

	private Long seller_id;

	private String text;

	private String status;

	private String item_id;

	private String date_created;

	private Boolean hold;

	private Boolean deleted_from_listing;

	private String answer;

	private From from;

}
