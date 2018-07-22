/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { LayoutComponent } from '../../../../../../main/webapp/app/entities/layout/layout.component';
import { LayoutService } from '../../../../../../main/webapp/app/entities/layout/layout.service';
import { Layout } from '../../../../../../main/webapp/app/entities/layout/layout.model';

describe('Component Tests', () => {

    describe('Layout Management Component', () => {
        let comp: LayoutComponent;
        let fixture: ComponentFixture<LayoutComponent>;
        let service: LayoutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [LayoutComponent],
                providers: [
                    LayoutService
                ]
            })
            .overrideTemplate(LayoutComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LayoutComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LayoutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Layout(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.layouts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
