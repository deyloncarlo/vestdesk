/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { ConfiguracaoProdutoComponent } from '../../../../../../main/webapp/app/entities/configuracao-produto/configuracao-produto.component';
import { ConfiguracaoProdutoService } from '../../../../../../main/webapp/app/entities/configuracao-produto/configuracao-produto.service';
import { ConfiguracaoProduto } from '../../../../../../main/webapp/app/entities/configuracao-produto/configuracao-produto.model';

describe('Component Tests', () => {

    describe('ConfiguracaoProduto Management Component', () => {
        let comp: ConfiguracaoProdutoComponent;
        let fixture: ComponentFixture<ConfiguracaoProdutoComponent>;
        let service: ConfiguracaoProdutoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ConfiguracaoProdutoComponent],
                providers: [
                    ConfiguracaoProdutoService
                ]
            })
            .overrideTemplate(ConfiguracaoProdutoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConfiguracaoProdutoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfiguracaoProdutoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ConfiguracaoProduto(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.configuracaoProdutos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
