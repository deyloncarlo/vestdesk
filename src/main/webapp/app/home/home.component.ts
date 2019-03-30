import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { Account, LoginModalService, Principal } from '../shared';
import { PedidoService } from '../entities/pedido/pedido.service';
import { NotificacaoService } from '../entities/notificacao/notificacao.service';
import { Notificacao } from '../entities/notificacao/notificacao.model';

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
    private listNotificacao: any[];

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private pedidoService: PedidoService,
        private notificacaoService: NotificacaoService
    ) {
    }


    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            this.obterQuantidadePedidoStatusRascunho();
            this.obterQuantidadePedidoSeraoFechados10Dias();
            this.getListNotificacao();
        });
        this.registerAuthenticationSuccess();
    }
    
    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
                this.obterQuantidadePedidoStatusRascunho();
                this.obterQuantidadePedidoSeraoFechados10Dias();
                this.getListNotificacao();
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

    getListNotificacao () {
        this.notificacaoService.query({sort: ["dataCriacao, desc"]}).subscribe((response) => {
            if (response.body != null && response.body != undefined) {
                this.listNotificacao = response.body;
            }else {
                this.listNotificacao = [];
            }
            setTimeout(this.getListNotificacao.bind(this), 3000);
        });
    }

    public onReadNotificacao (notification: Notificacao) {
        return this.notificacaoService.setReadNotifications(notification).subscribe((response) => {
        });
    }
}
