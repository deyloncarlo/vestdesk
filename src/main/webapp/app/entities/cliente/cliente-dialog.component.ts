import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Cliente } from './cliente.model';
import { ClientePopupService } from './cliente-popup.service';
import { ClienteService } from './cliente.service';

@Component({
    selector: 'jhi-cliente-dialog',
    templateUrl: './cliente-dialog.component.html'
})
export class ClienteDialogComponent implements OnInit {

    cliente: Cliente;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private clienteService: ClienteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cliente.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clienteService.update(this.cliente));
        } else {
            this.subscribeToSaveResponse(
                this.clienteService.create(this.cliente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Cliente>>) {
        result.subscribe((res: HttpResponse<Cliente>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Cliente) {
        this.eventManager.broadcast({ name: 'clienteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-cliente-popup',
    template: ''
})
export class ClientePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientePopupService: ClientePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.clientePopupService
                    .open(ClienteDialogComponent as Component, params['id']);
            } else {
                this.clientePopupService
                    .open(ClienteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
