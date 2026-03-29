package com.fluxo_pedido.pedidos.domain.event;

import java.math.BigDecimal;
import java.util.List;

public record OrderEvent(
    String orderId,
    String customerId,
    BigDecimal totalValue,
    List<String> items
) {}
