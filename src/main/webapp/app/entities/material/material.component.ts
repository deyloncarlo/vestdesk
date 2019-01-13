import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Material } from './material.model';
import { MaterialService } from './material.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';

@Component({
    selector: 'jhi-material',
    templateUrl: './material.component.html'
})
export class MaterialComponent implements OnInit, OnDestroy {

    materials: Material[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    nome: string;

    constructor(
        private materialService: MaterialService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal
    ) {
        this.materials = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.materialService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: HttpResponse<Material[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    filtrar() {
        this.page = 0;
        this.materials = [];
        this.materialService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort(),
            nome: this.nome
        }).subscribe(
            (res: HttpResponse<Material[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    reset() {
        this.page = 0;
        this.materials = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMaterials();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Material) {
        return item.id;
    }
    registerChangeInMaterials() {
        this.eventSubscriber = this.eventManager.subscribe('materialListModification', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.materials.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
