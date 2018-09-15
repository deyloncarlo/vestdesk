/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { CorDetailComponent } from '../../../../../../main/webapp/app/entities/cor/cor-detail.component';
import { CorService } from '../../../../../../main/webapp/app/entities/cor/cor.service';
import { Cor } from '../../../../../../main/webapp/app/entities/cor/cor.model';

describe('Component Tests', () => {

    describe('Cor Management Detail Component', () => {
        let comp: CorDetailComponent;
        let fixture: ComponentFixture<CorDetailComponent>;
        let service: CorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [CorDetailComponent],
                providers: [
                    CorService
                ]
            })
            .overrideTemplate(CorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Cor(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.cor).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
