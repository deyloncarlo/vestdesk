import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    UsuarioService,
    UsuarioPopupService,
    UsuarioComponent,
    UsuarioDetailComponent,
    UsuarioDialogComponent,
    UsuarioPopupComponent,
    UsuarioDeletePopupComponent,
    UsuarioDeleteDialogComponent,
    usuarioRoute,
    usuarioPopupRoute,
} from './';

const ENTITY_STATES = [
    ...usuarioRoute,
    ...usuarioPopupRoute,
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UsuarioComponent,
        UsuarioDetailComponent,
        UsuarioDialogComponent,
        UsuarioDeleteDialogComponent,
        UsuarioPopupComponent,
        UsuarioDeletePopupComponent,
    ],
    entryComponents: [
        UsuarioComponent,
        UsuarioDialogComponent,
        UsuarioPopupComponent,
        UsuarioDeleteDialogComponent,
        UsuarioDeletePopupComponent,
    ],
    providers: [
        UsuarioService,
        UsuarioPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskUsuarioModule {}
