import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ConfiguracaoLayout } from './configuracao-layout.model';
import { ConfiguracaoLayoutService } from './configuracao-layout.service';

@Injectable()
export class ConfiguracaoLayoutPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private configuracaoLayoutService: ConfiguracaoLayoutService

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
                this.configuracaoLayoutService.find(id)
                    .subscribe((configuracaoLayoutResponse: HttpResponse<ConfiguracaoLayout>) => {
                        const configuracaoLayout: ConfiguracaoLayout = configuracaoLayoutResponse.body;
                        if (configuracaoLayout.dataCricao) {
                            configuracaoLayout.dataCricao = {
                                year: configuracaoLayout.dataCricao.getFullYear(),
                                month: configuracaoLayout.dataCricao.getMonth() + 1,
                                day: configuracaoLayout.dataCricao.getDate()
                            };
                        }
                        this.ngbModalRef = this.configuracaoLayoutModalRef(component, configuracaoLayout);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.configuracaoLayoutModalRef(component, new ConfiguracaoLayout());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    configuracaoLayoutModalRef(component: Component, configuracaoLayout: ConfiguracaoLayout): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.configuracaoLayout = configuracaoLayout;
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
