import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Cliente } from './cliente.model';
import { ClienteService } from './cliente.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-cliente-input',
    templateUrl: './cliente-input.component.html'
})
export class ClienteInputComponent implements OnInit, OnDestroy {

    clientes: Cliente[];
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
        private clienteService: ClienteService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        public activeModal: NgbActiveModal
    ) {
        this.clientes = [];
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
        this.clienteService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: HttpResponse<Cliente[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    filtrar() {
        this.page = 0;
        this.clientes = [];
        this.clienteService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
            nome: this.nome

        }).subscribe(
            (res: HttpResponse<Cliente[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    reset() {
        this.page = 0;
        this.clientes = [];
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

    trackId(index: number, item: Cliente) {
        return item.id;
    }
    registerChangeInClientes() {
        this.eventSubscriber = this.eventManager.subscribe('clienteListModification', (response) => this.reset());
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    selecionar(cliente) {
        if (cliente) {
            this.activeModal.close(cliente);
        }
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
            this.clientes.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
