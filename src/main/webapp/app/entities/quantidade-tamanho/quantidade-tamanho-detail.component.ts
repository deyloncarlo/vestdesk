import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { QuantidadeTamanho } from './quantidade-tamanho.model';
import { QuantidadeTamanhoService } from './quantidade-tamanho.service';

@Component({
    selector: 'jhi-quantidade-tamanho-detail',
    templateUrl: './quantidade-tamanho-detail.component.html'
})
export class QuantidadeTamanhoDetailComponent implements OnInit, OnDestroy {

    quantidadeTamanho: QuantidadeTamanho;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private quantidadeTamanhoService: QuantidadeTamanhoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInQuantidadeTamanhos();
    }

    load(id) {
        this.quantidadeTamanhoService.find(id)
            .subscribe((quantidadeTamanhoResponse: HttpResponse<QuantidadeTamanho>) => {
                this.quantidadeTamanho = quantidadeTamanhoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInQuantidadeTamanhos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'quantidadeTamanhoListModification',
            (response) => this.load(this.quantidadeTamanho.id)
        );
    }
}
