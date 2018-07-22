import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Cliente } from './cliente.model';
import { ClienteService } from './cliente.service';

@Component({
    selector: 'jhi-cliente-detail',
    templateUrl: './cliente-detail.component.html'
})
export class ClienteDetailComponent implements OnInit, OnDestroy {

    cliente: Cliente;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private clienteService: ClienteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClientes();
    }

    load(id) {
        this.clienteService.find(id)
            .subscribe((clienteResponse: HttpResponse<Cliente>) => {
                this.cliente = clienteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClientes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clienteListModification',
            (response) => this.load(this.cliente.id)
        );
    }
}
