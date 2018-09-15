import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Usuario } from './usuario.model';
import { UsuarioPopupService } from './usuario-popup.service';
import { UsuarioService } from './usuario.service';

@Component({
    selector: 'jhi-usuario-delete-dialog',
    templateUrl: './usuario-delete-dialog.component.html'
})
export class UsuarioDeleteDialogComponent {

    usuario: Usuario;

    constructor(
        private usuarioService: UsuarioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.usuarioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'usuarioListModification',
                content: 'Deleted an usuario'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-usuario-delete-popup',
    template: ''
})
export class UsuarioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private usuarioPopupService: UsuarioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.usuarioPopupService
                .open(UsuarioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
