/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { MaterialDetailComponent } from '../../../../../../main/webapp/app/entities/material/material-detail.component';
import { MaterialService } from '../../../../../../main/webapp/app/entities/material/material.service';
import { Material } from '../../../../../../main/webapp/app/entities/material/material.model';

describe('Component Tests', () => {

    describe('Material Management Detail Component', () => {
        let comp: MaterialDetailComponent;
        let fixture: ComponentFixture<MaterialDetailComponent>;
        let service: MaterialService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [MaterialDetailComponent],
                providers: [
                    MaterialService
                ]
            })
            .overrideTemplate(MaterialDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterialDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterialService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Material(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.material).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
