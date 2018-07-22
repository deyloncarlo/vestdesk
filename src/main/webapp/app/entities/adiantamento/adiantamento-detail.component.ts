import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Adiantamento } from './adiantamento.model';
import { AdiantamentoService } from './adiantamento.service';

@Component({
    selector: 'jhi-adiantamento-detail',
    templateUrl: './adiantamento-detail.component.html'
})
export class AdiantamentoDetailComponent implements OnInit, OnDestroy {

    adiantamento: Adiantamento;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private adiantamentoService: AdiantamentoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAdiantamentos();
    }

    load(id) {
        this.adiantamentoService.find(id)
            .subscribe((adiantamentoResponse: HttpResponse<Adiantamento>) => {
                this.adiantamento = adiantamentoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAdiantamentos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'adiantamentoListModification',
            (response) => this.load(this.adiantamento.id)
        );
    }
}
