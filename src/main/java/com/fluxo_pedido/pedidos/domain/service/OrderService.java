package com.fluxo_pedido.pedidos.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fluxo_pedido.pedidos.domain.event.OrderEvent;
import com.fluxo_pedido.pedidos.infrastructure.web.dto.OrderRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	// O segredo está aqui: você injeta a INTERFACE, não a classe concreta
	private final OrderPublisher orderPublisher;

	public void createOrder(OrderRequest request) {
		// 1. Lógica de negócio (ex: persistir no banco)
		String orderId = UUID.randomUUID().toString();

		// 2. Mapeamento para o Evento
		OrderEvent event = new OrderEvent(orderId, request.customerId(), request.totalValue(), request.items());

		// 3. Publicação (DIP em ação: chamando a interface)
		orderPublisher.sendOrderEvent(event);
	}
}
