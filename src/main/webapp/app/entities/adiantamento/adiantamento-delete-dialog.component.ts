import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Adiantamento } from './adiantamento.model';
import { AdiantamentoPopupService } from './adiantamento-popup.service';
import { AdiantamentoService } from './adiantamento.service';

@Component({
    selector: 'jhi-adiantamento-delete-dialog',
    templateUrl: './adiantamento-delete-dialog.component.html'
})
export class AdiantamentoDeleteDialogComponent {

    adiantamento: Adiantamento;

    constructor(
        private adiantamentoService: AdiantamentoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.adiantamentoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'adiantamentoListModification',
                content: 'Deleted an adiantamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-adiantamento-delete-popup',
    template: ''
})
export class AdiantamentoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adiantamentoPopupService: AdiantamentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.adiantamentoPopupService
                .open(AdiantamentoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
