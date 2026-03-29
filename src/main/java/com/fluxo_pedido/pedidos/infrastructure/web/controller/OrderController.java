package com.fluxo_pedido.pedidos.infrastructure.web.controller;

import com.fluxo_pedido.pedidos.domain.service.OrderService;
import com.fluxo_pedido.pedidos.infrastructure.web.dto.OrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@RequestBody @Valid OrderRequest request) {
		orderService.createOrder(request);
		return "Pedido processado com sucesso e enviado para fila!";
	}
}