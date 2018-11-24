import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { VendaAcumulada } from './venda-acumulada.model';
import { VendaAcumuladaService } from './venda-acumulada.service';

@Injectable()
export class VendaAcumuladaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private vendaAcumuladaService: VendaAcumuladaService

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
                this.vendaAcumuladaService.find(id)
                    .subscribe((vendaAcumuladaResponse: HttpResponse<VendaAcumulada>) => {
                        const vendaAcumulada: VendaAcumulada = vendaAcumuladaResponse.body;
                        this.ngbModalRef = this.vendaAcumuladaModalRef(component, vendaAcumulada);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.vendaAcumuladaModalRef(component, new VendaAcumulada());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    vendaAcumuladaModalRef(component: Component, vendaAcumulada: VendaAcumulada): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vendaAcumulada = vendaAcumulada;
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
