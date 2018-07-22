import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EtapaProducao } from './etapa-producao.model';
import { EtapaProducaoService } from './etapa-producao.service';

@Component({
    selector: 'jhi-etapa-producao-detail',
    templateUrl: './etapa-producao-detail.component.html'
})
export class EtapaProducaoDetailComponent implements OnInit, OnDestroy {

    etapaProducao: EtapaProducao;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private etapaProducaoService: EtapaProducaoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEtapaProducaos();
    }

    load(id) {
        this.etapaProducaoService.find(id)
            .subscribe((etapaProducaoResponse: HttpResponse<EtapaProducao>) => {
                this.etapaProducao = etapaProducaoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEtapaProducaos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'etapaProducaoListModification',
            (response) => this.load(this.etapaProducao.id)
        );
    }
}
