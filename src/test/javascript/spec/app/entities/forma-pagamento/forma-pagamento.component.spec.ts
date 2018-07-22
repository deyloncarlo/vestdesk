/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { FormaPagamentoComponent } from '../../../../../../main/webapp/app/entities/forma-pagamento/forma-pagamento.component';
import { FormaPagamentoService } from '../../../../../../main/webapp/app/entities/forma-pagamento/forma-pagamento.service';
import { FormaPagamento } from '../../../../../../main/webapp/app/entities/forma-pagamento/forma-pagamento.model';

describe('Component Tests', () => {

    describe('FormaPagamento Management Component', () => {
        let comp: FormaPagamentoComponent;
        let fixture: ComponentFixture<FormaPagamentoComponent>;
        let service: FormaPagamentoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [FormaPagamentoComponent],
                providers: [
                    FormaPagamentoService
                ]
            })
            .overrideTemplate(FormaPagamentoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FormaPagamentoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormaPagamentoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FormaPagamento(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.formaPagamentos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
