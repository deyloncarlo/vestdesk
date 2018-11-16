import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ConfiguracaoLayout } from './configuracao-layout.model';
import { ConfiguracaoLayoutService } from './configuracao-layout.service';

@Component({
    selector: 'jhi-configuracao-layout-detail',
    templateUrl: './configuracao-layout-detail.component.html'
})
export class ConfiguracaoLayoutDetailComponent implements OnInit, OnDestroy {

    configuracaoLayout: ConfiguracaoLayout;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private configuracaoLayoutService: ConfiguracaoLayoutService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConfiguracaoLayouts();
    }

    load(id) {
        this.configuracaoLayoutService.find(id)
            .subscribe((configuracaoLayoutResponse: HttpResponse<ConfiguracaoLayout>) => {
                this.configuracaoLayout = configuracaoLayoutResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConfiguracaoLayouts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'configuracaoLayoutListModification',
            (response) => this.load(this.configuracaoLayout.id)
        );
    }
}
