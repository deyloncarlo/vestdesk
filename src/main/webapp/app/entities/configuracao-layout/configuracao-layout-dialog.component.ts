import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ConfiguracaoLayout } from './configuracao-layout.model';
import { ConfiguracaoLayoutPopupService } from './configuracao-layout-popup.service';
import { ConfiguracaoLayoutService } from './configuracao-layout.service';

@Component({
    selector: 'jhi-configuracao-layout-dialog',
    templateUrl: './configuracao-layout-dialog.component.html'
})
export class ConfiguracaoLayoutDialogComponent implements OnInit {

    configuracaoLayout: ConfiguracaoLayout;
    isSaving: boolean;
    dataCricaoDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private configuracaoLayoutService: ConfiguracaoLayoutService,
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
        if (this.configuracaoLayout.id !== undefined) {
            this.subscribeToSaveResponse(
                this.configuracaoLayoutService.update(this.configuracaoLayout));
        } else {
            this.subscribeToSaveResponse(
                this.configuracaoLayoutService.create(this.configuracaoLayout));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ConfiguracaoLayout>>) {
        result.subscribe((res: HttpResponse<ConfiguracaoLayout>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ConfiguracaoLayout) {
        this.eventManager.broadcast({ name: 'configuracaoLayoutListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-configuracao-layout-popup',
    template: ''
})
export class ConfiguracaoLayoutPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private configuracaoLayoutPopupService: ConfiguracaoLayoutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.configuracaoLayoutPopupService
                    .open(ConfiguracaoLayoutDialogComponent as Component, params['id']);
            } else {
                this.configuracaoLayoutPopupService
                    .open(ConfiguracaoLayoutDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
