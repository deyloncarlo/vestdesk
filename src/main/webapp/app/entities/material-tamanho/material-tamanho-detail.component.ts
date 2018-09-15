import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MaterialTamanho } from './material-tamanho.model';
import { MaterialTamanhoService } from './material-tamanho.service';

@Component({
    selector: 'jhi-material-tamanho-detail',
    templateUrl: './material-tamanho-detail.component.html'
})
export class MaterialTamanhoDetailComponent implements OnInit, OnDestroy {

    materialTamanho: MaterialTamanho;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private materialTamanhoService: MaterialTamanhoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMaterialTamanhos();
    }

    load(id) {
        this.materialTamanhoService.find(id)
            .subscribe((materialTamanhoResponse: HttpResponse<MaterialTamanho>) => {
                this.materialTamanho = materialTamanhoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMaterialTamanhos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'materialTamanhoListModification',
            (response) => this.load(this.materialTamanho.id)
        );
    }
}
