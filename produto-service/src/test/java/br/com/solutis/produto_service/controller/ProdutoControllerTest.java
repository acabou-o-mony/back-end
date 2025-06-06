package br.com.solutis.produto_service.controller;

import br.com.solutis.produto_service.dto.ProdutoRequestDto;
import br.com.solutis.produto_service.dto.ProdutoResponseDto;
import br.com.solutis.produto_service.dto.ProdutoUpdateDto;
import br.com.solutis.produto_service.entity.Produto;
import br.com.solutis.produto_service.mapper.ProdutoMapper;
import br.com.solutis.produto_service.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProdutoRequestDto requestDto;

    //METODO PARA TESTAR O BAD REQUEST
    private ProdutoRequestDto requestDtoInvalido;

    //METODO PARA TESTE O CONFLICT
    private ProdutoRequestDto requestDtoDuplicado;

    private ProdutoResponseDto responseDto;

    private ProdutoUpdateDto updateDto;

    //METODO PARA TESTAR O BAD REQUEST
    private ProdutoUpdateDto updateDtoInvalido;

    @BeforeEach
    void setUp() {

        requestDto = new ProdutoRequestDto(
                "Garrafa de agua",
                "Garrafa de agua de 50ml",
                100.0,
                100,
                true,
                LocalDate.of(2025, 5, 21)
        );

        requestDtoDuplicado = new ProdutoRequestDto(
                requestDto.nome(),
                "Garrafa de agua de 100ml",
                150.0,
                30,
                true,
                LocalDate.of(2025, 5, 10)
        );


        requestDtoInvalido = new ProdutoRequestDto(
                "",
                "Arroz Camil",
                109.0,
                15,
                true,
                LocalDate.of(2025, 5, 11)
        );

        responseDto = new ProdutoResponseDto(
                1L,
                "Garrafa de agua",
                "Garrafa de agua de 50ml",
                100.0,
                100,
                true,
                LocalDate.of(2025, 5, 21)
        );

        updateDto = new ProdutoUpdateDto(50, false);

        updateDtoInvalido = new ProdutoUpdateDto(-5, null);
    }

    @Test
    @DisplayName("Deve cadastrar um produto com sucesso e deve retornar 200")
    void deveCadastrarUmProdutoComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    assertTrue(response.contains("\"nome\":\"Garrafa de agua\""));
                });

    }

    @Test
    @DisplayName("Deve lançar erro 400 quando dados inválidos forem enviados")
    void deveRetornar400ParaDadosInvalidos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve lançar erro 409 ao tentar cadastrar produto com nome já existente")
    void deveLancarErroConflitoAoCadastrarProdutoComNomeExistente() throws Exception {
        Produto produto = ProdutoMapper.toEntity(requestDto);
        produtoService.cadastrar(produto);

        mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDtoDuplicado)))
                .andExpect(status().isConflict());
    }


    @Test
    @DisplayName("Deve retornar uma lista de produtos cadastrados e deve retornar código 200")
    void deveListarProdutosCadastradosComSucesso() throws Exception {
        Produto produto = ProdutoMapper.toEntity(requestDto);
        produtoService.cadastrar(produto);

        mockMvc.perform(MockMvcRequestBuilders.get("/produtos")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value(responseDto.nome()))
                .andExpect(jsonPath("$[0].descricao").value(responseDto.descricao()))
                .andExpect(jsonPath("$[0].precoUnitario").value(responseDto.precoUnitario()))
                .andExpect(jsonPath("$[0].estoque").value(responseDto.estoque()))
                .andExpect(jsonPath("[0].ativo").value(responseDto.ativo()))
                .andExpect(jsonPath("$[0].dataCriacao").value(responseDto.dataCriacao().toString()));
    }

    @Test
    @DisplayName("Deve retornar 204 quando não houver produtos")
    void deveRetornar204QuandoNaoHouverProdutos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 200 e os dados do produto com o ID fornecido")
    void deveBuscarProdutoPorIdComSucesso() throws Exception {
        Produto produto = ProdutoMapper.toEntity(requestDto);
        Produto produtoSalvo = produtoService.cadastrar(produto);
        ProdutoResponseDto produtoDto = ProdutoMapper.toDto(produtoSalvo);

        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/{id}", produtoDto.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(produtoDto.id()))
                .andExpect(jsonPath("$.nome").value(produtoDto.nome()))
                .andExpect(jsonPath("$.descricao").value(produtoDto.descricao()))
                .andExpect(jsonPath("$.precoUnitario").value(produtoDto.precoUnitario()))
                .andExpect(jsonPath("$.estoque").value(produtoDto.estoque()))
                .andExpect(jsonPath("$.ativo").value(produtoDto.ativo()))
                .andExpect(jsonPath("$.dataCriacao").value(produtoDto.dataCriacao().toString()));
    }

    @Test
    @DisplayName("Deve retornar 404 quando não encontrar um produto com o ID fornecido")
    void deveRetornar404QuandoNaoEncontrarProdutoPorId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/{id}", 8L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar apenas produtos ativos com todos os campos e status 200")
    void deveRetornarProdutosAtivosComTodosOsCampos() throws Exception {
        Produto produto = ProdutoMapper.toEntity(requestDto);
        Produto produtoSalvo = produtoService.cadastrar(produto);
        ProdutoResponseDto produtoDto = ProdutoMapper.toDto(produtoSalvo);

        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/ativos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(produtoDto.id()))
                .andExpect(jsonPath("$[0].nome").value(produtoDto.nome()))
                .andExpect(jsonPath("$[0].descricao").value(produtoDto.descricao()))
                .andExpect(jsonPath("$[0].precoUnitario").value(produtoDto.precoUnitario()))
                .andExpect(jsonPath("$[0].estoque").value(produtoDto.estoque()))
                .andExpect(jsonPath("$[0].ativo").value(produtoDto.ativo()))
                .andExpect(jsonPath("$[0].dataCriacao").value(produtoDto.dataCriacao().toString()));
    }

    @Test
    @DisplayName("Deve retornar 204 quando não houver produtos ativos")
    void deveRetornar204SemProdutosAtivos() throws Exception {
        Produto produtoInativo = ProdutoMapper.toEntity(requestDto);

        produtoInativo.setAtivo(false);
        produtoInativo.setNome("Produto Inativo");

        produtoService.cadastrar(produtoInativo);

        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/ativos"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar produtos com nome correspondente (ignorar case) e todos os campos")
    void deveBuscarProdutoPorNomeComTodosOsCampos() throws Exception {
        Produto produto = ProdutoMapper.toEntity(requestDto);
        Produto produtoSalvo = produtoService.cadastrar(produto);

        ProdutoResponseDto produtoDto = ProdutoMapper.toDto(produtoSalvo);

        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/nomes/{nome}", "garrafa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(produtoDto.id()))
                .andExpect(jsonPath("$[0].nome").value(produtoDto.nome()))
                .andExpect(jsonPath("$[0].descricao").value(produtoDto.descricao()))
                .andExpect(jsonPath("$[0].precoUnitario").value(produtoDto.precoUnitario()))
                .andExpect(jsonPath("$[0].estoque").value(produtoDto.estoque()))
                .andExpect(jsonPath("$[0].ativo").value(produtoDto.ativo()))
                .andExpect(jsonPath("$[0].dataCriacao").value(produtoDto.dataCriacao().toString()));
    }

    @Test
    @DisplayName("Deve retornar 204 quando não houver produtos com o nome passado")
    void deveRetornar204SemProdutosComNomeEspecifico() throws Exception {
        Produto produtoNome = ProdutoMapper.toEntity(requestDto);

        produtoNome.setNome("Not Found");

        produtoService.cadastrar(produtoNome);

        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/nomes/{nome}", "garrafa"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve atualizar o estoque e ativo de um produto existente e retornar 200")
    void deveAtualizarProdutoComSucesso() throws Exception {
        Produto produto = ProdutoMapper.toEntity(requestDto);
        Produto produtoSalvo = produtoService.cadastrar(produto);

        mockMvc.perform(MockMvcRequestBuilders.put("/produtos/{id}", produtoSalvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estoque").value(updateDto.estoque()))
                .andExpect(jsonPath("$.ativo").value(updateDto.ativo()));
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar atualizar um produto inexistente")
    void deveRetornar404QuandoProdutoNaoExiste() throws Exception {
        Long idInexistente = 99L;

        mockMvc.perform(MockMvcRequestBuilders.put("/produtos/{id}", idInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 400 ao enviar dados inválidos para atualização")
    void deveRetornar400ParaDadosInvalidosNaAtualizacao() throws Exception {
        Produto produto = ProdutoMapper.toEntity(requestDto);
        Produto produtoSalvo = produtoService.cadastrar(produto);

        mockMvc.perform(MockMvcRequestBuilders.put("/produtos/{id}", produtoSalvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 204 ao deletar o produto com o ID fornecido")
    void deveDeletarProdutoPorIdComSucesso() throws Exception {
        Produto produto = ProdutoMapper.toEntity(requestDto);
        Produto produtoSalvo = produtoService.cadastrar(produto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/{id}", produtoSalvo.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar deletar um produto inexistente")
    void deveRetornar404AoDeletarProdutoInexistente() throws Exception {

        Long idInexistente = 99L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/{id}", idInexistente))
                .andExpect(status().isNotFound());
    }

}