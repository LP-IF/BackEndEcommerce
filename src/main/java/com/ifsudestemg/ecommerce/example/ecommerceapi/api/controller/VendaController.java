package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.VendaDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Cliente;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Venda;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.ClienteService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.VendaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vendas")
@RequiredArgsConstructor
public class VendaController {
    private final VendaService vendaService;
    private final ClienteService clienteService;

    @GetMapping()
    @ApiOperation("Obter todos os vendaes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda encontrado")})
    public ResponseEntity get() {
        List<Venda> vendas = vendaService.getVenda();
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um venda")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Venda encontrado"),
            @ApiResponse(code = 404, message = "Venda não encontrado")})
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Venda") Long id) {
        Optional<Venda> venda = vendaService.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salvar um venda")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Venda criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar venda")})
    public ResponseEntity post(VendaDTO dto) {
        try {
            Venda venda = converter(dto);
            Cliente cliente = clienteService.salvar(venda.getCliente());
            venda.setCliente(cliente);
            venda = vendaService.salvar(venda);
            return new ResponseEntity(venda, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um venda")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Venda alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar venda")})
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id do Venda") Long id, VendaDTO dto) {
        if (!vendaService.getVendaById(id).isPresent()) {
            return new ResponseEntity("Venda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Venda venda = converter(dto);
            venda.setId(id);
            Cliente cliente = clienteService.salvar(venda.getCliente());
            venda.setCliente(cliente);
            vendaService.salvar(venda);
            return ResponseEntity.ok(venda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Apagar um venda")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Venda excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir venda"),
            @ApiResponse(code = 404, message = "Venda não encontrado")})
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Venda") Long id) {
        Optional<Venda> venda = vendaService.getVendaById(id);
        if (!venda.isPresent()) {
            return new ResponseEntity("Venda não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            vendaService.excluir(venda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Venda converter(VendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Venda venda = modelMapper.map(dto, Venda.class);
        if (dto.getClienteId() != null) {
            Optional<Cliente> cliente = clienteService.getClienteById(dto.getClienteId());
            if (!cliente.isPresent()) {
                venda.setCliente(null);
            } else {
                venda.setCliente(cliente.get());
            }
        }
        return venda;
    }
}