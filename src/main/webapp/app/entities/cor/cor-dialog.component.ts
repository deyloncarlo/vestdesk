import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Cor } from './cor.model';
import { CorPopupService } from './cor-popup.service';
import { CorService } from './cor.service';
import { Produto, ProdutoService } from '../produto';

@Component({
    selector: 'jhi-cor-dialog',
    templateUrl: './cor-dialog.component.html'
})
export class CorDialogComponent implements OnInit {

    cor: Cor;
    isSaving: boolean;

    produtos: Produto[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private corService: CorService,
        private produtoService: ProdutoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.produtoService.query()
            .subscribe((res: HttpResponse<Produto[]>) => { this.produtos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.corService.update(this.cor));
        } else {
            this.subscribeToSaveResponse(
                this.corService.create(this.cor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Cor>>) {
        result.subscribe((res: HttpResponse<Cor>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Cor) {
        this.eventManager.broadcast({ name: 'corListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProdutoById(index: number, item: Produto) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-cor-popup',
    template: ''
})
export class CorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private corPopupService: CorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.corPopupService
                    .open(CorDialogComponent as Component, params['id']);
            } else {
                this.corPopupService
                    .open(CorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
