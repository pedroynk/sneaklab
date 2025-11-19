package br.com.uniceplac.sneaklab.adapters.controllers;

import br.com.uniceplac.sneaklab.adapters.dtos.PagamentoDto;
import br.com.uniceplac.sneaklab.application.ports.in.GerenciarPagamentoUseCase;
import br.com.uniceplac.sneaklab.domain.Pagamento;
import br.com.uniceplac.sneaklab.domain.TipoPagamento;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final GerenciarPagamentoUseCase pagamentoUseCase;

    public PagamentoController(GerenciarPagamentoUseCase pagamentoUseCase) {
        this.pagamentoUseCase = pagamentoUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoDto criarPagamento(@RequestBody PagamentoDto dto) {
        Pagamento pagamento = pagamentoUseCase.registrarPagamento(
                (int) dto.getIdPedido(),
                dto.getValor(),
                dto.getTipo()
        );
        return PagamentoDto.fromDomain(pagamento);
    }

    @PostMapping("/{id}/aprovar")
    public PagamentoDto aprovar(@PathVariable int id) {
        Pagamento pagamento = pagamentoUseCase.aprovarPagamento(id);
        return PagamentoDto.fromDomain(pagamento);
    }

    @PostMapping("/{id}/estornar")
    public PagamentoDto estornar(@PathVariable int id) {
        Pagamento pagamento = pagamentoUseCase.estornarPagamento(id);
        return PagamentoDto.fromDomain(pagamento);
    }

    @GetMapping("/{id}")
    public PagamentoDto buscarPorId(@PathVariable int id) {
        Pagamento pagamento = pagamentoUseCase.buscarPorId(id);
        return PagamentoDto.fromDomain(pagamento);
    }

    @GetMapping("/pedido/{idPedido}")
    public List<PagamentoDto> listarPorPedido(@PathVariable int idPedido) {
        return pagamentoUseCase.listarPorPedido(idPedido).stream()
                .map(PagamentoDto::fromDomain)
                .collect(Collectors.toList());
    }
}
