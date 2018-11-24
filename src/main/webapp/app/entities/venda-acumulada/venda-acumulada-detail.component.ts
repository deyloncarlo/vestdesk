import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { VendaAcumulada } from './venda-acumulada.model';
import { VendaAcumuladaService } from './venda-acumulada.service';

@Component({
    selector: 'jhi-venda-acumulada-detail',
    templateUrl: './venda-acumulada-detail.component.html'
})
export class VendaAcumuladaDetailComponent implements OnInit, OnDestroy {

    vendaAcumulada: VendaAcumulada;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private vendaAcumuladaService: VendaAcumuladaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVendaAcumuladas();
    }

    load(id) {
        this.vendaAcumuladaService.find(id)
            .subscribe((vendaAcumuladaResponse: HttpResponse<VendaAcumulada>) => {
                this.vendaAcumulada = vendaAcumuladaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVendaAcumuladas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'vendaAcumuladaListModification',
            (response) => this.load(this.vendaAcumulada.id)
        );
    }
}
