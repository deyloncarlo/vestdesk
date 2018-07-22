import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FormaPagamento } from './forma-pagamento.model';
import { FormaPagamentoPopupService } from './forma-pagamento-popup.service';
import { FormaPagamentoService } from './forma-pagamento.service';

@Component({
    selector: 'jhi-forma-pagamento-dialog',
    templateUrl: './forma-pagamento-dialog.component.html'
})
export class FormaPagamentoDialogComponent implements OnInit {

    formaPagamento: FormaPagamento;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private formaPagamentoService: FormaPagamentoService,
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
        if (this.formaPagamento.id !== undefined) {
            this.subscribeToSaveResponse(
                this.formaPagamentoService.update(this.formaPagamento));
        } else {
            this.subscribeToSaveResponse(
                this.formaPagamentoService.create(this.formaPagamento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<FormaPagamento>>) {
        result.subscribe((res: HttpResponse<FormaPagamento>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: FormaPagamento) {
        this.eventManager.broadcast({ name: 'formaPagamentoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-forma-pagamento-popup',
    template: ''
})
export class FormaPagamentoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private formaPagamentoPopupService: FormaPagamentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.formaPagamentoPopupService
                    .open(FormaPagamentoDialogComponent as Component, params['id']);
            } else {
                this.formaPagamentoPopupService
                    .open(FormaPagamentoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
