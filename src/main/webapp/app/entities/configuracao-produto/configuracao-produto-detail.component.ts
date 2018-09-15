import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ConfiguracaoProduto } from './configuracao-produto.model';
import { ConfiguracaoProdutoService } from './configuracao-produto.service';

@Component({
    selector: 'jhi-configuracao-produto-detail',
    templateUrl: './configuracao-produto-detail.component.html'
})
export class ConfiguracaoProdutoDetailComponent implements OnInit, OnDestroy {

    configuracaoProduto: ConfiguracaoProduto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private configuracaoProdutoService: ConfiguracaoProdutoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConfiguracaoProdutos();
    }

    load(id) {
        this.configuracaoProdutoService.find(id)
            .subscribe((configuracaoProdutoResponse: HttpResponse<ConfiguracaoProduto>) => {
                this.configuracaoProduto = configuracaoProdutoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConfiguracaoProdutos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'configuracaoProdutoListModification',
            (response) => this.load(this.configuracaoProduto.id)
        );
    }
}
