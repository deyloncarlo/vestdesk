import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';

import { Pedido, TipoPedido, StatusPedido } from './pedido.model';
import { PedidoService } from './pedido.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { flushMicrotasks } from '@angular/core/testing';

@Component({
    selector: 'jhi-pedido',
    templateUrl: './pedido.component.html'
})
export class PedidoComponent implements OnInit, OnDestroy {

    pedidos: Pedido[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    id: number;
    statusPedido: StatusPedido;
    fechaEm10Dias: boolean;

    constructor(
        private pedidoService: PedidoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private route: ActivatedRoute
    ) {
        this.pedidos = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.pedidoService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: HttpResponse<Pedido[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    filtrar() {
        this.page = 0;
        this.pedidos = [];

        if (!this.id) {
            this.id = null;
        }
        if (!this.statusPedido) {
            this.statusPedido = null;
        }



        this.pedidoService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
            id: this.id,
            statusPedido: this.statusPedido,
            fechaEm10Dias: this.fechaEm10Dias
        }).subscribe(
            (res: HttpResponse<Pedido[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    reset() {
        this.page = 0;
        this.pedidos = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }
    ngOnInit() {
        this.obterParamentrosUrl();
        // this.loadAll();
        this.filtrar();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPedidos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Pedido) {
        return item.id;
    }
    registerChangeInPedidos() {
        this.eventSubscriber = this.eventManager.subscribe('pedidoListModification', (response) => this.reset());
    }

    obterParamentrosUrl() {
        let obtendoParametros = this.route.params.subscribe(params => {
            debugger
            this.statusPedido = params['statusPedido']; // (+) converts string 'id' to a number
            this.fechaEm10Dias = params['fechaEm10Dias'] == "true"; // (+) converts string 'id' to a number
        });
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.pedidos.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
