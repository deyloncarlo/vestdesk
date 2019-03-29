package br.com.vestdesk.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.vestdesk.domain.Notificacao;
import br.com.vestdesk.service.dto.NotificacaoDTO;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface NotificacaoMapper
{
	Notificacao toEntity(NotificacaoDTO notficacaoDTO);

	List<Notificacao> toEntity(List<NotificacaoDTO> listaNotificacaoDTO);

	NotificacaoDTO toDto(Notificacao notificacao);

	List<NotificacaoDTO> toListDto(List<Notificacao> listaNotificacao);

	default Notificacao fromId(Long id)
	{
		if (id == null)
		{
			return null;
		}
		Notificacao notificacao = new Notificacao();
		notificacao.setId(id);
		return notificacao;
	}
}
