import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { FormaPagamento } from './forma-pagamento.model';
import { FormaPagamentoService } from './forma-pagamento.service';

@Component({
    selector: 'jhi-forma-pagamento-detail',
    templateUrl: './forma-pagamento-detail.component.html'
})
export class FormaPagamentoDetailComponent implements OnInit, OnDestroy {

    formaPagamento: FormaPagamento;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private formaPagamentoService: FormaPagamentoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFormaPagamentos();
    }

    load(id) {
        this.formaPagamentoService.find(id)
            .subscribe((formaPagamentoResponse: HttpResponse<FormaPagamento>) => {
                this.formaPagamento = formaPagamentoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFormaPagamentos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'formaPagamentoListModification',
            (response) => this.load(this.formaPagamento.id)
        );
    }
}
