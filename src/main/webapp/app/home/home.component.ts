import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { Account, LoginModalService, Principal } from '../shared';
import { PedidoService } from '../entities/pedido/pedido.service';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    quantidadePedidosStatusRascunho: number;
    quantidadePedidosSeraoFechados10Dias: number;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private pedidoService: PedidoService
    ) {
    }


    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            this.obterQuantidadePedidoStatusRascunho();
            this.obterQuantidadePedidoSeraoFechados10Dias();
        });
        this.registerAuthenticationSuccess();
    }
    
    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
                this.obterQuantidadePedidoStatusRascunho();
                this.obterQuantidadePedidoSeraoFechados10Dias();
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    obterQuantidadePedidoStatusRascunho() {
        this.pedidoService.obterQuantidadePedidoStatusRascunho().subscribe((quantidade: number) => {
            this.quantidadePedidosStatusRascunho = quantidade;
        });
    }

    obterQuantidadePedidoSeraoFechados10Dias() {
        this.pedidoService.obterQuantidadePedidoSeraoFechados10Dias().subscribe((quantidade: number) => {
            this.quantidadePedidosSeraoFechados10Dias = quantidade;
        });
    }
}
