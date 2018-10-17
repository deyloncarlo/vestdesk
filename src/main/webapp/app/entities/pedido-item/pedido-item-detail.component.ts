import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PedidoItem } from './pedido-item.model';
import { PedidoItemService } from './pedido-item.service';

@Component({
    selector: 'jhi-pedido-item-detail',
    templateUrl: './pedido-item-detail.component.html'
})
export class PedidoItemDetailComponent implements OnInit, OnDestroy {

    pedidoItem: PedidoItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pedidoItemService: PedidoItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPedidoItems();
    }

    load(id) {
        this.pedidoItemService.find(id)
            .subscribe((pedidoItemResponse: HttpResponse<PedidoItem>) => {
                this.pedidoItem = pedidoItemResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPedidoItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pedidoItemListModification',
            (response) => this.load(this.pedidoItem.id)
        );
    }
}
