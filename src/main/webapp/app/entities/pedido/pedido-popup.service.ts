import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Pedido } from './pedido.model';
import { PedidoService } from './pedido.service';

@Injectable()
export class PedidoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pedidoService: PedidoService

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
                this.pedidoService.find(id)
                    .subscribe((pedidoResponse: HttpResponse<Pedido>) => {
                        const pedido: Pedido = pedidoResponse.body;
                        if (pedido.dataCriacao) {
                            pedido.dataCriacao = {
                                year: pedido.dataCriacao.getFullYear(),
                                month: pedido.dataCriacao.getMonth() + 1,
                                day: pedido.dataCriacao.getDate()
                            };
                        }
                        this.ngbModalRef = this.pedidoModalRef(component, pedido);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pedidoModalRef(component, new Pedido());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pedidoModalRef(component: Component, pedido: Pedido): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pedido = pedido;
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
