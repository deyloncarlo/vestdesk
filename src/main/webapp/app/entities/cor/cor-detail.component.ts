import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Cor } from './cor.model';
import { CorService } from './cor.service';

@Component({
    selector: 'jhi-cor-detail',
    templateUrl: './cor-detail.component.html'
})
export class CorDetailComponent implements OnInit, OnDestroy {

    cor: Cor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private corService: CorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCors();
    }

    load(id) {
        this.corService.find(id)
            .subscribe((corResponse: HttpResponse<Cor>) => {
                this.cor = corResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'corListModification',
            (response) => this.load(this.cor.id)
        );
    }
}
