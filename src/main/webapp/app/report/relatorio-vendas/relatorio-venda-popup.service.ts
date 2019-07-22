import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { RelatorioVenda } from './relatorio-venda.model';
import { RelatorioVendaService } from './relatorio-venda.service';

@Injectable()
export class RelatorioVendaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private relatorioVendaService: RelatorioVendaService

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
                this.relatorioVendaService.find(id)
                    .subscribe((relatorioVendaResponse: HttpResponse<RelatorioVenda>) => {
                        const relatorioVenda: RelatorioVenda = relatorioVendaResponse.body;
                        this.ngbModalRef = this.relatorioVendaModalRef(component, relatorioVenda);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.relatorioVendaModalRef(component, new RelatorioVenda());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    relatorioVendaModalRef(component: Component, relatorioVenda: RelatorioVenda): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.relatorioVenda = relatorioVenda;
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
