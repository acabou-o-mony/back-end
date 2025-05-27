package mapper;

import dto.ProdutoRequestDto;
import dto.ProdutoResponseDto;
import dto.ProdutoUpdateDto;
import entity.Produto;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoRequestDto requestDto){

        if(requestDto == null){
            return null;
        }

        Produto produto = new Produto();
        produto.setNome(requestDto.nome());
        produto.setDescricao(requestDto.descricao());
        produto.setEstoque(requestDto.estoque());
        produto.setAtivo(requestDto.ativo());
        produto.setDataCriacao(requestDto.dataCriacao());

        return produto;
    }

    public static ProdutoResponseDto toDto(Produto produto){

        if(produto == null){
            return null;
        }

        ProdutoResponseDto responseDto = new ProdutoResponseDto(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getEstoque(),
                produto.getAtivo(),
                produto.getDataCriacao()
        );

        return responseDto;
    }

    public static Produto toUpdate(ProdutoUpdateDto dto, Long id){
        if (dto == null){
            return null;
        }

        Produto produtoUpdate = new Produto();
        produtoUpdate.setId(id);
        produtoUpdate.setEstoque(dto.estoque());
        produtoUpdate.setAtivo(dto.ativo());

        return produtoUpdate;
    }
}
