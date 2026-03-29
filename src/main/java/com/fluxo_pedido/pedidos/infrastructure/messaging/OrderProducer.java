package com.fluxo_pedido.pedidos.infrastructure.messaging;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.fluxo_pedido.pedidos.domain.event.OrderEvent;
import com.fluxo_pedido.pedidos.domain.service.OrderPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer implements OrderPublisher {

	private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
	private static final String TOPIC = "pedidos-criados";

	@Override
	public void sendOrderEvent(OrderEvent event) {
		log.info("Enviando pedido ao Kafka: {}", event.orderId());

		// O envio do KafkaTemplate já é assíncrono por padrão
		CompletableFuture<SendResult<String, OrderEvent>> future = kafkaTemplate.send(TOPIC, event.orderId(), event);

		// Tratando o callback (Sucesso ou Falha)
		future.whenComplete((result, ex) -> {
			if (ex == null) {
				log.info("Pedido enviado com sucesso! Offset: {}", result.getRecordMetadata().offset());
			} else {
				log.error("Erro ao enviar pedido: {}", ex.getMessage());
				// Aqui como arquiteto você pensaria:
				// Salvar num banco de 'retry' ou enviar para uma DLQ?
			}
		});
	}
}