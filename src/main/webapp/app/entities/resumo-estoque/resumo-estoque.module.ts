import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VestdeskSharedModule } from '../../shared';
import {
    ResumoEstoqueService,
    ResumoEstoqueComponent,
    resumoEstoqueRoute
} from './';

const ENTITY_STATES = [
    ...resumoEstoqueRoute
];

@NgModule({
    imports: [
        VestdeskSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ResumoEstoqueComponent
    ],
    entryComponents: [
        ResumoEstoqueComponent
    ],
    providers: [
        ResumoEstoqueService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskResumoEstoqueModule {}
