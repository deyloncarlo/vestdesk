import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PedidoItem } from './pedido-item.model';
import { PedidoItemPopupService } from './pedido-item-popup.service';
import { PedidoItemService } from './pedido-item.service';

@Component({
    selector: 'jhi-pedido-item-dialog',
    templateUrl: './pedido-item-dialog.component.html'
})
export class PedidoItemDialogComponent implements OnInit {

    pedidoItem: PedidoItem;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private pedidoItemService: PedidoItemService,
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
        if (this.pedidoItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pedidoItemService.update(this.pedidoItem));
        } else {
            this.subscribeToSaveResponse(
                this.pedidoItemService.create(this.pedidoItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PedidoItem>>) {
        result.subscribe((res: HttpResponse<PedidoItem>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PedidoItem) {
        this.eventManager.broadcast({ name: 'pedidoItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-pedido-item-popup',
    template: ''
})
export class PedidoItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pedidoItemPopupService: PedidoItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pedidoItemPopupService
                    .open(PedidoItemDialogComponent as Component, params['id']);
            } else {
                this.pedidoItemPopupService
                    .open(PedidoItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
