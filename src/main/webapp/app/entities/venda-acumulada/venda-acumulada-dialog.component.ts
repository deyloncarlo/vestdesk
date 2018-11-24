import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VendaAcumulada } from './venda-acumulada.model';
import { VendaAcumuladaPopupService } from './venda-acumulada-popup.service';
import { VendaAcumuladaService } from './venda-acumulada.service';

@Component({
    selector: 'jhi-venda-acumulada-dialog',
    templateUrl: './venda-acumulada-dialog.component.html'
})
export class VendaAcumuladaDialogComponent implements OnInit {

    vendaAcumulada: VendaAcumulada;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private vendaAcumuladaService: VendaAcumuladaService,
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
        if (this.vendaAcumulada.id !== undefined) {
            this.subscribeToSaveResponse(
                this.vendaAcumuladaService.update(this.vendaAcumulada));
        } else {
            this.subscribeToSaveResponse(
                this.vendaAcumuladaService.create(this.vendaAcumulada));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<VendaAcumulada>>) {
        result.subscribe((res: HttpResponse<VendaAcumulada>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: VendaAcumulada) {
        this.eventManager.broadcast({ name: 'vendaAcumuladaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-venda-acumulada-popup',
    template: ''
})
export class VendaAcumuladaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vendaAcumuladaPopupService: VendaAcumuladaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.vendaAcumuladaPopupService
                    .open(VendaAcumuladaDialogComponent as Component, params['id']);
            } else {
                this.vendaAcumuladaPopupService
                    .open(VendaAcumuladaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
