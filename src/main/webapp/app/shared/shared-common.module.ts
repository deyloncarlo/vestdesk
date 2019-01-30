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
    BoxInfo
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
        BoxInfo
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
        BoxInfo
    ]
})
export class VestdeskSharedCommonModule {
    constructor() {
        registerLocaleData(locale);
    }
}
