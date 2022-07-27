package com.ifsudestemg.ecommerce.example.ecommerceapi.api.controller;

import com.ifsudestemg.ecommerce.example.ecommerceapi.api.dto.EnderecoDTO;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Endereco;
import com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity.Usuario;
import com.ifsudestemg.ecommerce.example.ecommerceapi.exception.RegraNegocioException;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.EnderecoService;
import com.ifsudestemg.ecommerce.example.ecommerceapi.service.UsuarioService;
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
@RequestMapping("/api/v1/enderecos")
@RequiredArgsConstructor
public class EnderecoController {
    private final EnderecoService enderecoService;
    private final UsuarioService usuarioService;

    @GetMapping()
    @ApiOperation("Obter todos os endereços")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereços encontrado")})
    public ResponseEntity get() {
        List<Endereco> enderecos = enderecoService.getEndereco();
        return ResponseEntity.ok(enderecos.stream().map(EnderecoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um endereço")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereço encontrado"),
            @ApiResponse(code = 404, message = "Endereço não encontrado")})
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do endereço") Long id) {
        Optional<Endereco> endereco = enderecoService.getEnderecoById(id);
        if (!endereco.isPresent()) {
            return new ResponseEntity("Endereco não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(endereco.map(EnderecoDTO::create));
    }
    @PostMapping()
    @ApiOperation("Salvar um endereço")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Endereço criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar endereço")})
    public ResponseEntity post(EnderecoDTO dto) {
        try {
            Endereco endereco = converter(dto);
            Usuario usuario = usuarioService.salvar(endereco.getUsuario());
            endereco.setUsuario(usuario);
            endereco = enderecoService.salvar(endereco);
            return new ResponseEntity(endereco, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um endereço")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Endereço alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar endereço")})
    public ResponseEntity atualizar(@PathVariable("id") Long id, EnderecoDTO dto) {
        if (!enderecoService.getEnderecoById(id).isPresent()) {
            return new ResponseEntity("Endereco não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Endereco endereco = converter(dto);
            endereco.setId(id);
            Usuario usuario = usuarioService.salvar(endereco.getUsuario());
            endereco.setUsuario(usuario);
            enderecoService.salvar(endereco);
            return ResponseEntity.ok(endereco);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Apagar um endereço")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Endereço excluido com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir endereço"),
            @ApiResponse(code = 404, message = "Enderço não encontrado")})
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Endereco> endereco = enderecoService.getEnderecoById(id);
        if (!endereco.isPresent()) {
            return new ResponseEntity("Endereco não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            enderecoService.excluir(endereco.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Endereco converter(EnderecoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        if (dto.getUsuarioId() != null) {
            Optional<Usuario> usuario = usuarioService.getUsuarioById(dto.getUsuarioId());
            if (!usuario.isPresent()) {
                endereco.setUsuario(null);
            } else {
                endereco.setUsuario(usuario.get());
            }
        }
        return endereco;
    }
}
