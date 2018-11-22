import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { VestdeskSharedModule, UserRouteAccessService } from './shared';
import { VestdeskAppRoutingModule } from './app-routing.module';
import { VestdeskHomeModule } from './home/home.module';
import { VestdeskAdminModule } from './admin/admin.module';
import { VestdeskAccountModule } from './account/account.module';
import { VestdeskEntityModule } from './entities/entity.module';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { StateStorageService } from './shared/auth/state-storage.service';
import { ColorPickerModule } from 'ngx-color-picker';
import { LOCALE_ID } from '@angular/core';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        VestdeskAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        VestdeskSharedModule,
        VestdeskHomeModule,
        VestdeskAdminModule,
        VestdeskAccountModule,
        VestdeskEntityModule,
        ColorPickerModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        PaginationConfig,
        UserRouteAccessService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [
                StateStorageService,
                Injector
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [
                JhiEventManager
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        },
        {
            provide: LOCALE_ID,
            useValue: "pt-BR"
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class VestdeskAppModule {
    static forRoot(): VestdeskAppModule {
        return {
            ngModule: VestdeskAppModule
        };
    }
}
