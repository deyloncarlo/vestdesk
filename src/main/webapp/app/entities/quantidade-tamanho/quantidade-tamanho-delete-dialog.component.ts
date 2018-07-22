import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { QuantidadeTamanho } from './quantidade-tamanho.model';
import { QuantidadeTamanhoPopupService } from './quantidade-tamanho-popup.service';
import { QuantidadeTamanhoService } from './quantidade-tamanho.service';

@Component({
    selector: 'jhi-quantidade-tamanho-delete-dialog',
    templateUrl: './quantidade-tamanho-delete-dialog.component.html'
})
export class QuantidadeTamanhoDeleteDialogComponent {

    quantidadeTamanho: QuantidadeTamanho;

    constructor(
        private quantidadeTamanhoService: QuantidadeTamanhoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.quantidadeTamanhoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'quantidadeTamanhoListModification',
                content: 'Deleted an quantidadeTamanho'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-quantidade-tamanho-delete-popup',
    template: ''
})
export class QuantidadeTamanhoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private quantidadeTamanhoPopupService: QuantidadeTamanhoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.quantidadeTamanhoPopupService
                .open(QuantidadeTamanhoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
