import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Material } from './material.model';
import { MaterialService } from './material.service';

@Component({
    selector: 'jhi-material-detail',
    templateUrl: './material-detail.component.html'
})
export class MaterialDetailComponent implements OnInit, OnDestroy {

    material: Material;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private materialService: MaterialService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMaterials();
    }

    load(id) {
        this.materialService.find(id)
            .subscribe((materialResponse: HttpResponse<Material>) => {
                this.material = materialResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMaterials() {
        this.eventSubscriber = this.eventManager.subscribe(
            'materialListModification',
            (response) => this.load(this.material.id)
        );
    }
}
