import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Material } from './material.model';
import { MaterialPopupService } from './material-popup.service';
import { MaterialService } from './material.service';

@Component({
    selector: 'jhi-material-delete-dialog',
    templateUrl: './material-delete-dialog.component.html'
})
export class MaterialDeleteDialogComponent {

    material: Material;

    constructor(
        private materialService: MaterialService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.materialService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'materialListModification',
                content: 'Deleted an material'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-material-delete-popup',
    template: ''
})
export class MaterialDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materialPopupService: MaterialPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.materialPopupService
                .open(MaterialDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
