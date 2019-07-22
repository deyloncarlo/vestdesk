package br.com.vestdesk.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.vestdesk.service.PedidoItemService;
import br.com.vestdesk.service.PedidoService;
import br.com.vestdesk.service.dto.RelatorioVendaItemDTO;
import br.com.vestdesk.web.rest.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class RelatorioVendaResource
{

	private final Logger log = LoggerFactory.getLogger(RelatorioVendaResource.class);

	private static final String ENTITY_NAME = "RelatorioVenda";

	private final PedidoService pedidoService;

	private final PedidoItemService pedidoItemService;

	public RelatorioVendaResource(PedidoService pedidoService, PedidoItemService pedidoItemService)
	{
		this.pedidoService = pedidoService;
		this.pedidoItemService = pedidoItemService;
	}

	/**
	 * GET /quantidade-tamanhos : get all the quantidadeTamanhos.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 * quantidadeTamanhos in body
	 */
	@GetMapping("/relatorio-venda")
	@Timed
	public ResponseEntity<List<RelatorioVendaItemDTO>> getAllSelledItens(Pageable pageable)
	{

		Page<RelatorioVendaItemDTO> page = this.pedidoItemService.getRelatorioVenda(pageable);
		// this.log.debug("REST request to get a page of QuantidadeTamanhos");
		// Page<QuantidadeTamanhoDTO> page =
		// quantidadeTamanhoService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-vendidos");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

}
