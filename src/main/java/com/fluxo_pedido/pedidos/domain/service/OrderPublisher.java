package com.fluxo_pedido.pedidos.domain.service;

import com.fluxo_pedido.pedidos.domain.event.OrderEvent;

public interface OrderPublisher {
    void sendOrderEvent(OrderEvent event);
}