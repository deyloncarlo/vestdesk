import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Layout } from './layout.model';
import { LayoutPopupService } from './layout-popup.service';
import { LayoutService } from './layout.service';

@Component({
    selector: 'jhi-layout-delete-dialog',
    templateUrl: './layout-delete-dialog.component.html'
})
export class LayoutDeleteDialogComponent {

    layout: Layout;

    constructor(
        private layoutService: LayoutService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.layoutService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'layoutListModification',
                content: 'Deleted an layout'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-layout-delete-popup',
    template: ''
})
export class LayoutDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private layoutPopupService: LayoutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.layoutPopupService
                .open(LayoutDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
