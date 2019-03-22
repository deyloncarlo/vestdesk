import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import locale from '@angular/common/locales/pt';

import {
    VestdeskSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    SelectField,
    TableResume,
    BoxInfo,
    FilterForm
} from './';

@NgModule({
    imports: [
        VestdeskSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        SelectField,
        BoxInfo,
        TableResume,
        FilterForm
    ],
    providers: [
        JhiLanguageHelper,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'pt'
        },
    ],
    exports: [
        VestdeskSharedLibsModule,
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent,
        SelectField,
        BoxInfo,
        TableResume,
        FilterForm
    ]
})
export class VestdeskSharedCommonModule {
    constructor() {
        registerLocaleData(locale);
    }
}
