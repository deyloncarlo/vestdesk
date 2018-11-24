package br.com.vestdesk.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Layout;
import br.com.vestdesk.repository.LayoutRepository;
import br.com.vestdesk.service.dto.LayoutDTO;
import br.com.vestdesk.service.mapper.LayoutMapper;

/**
 * Service Interface for managing Layout.
 */
@Service
@Transactional
public class LayoutService
{

	private final Logger log = LoggerFactory.getLogger(LayoutService.class);

	private final LayoutRepository layoutRepository;

	private final LayoutMapper layoutMapper;

	private EntityManager em;

	public LayoutService(LayoutRepository layoutRepository, LayoutMapper layoutMapper, EntityManager em)
	{
		this.layoutRepository = layoutRepository;
		this.layoutMapper = layoutMapper;
		this.em = em;
	}

	/**
	 * Save a layout.
	 *
	 * @param layoutDTO the entity to save
	 * @return the persisted entity
	 */
	public LayoutDTO save(LayoutDTO layoutDTO)
	{
		Layout layout = this.layoutMapper.toEntity(layoutDTO);
		layout = this.layoutRepository.save(layout);
		return this.layoutMapper.toDto(layout);
	}

	/**
	 * Get all the layouts.
	 *
	 * @param pageable the pagination information
	 * @param nome
	 * @return the list of entities
	 */
	public Page<LayoutDTO> findAll(Pageable pageable, String nome)
	{
		if (nome == null)
		{
			nome = "";
		}

		Query query = this.em.createQuery("SELECT layout FROM Layout layout WHERE nome LIKE :nomeLayout");
		query.setParameter("nomeLayout", "%" + nome + "%");

		List<Layout> listaLayout = query.getResultList();
		Page<Layout> page = new PageImpl<>(listaLayout, pageable, listaLayout.size());

		this.log.debug("Request to get all Produtos");
		return page.map(this.layoutMapper::toDto);
	}

	/**
	 * Get the "id" layout.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	public LayoutDTO findOne(Long id)
	{
		Layout layout = this.layoutRepository.findOne(id);
		return this.layoutMapper.toDto(layout);
	}

	public Layout getById(Long id)
	{
		Layout layout = this.layoutRepository.findOne(id);
		return layout;
	}

	/**
	 * Delete the "id" layout.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
		Layout layoutEncontrado = getById(id);
		if (layoutEncontrado.getListaConfiguracaoLayout().isEmpty())
		{
			this.layoutRepository.delete(id);
		}
		else
		{
			throw new RuntimeException("error.layout.existemPedidosReferenciandoEsteLayout");
		}
	}
}
