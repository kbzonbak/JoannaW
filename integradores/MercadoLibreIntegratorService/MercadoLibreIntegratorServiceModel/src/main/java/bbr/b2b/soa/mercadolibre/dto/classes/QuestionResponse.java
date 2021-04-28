package bbr.b2b.soa.mercadolibre.dto.classes;

import java.io.Serializable;

import bbr.b2b.soa.mercadolibre.dto.classes.questions.QuestionData;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class QuestionResponse implements Serializable {

	private QuestionData data;

	private String jsonBody;

}
