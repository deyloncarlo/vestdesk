import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { VendaAcumulada } from './venda-acumulada.model';
import { VendaAcumuladaService } from './venda-acumulada.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { Observable } from 'rxjs/Observable';
import { CorService, Cor } from '../cor';
import { ENUM } from "./../../shared/enum";
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'jhi-venda-acumulada',
    templateUrl: './venda-acumulada.component.html'
})
export class VendaAcumuladaComponent implements OnInit, OnDestroy {

    vendaAcumuladas: VendaAcumulada[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    listaPorModelo: any[];
    listaTamanho: any[];
    listaCor: Cor[];
    vendaAcumuladaEmProducao: VendaAcumulada[];

    constructor(
        private vendaAcumuladaService: VendaAcumuladaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private ngbModal: NgbModal,
        private corService: CorService,
        private route: Router,
    ) {
        this.listaPorModelo = [];
        this.listaCor = [];
        this.vendaAcumuladas = [];
        this.listaTamanho = ENUM['Tamanho'];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.listaPorModelo = [];
        this.listaCor = [];
        this.vendaAcumuladas = [];
        this.vendaAcumuladaEmProducao = [];
        this.corService.query({}).subscribe(
            (res: HttpResponse<Cor[]>) => {
                for (let i = 0; i < res.body.length; i++) {
                    this.listaCor.push(res.body[i]);
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.vendaAcumuladaService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
            status: 'SEM_STATUS'
        }).subscribe(
            (res: HttpResponse<VendaAcumulada[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.vendaAcumuladaService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
            status: 'EM_PRODUCAO'
        }).subscribe(
            (res: HttpResponse<VendaAcumulada[]>) => this.onSuccessEmProducao(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    reset() {
        this.page = 0;
        this.vendaAcumuladas = [];
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
        this.registerChangeInVendaAcumuladas();
    }

    obterVendaAcumuladaPorModeloCor() {
        this.vendaAcumuladas.forEach((vendaAcumulada) => {
            let modeloEncontrado = this.listaPorModelo.filter((modelo) => {
                return modelo.nomeModelo == vendaAcumulada.produto.modelo;
            });
            if (modeloEncontrado.length == 0) {
                let novo = {
                    nomeModelo: vendaAcumulada.produto.modelo,
                    array: []
                };
                modeloEncontrado[0] = novo;
                this.listaPorModelo.push(modeloEncontrado[0]);
            }
            modeloEncontrado[0].array.push(vendaAcumulada);
        });
    }

    public produz(result) {
        if (result.result == "SIM") {
            this.subscribeToSaveResponse(this.vendaAcumuladaService.produzir(result.data));
        }
    }

    concluir (template, vendaAcumulada) {
        this.ngbModal.open(template).result.then((result)=> {
            if (result == "SIM") {
                this.subscribeToSaveResponse(this.vendaAcumuladaService.concluir(vendaAcumulada));
            }
        }, (reason) => {
        });
    } 

    public subscribeToSaveResponse(result: Observable<HttpResponse<VendaAcumulada>>) {
        result.subscribe((res: HttpResponse<VendaAcumulada>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveError() {
    }

    public onSaveSuccess(result: VendaAcumulada) {
        this.eventManager.broadcast({ name: 'vendaAcumuladaListModification', content: 'OK' });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VendaAcumulada) {
        return item.id;
    }
    registerChangeInVendaAcumuladas() {
        this.eventSubscriber = this.eventManager.subscribe('vendaAcumuladaListModification', (response) => this.reset());
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
            this.vendaAcumuladas.push(data[i]);
        }

        this.obterVendaAcumuladaPorModeloCor();
    }

    onSuccessEmProducao (data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.vendaAcumuladaEmProducao.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
