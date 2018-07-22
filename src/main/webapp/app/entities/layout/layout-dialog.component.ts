import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Layout } from './layout.model';
import { LayoutPopupService } from './layout-popup.service';
import { LayoutService } from './layout.service';

@Component({
    selector: 'jhi-layout-dialog',
    templateUrl: './layout-dialog.component.html'
})
export class LayoutDialogComponent implements OnInit {

    layout: Layout;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private layoutService: LayoutService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.layout.id !== undefined) {
            this.subscribeToSaveResponse(
                this.layoutService.update(this.layout));
        } else {
            this.subscribeToSaveResponse(
                this.layoutService.create(this.layout));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Layout>>) {
        result.subscribe((res: HttpResponse<Layout>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Layout) {
        this.eventManager.broadcast({ name: 'layoutListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-layout-popup',
    template: ''
})
export class LayoutPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private layoutPopupService: LayoutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.layoutPopupService
                    .open(LayoutDialogComponent as Component, params['id']);
            } else {
                this.layoutPopupService
                    .open(LayoutDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
