import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { LayoutService } from './layout.service';
import { Layout } from './layout.model';
import { ConfiguracaoLayout } from './../configuracao-layout';

@Component({
    selector: 'jhi-layout-input',
    templateUrl: './layout-input.component.html'
})
export class LayoutInputComponent implements OnInit, OnDestroy {

    layouts: Layout[];
    selecionados: ConfiguracaoLayout[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    // Filtros
    nome: string;

    constructor(
        private layoutService: LayoutService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        public activeModal: NgbActiveModal
    ) {
        this.layouts = [];
        this.selecionados = [];
        this.nome = '';
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.layoutService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: HttpResponse<Layout[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    filtrar() {
        this.page = 0;
        this.layouts = [];
        this.layoutService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
            nome: this.nome

        }).subscribe(
            (res: HttpResponse<Layout[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    reset() {
        this.page = 0;
        this.layouts = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClientes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Layout) {
        return item.id;
    }
    registerChangeInClientes() {
        this.eventSubscriber = this.eventManager.subscribe('clienteListModification', (response) => this.reset());
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    desselecionar(layout) {
        layout.selecionado = false;
        layout.configuracaoLayout = null;
        let index = this.selecionados.indexOf(layout);
        this.selecionados.splice(1, index);
    }

    clique(layout) {
        if(layout.selecionado) {
            this.desselecionar(layout);
        }else {
            debugger
            this.selecionar(layout);
        }
    }

    finalizar() {
        this.activeModal.close(this.selecionados);
    }

    selecionar(layout) {
        layout.selecionado = true;
        layout.configuracaoLayout = new ConfiguracaoLayout();
        layout.configuracaoLayout.layout = new Layout();
        layout.configuracaoLayout.layout.id = layout.id;
        this.selecionados.push(layout.configuracaoLayout);
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
            debugger
            this.layouts.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
