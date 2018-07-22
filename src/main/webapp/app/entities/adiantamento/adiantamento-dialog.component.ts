import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Adiantamento } from './adiantamento.model';
import { AdiantamentoPopupService } from './adiantamento-popup.service';
import { AdiantamentoService } from './adiantamento.service';
import { FormaPagamento, FormaPagamentoService } from '../forma-pagamento';

@Component({
    selector: 'jhi-adiantamento-dialog',
    templateUrl: './adiantamento-dialog.component.html'
})
export class AdiantamentoDialogComponent implements OnInit {

    adiantamento: Adiantamento;
    isSaving: boolean;

    formapagamentos: FormaPagamento[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private adiantamentoService: AdiantamentoService,
        private formaPagamentoService: FormaPagamentoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.formaPagamentoService.query()
            .subscribe((res: HttpResponse<FormaPagamento[]>) => { this.formapagamentos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.adiantamento.id !== undefined) {
            this.subscribeToSaveResponse(
                this.adiantamentoService.update(this.adiantamento));
        } else {
            this.subscribeToSaveResponse(
                this.adiantamentoService.create(this.adiantamento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Adiantamento>>) {
        result.subscribe((res: HttpResponse<Adiantamento>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Adiantamento) {
        this.eventManager.broadcast({ name: 'adiantamentoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFormaPagamentoById(index: number, item: FormaPagamento) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-adiantamento-popup',
    template: ''
})
export class AdiantamentoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adiantamentoPopupService: AdiantamentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.adiantamentoPopupService
                    .open(AdiantamentoDialogComponent as Component, params['id']);
            } else {
                this.adiantamentoPopupService
                    .open(AdiantamentoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
