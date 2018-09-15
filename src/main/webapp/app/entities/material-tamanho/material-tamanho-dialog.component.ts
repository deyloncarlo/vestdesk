import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MaterialTamanho } from './material-tamanho.model';
import { MaterialTamanhoPopupService } from './material-tamanho-popup.service';
import { MaterialTamanhoService } from './material-tamanho.service';
import { ConfiguracaoProduto, ConfiguracaoProdutoService } from '../configuracao-produto';
import { Material, MaterialService } from '../material';

@Component({
    selector: 'jhi-material-tamanho-dialog',
    templateUrl: './material-tamanho-dialog.component.html'
})
export class MaterialTamanhoDialogComponent implements OnInit {

    materialTamanho: MaterialTamanho;
    isSaving: boolean;

    configuracaoprodutos: ConfiguracaoProduto[];

    materials: Material[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private materialTamanhoService: MaterialTamanhoService,
        private configuracaoProdutoService: ConfiguracaoProdutoService,
        private materialService: MaterialService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.configuracaoProdutoService.query()
            .subscribe((res: HttpResponse<ConfiguracaoProduto[]>) => { this.configuracaoprodutos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.materialService.query()
            .subscribe((res: HttpResponse<Material[]>) => { this.materials = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.materialTamanho.id !== undefined) {
            this.subscribeToSaveResponse(
                this.materialTamanhoService.update(this.materialTamanho));
        } else {
            this.subscribeToSaveResponse(
                this.materialTamanhoService.create(this.materialTamanho));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MaterialTamanho>>) {
        result.subscribe((res: HttpResponse<MaterialTamanho>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MaterialTamanho) {
        this.eventManager.broadcast({ name: 'materialTamanhoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackConfiguracaoProdutoById(index: number, item: ConfiguracaoProduto) {
        return item.id;
    }

    trackMaterialById(index: number, item: Material) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-material-tamanho-popup',
    template: ''
})
export class MaterialTamanhoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materialTamanhoPopupService: MaterialTamanhoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.materialTamanhoPopupService
                    .open(MaterialTamanhoDialogComponent as Component, params['id']);
            } else {
                this.materialTamanhoPopupService
                    .open(MaterialTamanhoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
