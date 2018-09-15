import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Cor } from './cor.model';
import { CorPopupService } from './cor-popup.service';
import { CorService } from './cor.service';

@Component({
    selector: 'jhi-cor-delete-dialog',
    templateUrl: './cor-delete-dialog.component.html'
})
export class CorDeleteDialogComponent {

    cor: Cor;

    constructor(
        private corService: CorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.corService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'corListModification',
                content: 'Deleted an cor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cor-delete-popup',
    template: ''
})
export class CorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private corPopupService: CorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.corPopupService
                .open(CorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
