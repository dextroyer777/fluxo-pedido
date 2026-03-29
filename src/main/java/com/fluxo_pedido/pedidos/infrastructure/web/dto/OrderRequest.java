package com.fluxo_pedido.pedidos.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
    @NotBlank String customerId,
    @NotNull @Positive BigDecimal totalValue,
    @NotNull List<String> items
) {}