package br.com.vestdesk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Produto entity.
 */
public class ProdutoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long oid;

    @NotNull
    private Integer quantidadeEstoque;

    @NotNull
    private String descricao;

    private Long configuracaoProdutoId;

    private Set<CorDTO> listaCors = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getConfiguracaoProdutoId() {
        return configuracaoProdutoId;
    }

    public void setConfiguracaoProdutoId(Long configuracaoProdutoId) {
        this.configuracaoProdutoId = configuracaoProdutoId;
    }

    public Set<CorDTO> getListaCors() {
        return listaCors;
    }

    public void setListaCors(Set<CorDTO> cors) {
        this.listaCors = cors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProdutoDTO produtoDTO = (ProdutoDTO) o;
        if(produtoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produtoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
            "id=" + getId() +
            ", oid=" + getOid() +
            ", quantidadeEstoque=" + getQuantidadeEstoque() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
