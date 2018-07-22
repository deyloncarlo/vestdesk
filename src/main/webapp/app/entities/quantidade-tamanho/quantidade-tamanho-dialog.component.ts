import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { QuantidadeTamanho } from './quantidade-tamanho.model';
import { QuantidadeTamanhoPopupService } from './quantidade-tamanho-popup.service';
import { QuantidadeTamanhoService } from './quantidade-tamanho.service';
import { ModeloVestuario, ModeloVestuarioService } from '../modelo-vestuario';

@Component({
    selector: 'jhi-quantidade-tamanho-dialog',
    templateUrl: './quantidade-tamanho-dialog.component.html'
})
export class QuantidadeTamanhoDialogComponent implements OnInit {

    quantidadeTamanho: QuantidadeTamanho;
    isSaving: boolean;

    modelovestuarios: ModeloVestuario[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private quantidadeTamanhoService: QuantidadeTamanhoService,
        private modeloVestuarioService: ModeloVestuarioService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.modeloVestuarioService.query()
            .subscribe((res: HttpResponse<ModeloVestuario[]>) => { this.modelovestuarios = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.quantidadeTamanho.id !== undefined) {
            this.subscribeToSaveResponse(
                this.quantidadeTamanhoService.update(this.quantidadeTamanho));
        } else {
            this.subscribeToSaveResponse(
                this.quantidadeTamanhoService.create(this.quantidadeTamanho));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<QuantidadeTamanho>>) {
        result.subscribe((res: HttpResponse<QuantidadeTamanho>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: QuantidadeTamanho) {
        this.eventManager.broadcast({ name: 'quantidadeTamanhoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackModeloVestuarioById(index: number, item: ModeloVestuario) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-quantidade-tamanho-popup',
    template: ''
})
export class QuantidadeTamanhoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private quantidadeTamanhoPopupService: QuantidadeTamanhoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.quantidadeTamanhoPopupService
                    .open(QuantidadeTamanhoDialogComponent as Component, params['id']);
            } else {
                this.quantidadeTamanhoPopupService
                    .open(QuantidadeTamanhoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
