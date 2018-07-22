import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ModeloVestuario } from './modelo-vestuario.model';
import { ModeloVestuarioService } from './modelo-vestuario.service';

@Component({
    selector: 'jhi-modelo-vestuario-detail',
    templateUrl: './modelo-vestuario-detail.component.html'
})
export class ModeloVestuarioDetailComponent implements OnInit, OnDestroy {

    modeloVestuario: ModeloVestuario;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private modeloVestuarioService: ModeloVestuarioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInModeloVestuarios();
    }

    load(id) {
        this.modeloVestuarioService.find(id)
            .subscribe((modeloVestuarioResponse: HttpResponse<ModeloVestuario>) => {
                this.modeloVestuario = modeloVestuarioResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInModeloVestuarios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'modeloVestuarioListModification',
            (response) => this.load(this.modeloVestuario.id)
        );
    }
}
