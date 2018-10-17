import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pedido } from './pedido.model';
import { PedidoPopupService } from './pedido-popup.service';
import { PedidoService } from './pedido.service';
import { ClientePopupService, ClienteInputComponent, Cliente } from '../cliente';
import { Modelo } from '../modelo-vestuario';

@Component({
    selector: 'jhi-pedido-dialog',
    templateUrl: './pedido-dialog.component.html'
})
export class PedidoDialogComponent implements OnInit {

    pedido: Pedido;
    isSaving: boolean;
    dataCriacaoDp: any;

    nomeRoupa: string;
    telefone: string;
    modelo: Modelo;

    constructor(
        public activeModal: NgbActiveModal,
        private pedidoService: PedidoService,
        private eventManager: JhiEventManager,
        private clientePopupService: ClientePopupService
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        if (this.pedido.cliente == null) {
            this.pedido.cliente = new Cliente();
        }

    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pedido.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pedidoService.update(this.pedido));
        } else {
            this.subscribeToSaveResponse(
                this.pedidoService.create(this.pedido));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Pedido>>) {
        result.subscribe((res: HttpResponse<Pedido>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Pedido) {
        this.eventManager.broadcast({ name: 'pedidoListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    selecionarCliente() {
        this.clientePopupService.open(ClienteInputComponent as Component)
            .then( (resolve) => {
                resolve.result.then( (cliente) => {
                    if (cliente != null) {
                        this.pedido.cliente = cliente;
                    }
                });
            });
    }
}

@Component({
    selector: 'jhi-pedido-popup',
    template: ''
})
export class PedidoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pedidoPopupService: PedidoPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.pedidoPopupService
                    .open(PedidoDialogComponent as Component, params['id']);
            } else {
                this.pedidoPopupService
                    .open(PedidoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
