import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ConfiguracaoProduto } from './configuracao-produto.model';
import { ConfiguracaoProdutoPopupService } from './configuracao-produto-popup.service';
import { ConfiguracaoProdutoService } from './configuracao-produto.service';

@Component({
    selector: 'jhi-configuracao-produto-delete-dialog',
    templateUrl: './configuracao-produto-delete-dialog.component.html'
})
export class ConfiguracaoProdutoDeleteDialogComponent {

    configuracaoProduto: ConfiguracaoProduto;

    constructor(
        private configuracaoProdutoService: ConfiguracaoProdutoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.configuracaoProdutoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'configuracaoProdutoListModification',
                content: 'Deleted an configuracaoProduto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-configuracao-produto-delete-popup',
    template: ''
})
export class ConfiguracaoProdutoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private configuracaoProdutoPopupService: ConfiguracaoProdutoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.configuracaoProdutoPopupService
                .open(ConfiguracaoProdutoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
