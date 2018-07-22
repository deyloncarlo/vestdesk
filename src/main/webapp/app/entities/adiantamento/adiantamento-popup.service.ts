import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Adiantamento } from './adiantamento.model';
import { AdiantamentoService } from './adiantamento.service';

@Injectable()
export class AdiantamentoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private adiantamentoService: AdiantamentoService

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
                this.adiantamentoService.find(id)
                    .subscribe((adiantamentoResponse: HttpResponse<Adiantamento>) => {
                        const adiantamento: Adiantamento = adiantamentoResponse.body;
                        this.ngbModalRef = this.adiantamentoModalRef(component, adiantamento);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.adiantamentoModalRef(component, new Adiantamento());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    adiantamentoModalRef(component: Component, adiantamento: Adiantamento): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.adiantamento = adiantamento;
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
