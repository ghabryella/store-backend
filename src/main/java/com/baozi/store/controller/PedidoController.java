package com.baozi.store.controller;

import com.baozi.store.model.Cliente;
import com.baozi.store.model.Pedido;
import com.baozi.store.model.Produto;
import com.baozi.store.repository.ClienteRepository;
import com.baozi.store.repository.PedidoRepository;
import com.baozi.store.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    @PostMapping
    public Pedido criarPedido(@RequestBody Pedido pedido) {
        Pedido salvo = pedidoRepository.save(pedido);

        Cliente cliente = clienteRepository.findById(salvo.getCliente().getId()).get();
        Produto produto = produtoRepository.findById(salvo.getProduto().getId()).get();

        salvo.setCliente(cliente);
        salvo.setProduto(produto);

        return salvo;
    }

    @GetMapping
    public List<Pedido> buscarPedido() {
        List<Pedido> buscar = pedidoRepository.findAll();
        return buscar;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoId(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não existe."));


        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("/{id}")
    public void deletarPedido(@PathVariable Long id) {
        pedidoRepository.deleteById(id);
    }
}
