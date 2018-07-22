/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { LayoutDetailComponent } from '../../../../../../main/webapp/app/entities/layout/layout-detail.component';
import { LayoutService } from '../../../../../../main/webapp/app/entities/layout/layout.service';
import { Layout } from '../../../../../../main/webapp/app/entities/layout/layout.model';

describe('Component Tests', () => {

    describe('Layout Management Detail Component', () => {
        let comp: LayoutDetailComponent;
        let fixture: ComponentFixture<LayoutDetailComponent>;
        let service: LayoutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [LayoutDetailComponent],
                providers: [
                    LayoutService
                ]
            })
            .overrideTemplate(LayoutDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LayoutDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LayoutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Layout(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.layout).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
