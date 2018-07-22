import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EtapaProducao } from './etapa-producao.model';
import { EtapaProducaoPopupService } from './etapa-producao-popup.service';
import { EtapaProducaoService } from './etapa-producao.service';

@Component({
    selector: 'jhi-etapa-producao-delete-dialog',
    templateUrl: './etapa-producao-delete-dialog.component.html'
})
export class EtapaProducaoDeleteDialogComponent {

    etapaProducao: EtapaProducao;

    constructor(
        private etapaProducaoService: EtapaProducaoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.etapaProducaoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'etapaProducaoListModification',
                content: 'Deleted an etapaProducao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-etapa-producao-delete-popup',
    template: ''
})
export class EtapaProducaoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private etapaProducaoPopupService: EtapaProducaoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.etapaProducaoPopupService
                .open(EtapaProducaoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
