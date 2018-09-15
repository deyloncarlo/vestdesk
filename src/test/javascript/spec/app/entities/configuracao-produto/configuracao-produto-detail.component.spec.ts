/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { ConfiguracaoProdutoDetailComponent } from '../../../../../../main/webapp/app/entities/configuracao-produto/configuracao-produto-detail.component';
import { ConfiguracaoProdutoService } from '../../../../../../main/webapp/app/entities/configuracao-produto/configuracao-produto.service';
import { ConfiguracaoProduto } from '../../../../../../main/webapp/app/entities/configuracao-produto/configuracao-produto.model';

describe('Component Tests', () => {

    describe('ConfiguracaoProduto Management Detail Component', () => {
        let comp: ConfiguracaoProdutoDetailComponent;
        let fixture: ComponentFixture<ConfiguracaoProdutoDetailComponent>;
        let service: ConfiguracaoProdutoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ConfiguracaoProdutoDetailComponent],
                providers: [
                    ConfiguracaoProdutoService
                ]
            })
            .overrideTemplate(ConfiguracaoProdutoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConfiguracaoProdutoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfiguracaoProdutoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ConfiguracaoProduto(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.configuracaoProduto).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
