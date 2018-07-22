/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { QuantidadeTamanhoDetailComponent } from '../../../../../../main/webapp/app/entities/quantidade-tamanho/quantidade-tamanho-detail.component';
import { QuantidadeTamanhoService } from '../../../../../../main/webapp/app/entities/quantidade-tamanho/quantidade-tamanho.service';
import { QuantidadeTamanho } from '../../../../../../main/webapp/app/entities/quantidade-tamanho/quantidade-tamanho.model';

describe('Component Tests', () => {

    describe('QuantidadeTamanho Management Detail Component', () => {
        let comp: QuantidadeTamanhoDetailComponent;
        let fixture: ComponentFixture<QuantidadeTamanhoDetailComponent>;
        let service: QuantidadeTamanhoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [QuantidadeTamanhoDetailComponent],
                providers: [
                    QuantidadeTamanhoService
                ]
            })
            .overrideTemplate(QuantidadeTamanhoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuantidadeTamanhoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuantidadeTamanhoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new QuantidadeTamanho(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.quantidadeTamanho).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
