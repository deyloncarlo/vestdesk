import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ModeloVestuario } from './modelo-vestuario.model';
import { ModeloVestuarioService } from './modelo-vestuario.service';

@Injectable()
export class ModeloVestuarioPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private modeloVestuarioService: ModeloVestuarioService

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
                this.modeloVestuarioService.find(id)
                    .subscribe((modeloVestuarioResponse: HttpResponse<ModeloVestuario>) => {
                        const modeloVestuario: ModeloVestuario = modeloVestuarioResponse.body;
                        this.ngbModalRef = this.modeloVestuarioModalRef(component, modeloVestuario);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.modeloVestuarioModalRef(component, new ModeloVestuario());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    modeloVestuarioModalRef(component: Component, modeloVestuario: ModeloVestuario): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: false});
        modalRef.componentInstance.modeloVestuario = modeloVestuario;
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
