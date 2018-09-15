package br.com.vestdesk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Layout entity.
 */
public class LayoutDTO implements Serializable {

    private Long id;

    @NotNull
    private Long oid;

    @NotNull
    private String nome;

    @NotNull
    @Lob
    private byte[] imagem;
    private String imagemContentType;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return imagemContentType;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LayoutDTO layoutDTO = (LayoutDTO) o;
        if(layoutDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), layoutDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LayoutDTO{" +
            "id=" + getId() +
            ", oid=" + getOid() +
            ", nome='" + getNome() + "'" +
            ", imagem='" + getImagem() + "'" +
            "}";
    }
}
