import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Layout } from './layout.model';
import { LayoutService } from './layout.service';

@Component({
    selector: 'jhi-layout-detail',
    templateUrl: './layout-detail.component.html'
})
export class LayoutDetailComponent implements OnInit, OnDestroy {

    layout: Layout;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private layoutService: LayoutService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLayouts();
    }

    load(id) {
        this.layoutService.find(id)
            .subscribe((layoutResponse: HttpResponse<Layout>) => {
                this.layout = layoutResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLayouts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'layoutListModification',
            (response) => this.load(this.layout.id)
        );
    }
}
