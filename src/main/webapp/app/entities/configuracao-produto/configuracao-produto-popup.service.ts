import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ConfiguracaoProduto } from './configuracao-produto.model';
import { ConfiguracaoProdutoService } from './configuracao-produto.service';

@Injectable()
export class ConfiguracaoProdutoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private configuracaoProdutoService: ConfiguracaoProdutoService

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
                this.configuracaoProdutoService.find(id)
                    .subscribe((configuracaoProdutoResponse: HttpResponse<ConfiguracaoProduto>) => {
                        const configuracaoProduto: ConfiguracaoProduto = configuracaoProdutoResponse.body;
                        this.ngbModalRef = this.configuracaoProdutoModalRef(component, configuracaoProduto);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.configuracaoProdutoModalRef(component, new ConfiguracaoProduto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    newOrUpdate(component: Component, p_configuracaoProduto?: ConfiguracaoProduto): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                this.ngbModalRef = null;
            }

            if (p_configuracaoProduto) {
                setTimeout(() => {
                    this.ngbModalRef = this.configuracaoProdutoModalRef(component, p_configuracaoProduto);
                    resolve(this.ngbModalRef);
                }, 0);
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.configuracaoProdutoModalRef(component, new ConfiguracaoProduto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    configuracaoProdutoModalRef(component: Component, configuracaoProduto: ConfiguracaoProduto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.configuracaoProduto = configuracaoProduto;
        return modalRef;
    }
}
