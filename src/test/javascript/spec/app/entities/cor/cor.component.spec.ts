/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { CorComponent } from '../../../../../../main/webapp/app/entities/cor/cor.component';
import { CorService } from '../../../../../../main/webapp/app/entities/cor/cor.service';
import { Cor } from '../../../../../../main/webapp/app/entities/cor/cor.model';

describe('Component Tests', () => {

    describe('Cor Management Component', () => {
        let comp: CorComponent;
        let fixture: ComponentFixture<CorComponent>;
        let service: CorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [CorComponent],
                providers: [
                    CorService
                ]
            })
            .overrideTemplate(CorComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Cor(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.cors[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
