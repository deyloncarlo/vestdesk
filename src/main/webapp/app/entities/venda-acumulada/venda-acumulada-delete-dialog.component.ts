import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VendaAcumulada } from './venda-acumulada.model';
import { VendaAcumuladaPopupService } from './venda-acumulada-popup.service';
import { VendaAcumuladaService } from './venda-acumulada.service';

@Component({
    selector: 'jhi-venda-acumulada-delete-dialog',
    templateUrl: './venda-acumulada-delete-dialog.component.html'
})
export class VendaAcumuladaDeleteDialogComponent {

    vendaAcumulada: VendaAcumulada;

    constructor(
        private vendaAcumuladaService: VendaAcumuladaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vendaAcumuladaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'vendaAcumuladaListModification',
                content: 'Deleted an vendaAcumulada'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-venda-acumulada-delete-popup',
    template: ''
})
export class VendaAcumuladaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vendaAcumuladaPopupService: VendaAcumuladaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.vendaAcumuladaPopupService
                .open(VendaAcumuladaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
