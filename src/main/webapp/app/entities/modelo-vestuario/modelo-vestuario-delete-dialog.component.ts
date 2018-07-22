import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ModeloVestuario } from './modelo-vestuario.model';
import { ModeloVestuarioPopupService } from './modelo-vestuario-popup.service';
import { ModeloVestuarioService } from './modelo-vestuario.service';

@Component({
    selector: 'jhi-modelo-vestuario-delete-dialog',
    templateUrl: './modelo-vestuario-delete-dialog.component.html'
})
export class ModeloVestuarioDeleteDialogComponent {

    modeloVestuario: ModeloVestuario;

    constructor(
        private modeloVestuarioService: ModeloVestuarioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.modeloVestuarioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'modeloVestuarioListModification',
                content: 'Deleted an modeloVestuario'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-modelo-vestuario-delete-popup',
    template: ''
})
export class ModeloVestuarioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modeloVestuarioPopupService: ModeloVestuarioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modeloVestuarioPopupService
                .open(ModeloVestuarioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
