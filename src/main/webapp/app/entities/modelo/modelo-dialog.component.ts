import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Modelo } from './modelo.model';
import { ModeloPopupService } from './modelo-popup.service';
import { ModeloService } from './modelo.service';

@Component({
    selector: 'jhi-modelo-dialog',
    templateUrl: './modelo-dialog.component.html'
})
export class ModeloDialogComponent implements OnInit {

    modelo: Modelo;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private modeloService: ModeloService,
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
        if (this.modelo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.modeloService.update(this.modelo));
        } else {
            this.subscribeToSaveResponse(
                this.modeloService.create(this.modelo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Modelo>>) {
        result.subscribe((res: HttpResponse<Modelo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Modelo) {
        this.eventManager.broadcast({ name: 'modeloListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-modelo-popup',
    template: ''
})
export class ModeloPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modeloPopupService: ModeloPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modeloPopupService
                    .open(ModeloDialogComponent as Component, params['id']);
            } else {
                this.modeloPopupService
                    .open(ModeloDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
