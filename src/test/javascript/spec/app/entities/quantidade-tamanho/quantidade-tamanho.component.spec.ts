/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { QuantidadeTamanhoComponent } from '../../../../../../main/webapp/app/entities/quantidade-tamanho/quantidade-tamanho.component';
import { QuantidadeTamanhoService } from '../../../../../../main/webapp/app/entities/quantidade-tamanho/quantidade-tamanho.service';
import { QuantidadeTamanho } from '../../../../../../main/webapp/app/entities/quantidade-tamanho/quantidade-tamanho.model';

describe('Component Tests', () => {

    describe('QuantidadeTamanho Management Component', () => {
        let comp: QuantidadeTamanhoComponent;
        let fixture: ComponentFixture<QuantidadeTamanhoComponent>;
        let service: QuantidadeTamanhoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [QuantidadeTamanhoComponent],
                providers: [
                    QuantidadeTamanhoService
                ]
            })
            .overrideTemplate(QuantidadeTamanhoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuantidadeTamanhoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuantidadeTamanhoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new QuantidadeTamanho(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.quantidadeTamanhos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
