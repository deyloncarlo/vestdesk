package br.com.vestdesk.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.service.dto.AdiantamentoDTO;

/**
 * Service for managing Adiantamento.
 */
@Service
@Transactional
public class AdiantamentoService
{

	public AdiantamentoDTO save(AdiantamentoDTO adiantamentoDTO)
	{
		return null;
	}

	public Page<AdiantamentoDTO> findAll(Pageable pageable)
	{
		return null;
	}

	public AdiantamentoDTO findOne(Long id)
	{
		return null;
	}

	public void delete(Long id)
	{
	}
}
