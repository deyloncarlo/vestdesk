import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EtapaProducao } from './etapa-producao.model';
import { EtapaProducaoPopupService } from './etapa-producao-popup.service';
import { EtapaProducaoService } from './etapa-producao.service';

@Component({
    selector: 'jhi-etapa-producao-dialog',
    templateUrl: './etapa-producao-dialog.component.html'
})
export class EtapaProducaoDialogComponent implements OnInit {

    etapaProducao: EtapaProducao;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private etapaProducaoService: EtapaProducaoService,
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
        if (this.etapaProducao.id !== undefined) {
            this.subscribeToSaveResponse(
                this.etapaProducaoService.update(this.etapaProducao));
        } else {
            this.subscribeToSaveResponse(
                this.etapaProducaoService.create(this.etapaProducao));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EtapaProducao>>) {
        result.subscribe((res: HttpResponse<EtapaProducao>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EtapaProducao) {
        this.eventManager.broadcast({ name: 'etapaProducaoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-etapa-producao-popup',
    template: ''
})
export class EtapaProducaoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private etapaProducaoPopupService: EtapaProducaoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.etapaProducaoPopupService
                    .open(EtapaProducaoDialogComponent as Component, params['id']);
            } else {
                this.etapaProducaoPopupService
                    .open(EtapaProducaoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
