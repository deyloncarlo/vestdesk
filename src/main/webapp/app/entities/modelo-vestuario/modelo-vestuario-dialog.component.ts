import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ModeloVestuario } from './modelo-vestuario.model';
import { ModeloVestuarioPopupService } from './modelo-vestuario-popup.service';
import { ModeloVestuarioService } from './modelo-vestuario.service';
import { Material, MaterialService } from '../material';
import { Modelo, ModeloService } from '../modelo';

@Component({
    selector: 'jhi-modelo-vestuario-dialog',
    templateUrl: './modelo-vestuario-dialog.component.html'
})
export class ModeloVestuarioDialogComponent implements OnInit {

    modeloVestuario: ModeloVestuario;
    isSaving: boolean;

    materials: Material[];

    modelos: Modelo[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private modeloVestuarioService: ModeloVestuarioService,
        private materialService: MaterialService,
        private modeloService: ModeloService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.materialService.query()
            .subscribe((res: HttpResponse<Material[]>) => { this.materials = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.modeloService.query()
            .subscribe((res: HttpResponse<Modelo[]>) => { this.modelos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.modeloVestuario.id !== undefined) {
            this.subscribeToSaveResponse(
                this.modeloVestuarioService.update(this.modeloVestuario));
        } else {
            this.subscribeToSaveResponse(
                this.modeloVestuarioService.create(this.modeloVestuario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ModeloVestuario>>) {
        result.subscribe((res: HttpResponse<ModeloVestuario>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ModeloVestuario) {
        this.eventManager.broadcast({ name: 'modeloVestuarioListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMaterialById(index: number, item: Material) {
        return item.id;
    }

    trackModeloById(index: number, item: Modelo) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-modelo-vestuario-popup',
    template: ''
})
export class ModeloVestuarioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modeloVestuarioPopupService: ModeloVestuarioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modeloVestuarioPopupService
                    .open(ModeloVestuarioDialogComponent as Component, params['id']);
            } else {
                this.modeloVestuarioPopupService
                    .open(ModeloVestuarioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
