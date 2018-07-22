/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { FormaPagamentoDetailComponent } from '../../../../../../main/webapp/app/entities/forma-pagamento/forma-pagamento-detail.component';
import { FormaPagamentoService } from '../../../../../../main/webapp/app/entities/forma-pagamento/forma-pagamento.service';
import { FormaPagamento } from '../../../../../../main/webapp/app/entities/forma-pagamento/forma-pagamento.model';

describe('Component Tests', () => {

    describe('FormaPagamento Management Detail Component', () => {
        let comp: FormaPagamentoDetailComponent;
        let fixture: ComponentFixture<FormaPagamentoDetailComponent>;
        let service: FormaPagamentoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [FormaPagamentoDetailComponent],
                providers: [
                    FormaPagamentoService
                ]
            })
            .overrideTemplate(FormaPagamentoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FormaPagamentoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormaPagamentoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new FormaPagamento(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.formaPagamento).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
