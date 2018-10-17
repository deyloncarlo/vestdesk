import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PedidoItem } from './pedido-item.model';
import { PedidoItemService } from './pedido-item.service';

@Injectable()
export class PedidoItemPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pedidoItemService: PedidoItemService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.pedidoItemService.find(id)
                    .subscribe((pedidoItemResponse: HttpResponse<PedidoItem>) => {
                        const pedidoItem: PedidoItem = pedidoItemResponse.body;
                        this.ngbModalRef = this.pedidoItemModalRef(component, pedidoItem);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pedidoItemModalRef(component, new PedidoItem());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pedidoItemModalRef(component: Component, pedidoItem: PedidoItem): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pedidoItem = pedidoItem;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
