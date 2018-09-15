import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MaterialTamanho } from './material-tamanho.model';
import { MaterialTamanhoPopupService } from './material-tamanho-popup.service';
import { MaterialTamanhoService } from './material-tamanho.service';

@Component({
    selector: 'jhi-material-tamanho-delete-dialog',
    templateUrl: './material-tamanho-delete-dialog.component.html'
})
export class MaterialTamanhoDeleteDialogComponent {

    materialTamanho: MaterialTamanho;

    constructor(
        private materialTamanhoService: MaterialTamanhoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.materialTamanhoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'materialTamanhoListModification',
                content: 'Deleted an materialTamanho'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-material-tamanho-delete-popup',
    template: ''
})
export class MaterialTamanhoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materialTamanhoPopupService: MaterialTamanhoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.materialTamanhoPopupService
                .open(MaterialTamanhoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
