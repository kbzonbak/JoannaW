package bbr.b2b.soa.mercadolibre.utis;

import java.util.Optional;

import org.springframework.stereotype.Component;

import bbr.b2b.soa.mercadolibre.dto.classes.orders.OrderData;

@Component
public interface IMessageMapper {

	public Optional<String> getOrderNotificationMessage(OrderData data);

	public Optional<String> getOrderMessage(OrderData data);

}
