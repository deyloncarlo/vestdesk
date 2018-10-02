import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Material } from './material.model';
import { MaterialPopupService } from './material-popup.service';
import { MaterialService } from './material.service';
import { Cor, CorService } from './../cor';

@Component({
    selector: 'jhi-material-dialog',
    templateUrl: './material-dialog.component.html'
})
export class MaterialDialogComponent implements OnInit {

    material: Material;
    isSaving: boolean;
    listaCor: Cor[];

    constructor(
        public activeModal: NgbActiveModal,
        private materialService: MaterialService,
        private eventManager: JhiEventManager,
        private corService: CorService,
        private jhiAlertService: JhiAlertService
    ) {
    }

    ngOnInit() {
        if (!this.material.cor || !this.material.cor.id) {
            this.material.cor = new Cor();
        }
        this.isSaving = false;
        this.corService.query()
            .subscribe((res: HttpResponse<Cor[]>) => { this.listaCor = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.material.id !== undefined) {
            this.subscribeToSaveResponse(
                this.materialService.update(this.material));
        } else {
            this.subscribeToSaveResponse(
                this.materialService.create(this.material));
        }
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Material>>) {
        result.subscribe((res: HttpResponse<Material>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Material) {
        this.eventManager.broadcast({ name: 'materialListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-material-popup',
    template: ''
})
export class MaterialPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materialPopupService: MaterialPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.materialPopupService
                    .open(MaterialDialogComponent as Component, params['id']);
            } else {
                this.materialPopupService
                    .open(MaterialDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
