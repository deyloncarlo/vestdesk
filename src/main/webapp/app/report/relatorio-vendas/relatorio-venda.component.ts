import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RelatorioVenda } from './relatorio-venda.model';
import { RelatorioVendaService } from './relatorio-venda.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { Observable } from 'rxjs/Observable';
import { CorService, Cor } from '../../entities/cor';
import { ENUM } from "../../shared/enum";
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'jhi-relatorio-venda',
    templateUrl: './relatorio-venda.component.html'
})
export class RelatorioVendaComponent implements OnInit, OnDestroy {


    filterSettings: any;
    fieldList: any[];
    filterData: {};

    relatorioVenda: RelatorioVenda[];
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
    vendaAcumuladaEmProducao: RelatorioVenda[];

    constructor(
        private relatorioVendaService: RelatorioVendaService,
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
        this.relatorioVenda = [];
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
        this.relatorioVenda = [];
        this.vendaAcumuladaEmProducao = [];
        this.corService.query({}).subscribe(
            (res: HttpResponse<Cor[]>) => {
                for (let i = 0; i < res.body.length; i++) {
                    this.listaCor.push(res.body[i]);
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.relatorioVendaService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
            status: 'SEM_STATUS'
        }).subscribe(
            (res: HttpResponse<RelatorioVenda[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    reset() {
        this.page = 0;
        this.relatorioVenda = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }
    ngOnInit() {
        // this.loadAll();
        this.configureFilterForm();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRelatorioVenda();
    }

    private configureFilterForm() {
        this.filterData = {};

        this.filterSettings = {
        };

        this.fieldList = [
            {
                name: "responsableName",
                type: "text",
                label: "report.relatorioVenda.responsableName"
            },
            {
                name: "startDateFilter",
                type: "date",
                label: "report.relatorioVenda.startDateFilter",
                required: true
            },
            {
                name: "endDateFilter",
                type: "date",
                label: "report.relatorioVenda.endDateFilter",
                required: true
            }
        ]
    }


    filter(event) {
        this.listaPorModelo = [];
        this.listaCor = [];
        this.relatorioVenda = [];
        this.vendaAcumuladaEmProducao = [];
        this.corService.query({}).subscribe(
            (res: HttpResponse<Cor[]>) => {
                for (let i = 0; i < res.body.length; i++) {
                    this.listaCor.push(res.body[i]);
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.relatorioVendaService.query(this.filterData).subscribe(
            (res: HttpResponse<RelatorioVenda[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    obterRelatorioVenda() {
        this.relatorioVenda.forEach((vendaAcumulada) => {
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
            this.subscribeToSaveResponse(this.relatorioVendaService.produzir(result.data));
        }
    }

    concluir(template, vendaAcumulada) {
        this.ngbModal.open(template).result.then((result) => {
            if (result == "SIM") {
                this.subscribeToSaveResponse(this.relatorioVendaService.concluir(vendaAcumulada));
            }
        }, (reason) => {
        });
    }

    public subscribeToSaveResponse(result: Observable<HttpResponse<RelatorioVenda>>) {
        result.subscribe((res: HttpResponse<RelatorioVenda>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveError() {
    }

    public onSaveSuccess(result: RelatorioVenda) {
        this.eventManager.broadcast({ name: 'vendaAcumuladaListModification', content: 'OK' });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RelatorioVenda) {
        return item.id;
    }
    registerChangeInRelatorioVenda() {
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
        debugger
        if (this.relatorioVenda.length > 0) {
            this.relatorioVenda = [];
            this.listaPorModelo = [];
        }
        for (let i = 0; i < data.length; i++) {
            this.relatorioVenda.push(data[i]);
        }

        this.obterRelatorioVenda();
    }

    onSuccessEmProducao(data, headers) {
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
