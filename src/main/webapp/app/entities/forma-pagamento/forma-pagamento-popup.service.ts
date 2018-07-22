import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { FormaPagamento } from './forma-pagamento.model';
import { FormaPagamentoService } from './forma-pagamento.service';

@Injectable()
export class FormaPagamentoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private formaPagamentoService: FormaPagamentoService

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
                this.formaPagamentoService.find(id)
                    .subscribe((formaPagamentoResponse: HttpResponse<FormaPagamento>) => {
                        const formaPagamento: FormaPagamento = formaPagamentoResponse.body;
                        this.ngbModalRef = this.formaPagamentoModalRef(component, formaPagamento);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.formaPagamentoModalRef(component, new FormaPagamento());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    formaPagamentoModalRef(component: Component, formaPagamento: FormaPagamento): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.formaPagamento = formaPagamento;
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
