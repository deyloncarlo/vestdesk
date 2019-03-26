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

	// Cor toEntityMin(CorMinDTO corMinDto);

	NotificacaoDTO toDto(Notificacao cor);

	List<NotificacaoDTO> toListDto(List<Notificacao> listaNotificacao);

	// CorMinDTO toMinDto(Cor cor);

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
