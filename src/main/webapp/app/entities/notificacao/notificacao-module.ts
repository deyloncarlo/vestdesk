import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    NotificacaoService
} from './';

@NgModule({
    imports: [
        VestdeskSharedModule,
    ],
    declarations: [
    ],
    entryComponents: [
    ],
    providers: [
        NotificacaoService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskNotificacaoModule {}
