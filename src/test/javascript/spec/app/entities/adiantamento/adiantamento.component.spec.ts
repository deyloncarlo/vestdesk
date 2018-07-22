/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { AdiantamentoComponent } from '../../../../../../main/webapp/app/entities/adiantamento/adiantamento.component';
import { AdiantamentoService } from '../../../../../../main/webapp/app/entities/adiantamento/adiantamento.service';
import { Adiantamento } from '../../../../../../main/webapp/app/entities/adiantamento/adiantamento.model';

describe('Component Tests', () => {

    describe('Adiantamento Management Component', () => {
        let comp: AdiantamentoComponent;
        let fixture: ComponentFixture<AdiantamentoComponent>;
        let service: AdiantamentoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [AdiantamentoComponent],
                providers: [
                    AdiantamentoService
                ]
            })
            .overrideTemplate(AdiantamentoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdiantamentoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdiantamentoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Adiantamento(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.adiantamentos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
