import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { ProfileService } from '../profiles/profile.service';
import { JhiLanguageHelper, Principal, LoginModalService, LoginService, User } from '../../shared';

import { VERSION } from '../../app.constants';
import { NotificacaoService } from '../../entities/notificacao/notificacao.service';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.scss'
    ]
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    amountUnreadNotification: Number;
    loginName: string;


    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router,
        private notificacaoService: NotificacaoService
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });

        this.getAmountUnreadNotification();
        this.getUserInfo();
    }

    changeLanguage(languageKey: string) {
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }

    getAmountUnreadNotification() {
        if (this.isAuthenticated()) {
            this.notificacaoService.getAmountOfUnreadNotifications().subscribe((response) => {
                if (response != null) {
                    this.amountUnreadNotification = response;
                }
                setTimeout(this.getAmountUnreadNotification.bind(this), 3000);
            });
        } else {
            setTimeout(this.getAmountUnreadNotification.bind(this), 1000);
        }
    }

    getUserInfo() {
        if (this.loginName == null || this.loginName == undefined) {
            setTimeout(() => {
                if (this.principal.isAuthenticated()) {
                    this.principal.identity().then((response) => {
                        this.loginName = response.login;
                    });
                } else {
                    this.getUserInfo();
                }
            });
        }
    }
}
