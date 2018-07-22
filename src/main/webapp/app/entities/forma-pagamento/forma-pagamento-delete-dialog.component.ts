import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FormaPagamento } from './forma-pagamento.model';
import { FormaPagamentoPopupService } from './forma-pagamento-popup.service';
import { FormaPagamentoService } from './forma-pagamento.service';

@Component({
    selector: 'jhi-forma-pagamento-delete-dialog',
    templateUrl: './forma-pagamento-delete-dialog.component.html'
})
export class FormaPagamentoDeleteDialogComponent {

    formaPagamento: FormaPagamento;

    constructor(
        private formaPagamentoService: FormaPagamentoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.formaPagamentoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'formaPagamentoListModification',
                content: 'Deleted an formaPagamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-forma-pagamento-delete-popup',
    template: ''
})
export class FormaPagamentoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private formaPagamentoPopupService: FormaPagamentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.formaPagamentoPopupService
                .open(FormaPagamentoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
