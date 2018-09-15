package br.com.vestdesk.service.impl;

import br.com.vestdesk.service.ConfiguracaoProdutoService;
import br.com.vestdesk.domain.ConfiguracaoProduto;
import br.com.vestdesk.repository.ConfiguracaoProdutoRepository;
import br.com.vestdesk.service.dto.ConfiguracaoProdutoDTO;
import br.com.vestdesk.service.mapper.ConfiguracaoProdutoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ConfiguracaoProduto.
 */
@Service
@Transactional
public class ConfiguracaoProdutoServiceImpl implements ConfiguracaoProdutoService {

    private final Logger log = LoggerFactory.getLogger(ConfiguracaoProdutoServiceImpl.class);

    private final ConfiguracaoProdutoRepository configuracaoProdutoRepository;

    private final ConfiguracaoProdutoMapper configuracaoProdutoMapper;

    public ConfiguracaoProdutoServiceImpl(ConfiguracaoProdutoRepository configuracaoProdutoRepository, ConfiguracaoProdutoMapper configuracaoProdutoMapper) {
        this.configuracaoProdutoRepository = configuracaoProdutoRepository;
        this.configuracaoProdutoMapper = configuracaoProdutoMapper;
    }

    /**
     * Save a configuracaoProduto.
     *
     * @param configuracaoProdutoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConfiguracaoProdutoDTO save(ConfiguracaoProdutoDTO configuracaoProdutoDTO) {
        log.debug("Request to save ConfiguracaoProduto : {}", configuracaoProdutoDTO);
        ConfiguracaoProduto configuracaoProduto = configuracaoProdutoMapper.toEntity(configuracaoProdutoDTO);
        configuracaoProduto = configuracaoProdutoRepository.save(configuracaoProduto);
        return configuracaoProdutoMapper.toDto(configuracaoProduto);
    }

    /**
     * Get all the configuracaoProdutos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConfiguracaoProdutoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConfiguracaoProdutos");
        return configuracaoProdutoRepository.findAll(pageable)
            .map(configuracaoProdutoMapper::toDto);
    }

    /**
     * Get one configuracaoProduto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConfiguracaoProdutoDTO findOne(Long id) {
        log.debug("Request to get ConfiguracaoProduto : {}", id);
        ConfiguracaoProduto configuracaoProduto = configuracaoProdutoRepository.findOne(id);
        return configuracaoProdutoMapper.toDto(configuracaoProduto);
    }

    /**
     * Delete the configuracaoProduto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConfiguracaoProduto : {}", id);
        configuracaoProdutoRepository.delete(id);
    }
}
