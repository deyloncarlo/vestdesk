package br.com.vestdesk.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.vestdesk.domain.enumeration.TipoPedido;
import br.com.vestdesk.domain.enumeration.TipoEstampa;
import br.com.vestdesk.domain.enumeration.TipoEstampa;
import br.com.vestdesk.domain.enumeration.TipoEstampa;
import br.com.vestdesk.domain.enumeration.TipoEstampa;

/**
 * A DTO for the Pedido entity.
 */
public class PedidoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private TipoPedido tipoPedido;

    private TipoEstampa tipoEstampaFrente;

    private TipoEstampa tipoEstampaCosta;

    private TipoEstampa tipoEstampaMangaDireita;

    private TipoEstampa tipoEstampaMangaEsquerda;

    private LocalDate dataCriacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public TipoEstampa getTipoEstampaFrente() {
        return tipoEstampaFrente;
    }

    public void setTipoEstampaFrente(TipoEstampa tipoEstampaFrente) {
        this.tipoEstampaFrente = tipoEstampaFrente;
    }

    public TipoEstampa getTipoEstampaCosta() {
        return tipoEstampaCosta;
    }

    public void setTipoEstampaCosta(TipoEstampa tipoEstampaCosta) {
        this.tipoEstampaCosta = tipoEstampaCosta;
    }

    public TipoEstampa getTipoEstampaMangaDireita() {
        return tipoEstampaMangaDireita;
    }

    public void setTipoEstampaMangaDireita(TipoEstampa tipoEstampaMangaDireita) {
        this.tipoEstampaMangaDireita = tipoEstampaMangaDireita;
    }

    public TipoEstampa getTipoEstampaMangaEsquerda() {
        return tipoEstampaMangaEsquerda;
    }

    public void setTipoEstampaMangaEsquerda(TipoEstampa tipoEstampaMangaEsquerda) {
        this.tipoEstampaMangaEsquerda = tipoEstampaMangaEsquerda;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoDTO pedidoDTO = (PedidoDTO) o;
        if(pedidoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedidoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipoPedido='" + getTipoPedido() + "'" +
            ", tipoEstampaFrente='" + getTipoEstampaFrente() + "'" +
            ", tipoEstampaCosta='" + getTipoEstampaCosta() + "'" +
            ", tipoEstampaMangaDireita='" + getTipoEstampaMangaDireita() + "'" +
            ", tipoEstampaMangaEsquerda='" + getTipoEstampaMangaEsquerda() + "'" +
            ", dataCriacao='" + getDataCriacao() + "'" +
            "}";
    }
}
