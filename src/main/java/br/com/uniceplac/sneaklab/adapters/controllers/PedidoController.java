package br.com.uniceplac.sneaklab.adapters.controllers;

import br.com.uniceplac.sneaklab.adapters.dtos.NovoItemPedidoDto;
import br.com.uniceplac.sneaklab.adapters.dtos.PedidoDto;
import br.com.uniceplac.sneaklab.application.ports.in.GerenciarPedidoUseCase;
import br.com.uniceplac.sneaklab.domain.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final GerenciarPedidoUseCase gerenciarPedidoUseCase;

    public PedidoController(GerenciarPedidoUseCase gerenciarPedidoUseCase) {
        this.gerenciarPedidoUseCase = gerenciarPedidoUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto criar(@RequestParam int idCliente) {
        Pedido pedido = gerenciarPedidoUseCase.criarPedido(idCliente);
        return PedidoDto.fromDomain(pedido);
    }

    @PostMapping("/{id}/itens")
    @ResponseStatus(HttpStatus.OK)
    public PedidoDto adicionarItem(@PathVariable int id,
                                   @RequestBody NovoItemPedidoDto dto) {
        Pedido pedido = gerenciarPedidoUseCase.adicionarItem(id, (int) dto.getIdProduto(), dto.getQuantidade());
        return PedidoDto.fromDomain(pedido);
    }

    @GetMapping
    public List<PedidoDto> listar(@RequestParam(required = false) Integer idCliente) {
        List<Pedido> pedidos = (idCliente == null)
                ? gerenciarPedidoUseCase.listarPedidos()
                : gerenciarPedidoUseCase.listarPedidosPorCliente(idCliente);

        return pedidos.stream()
                .map(PedidoDto::fromDomain)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PedidoDto buscarPorId(@PathVariable int id) {
        Pedido pedido = gerenciarPedidoUseCase.buscarPorId(id);
        return PedidoDto.fromDomain(pedido);
    }

    @PostMapping("/{id}/enviar")
    public PedidoDto enviar(@PathVariable int id) {
        Pedido pedido = gerenciarPedidoUseCase.enviarPedido(id);
        return PedidoDto.fromDomain(pedido);
    }

    @PostMapping("/{id}/entregar")
    public PedidoDto confirmarEntrega(@PathVariable int id) {
        Pedido pedido = gerenciarPedidoUseCase.confirmarEntrega(id);
        return PedidoDto.fromDomain(pedido);
    }

    @PostMapping("/{id}/cancelar")
    public PedidoDto cancelar(@PathVariable int id) {
        Pedido pedido = gerenciarPedidoUseCase.cancelarPedido(id);
        return PedidoDto.fromDomain(pedido);
    }
}
