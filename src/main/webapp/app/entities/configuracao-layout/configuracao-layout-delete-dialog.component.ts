import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ConfiguracaoLayout } from './configuracao-layout.model';
import { ConfiguracaoLayoutPopupService } from './configuracao-layout-popup.service';
import { ConfiguracaoLayoutService } from './configuracao-layout.service';

@Component({
    selector: 'jhi-configuracao-layout-delete-dialog',
    templateUrl: './configuracao-layout-delete-dialog.component.html'
})
export class ConfiguracaoLayoutDeleteDialogComponent {

    configuracaoLayout: ConfiguracaoLayout;

    constructor(
        private configuracaoLayoutService: ConfiguracaoLayoutService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.configuracaoLayoutService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'configuracaoLayoutListModification',
                content: 'Deleted an configuracaoLayout'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-configuracao-layout-delete-popup',
    template: ''
})
export class ConfiguracaoLayoutDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private configuracaoLayoutPopupService: ConfiguracaoLayoutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.configuracaoLayoutPopupService
                .open(ConfiguracaoLayoutDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
