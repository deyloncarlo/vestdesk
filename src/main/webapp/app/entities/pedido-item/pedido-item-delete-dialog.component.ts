import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PedidoItem } from './pedido-item.model';
import { PedidoItemPopupService } from './pedido-item-popup.service';
import { PedidoItemService } from './pedido-item.service';

@Component({
    selector: 'jhi-pedido-item-delete-dialog',
    templateUrl: './pedido-item-delete-dialog.component.html'
})
export class PedidoItemDeleteDialogComponent {

    pedidoItem: PedidoItem;

    constructor(
        private pedidoItemService: PedidoItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pedidoItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pedidoItemListModification',
                content: 'Deleted an pedidoItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pedido-item-delete-popup',
    template: ''
})
export class PedidoItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pedidoItemPopupService: PedidoItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pedidoItemPopupService
                .open(PedidoItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
