/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { MaterialComponent } from '../../../../../../main/webapp/app/entities/material/material.component';
import { MaterialService } from '../../../../../../main/webapp/app/entities/material/material.service';
import { Material } from '../../../../../../main/webapp/app/entities/material/material.model';

describe('Component Tests', () => {

    describe('Material Management Component', () => {
        let comp: MaterialComponent;
        let fixture: ComponentFixture<MaterialComponent>;
        let service: MaterialService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [MaterialComponent],
                providers: [
                    MaterialService
                ]
            })
            .overrideTemplate(MaterialComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterialComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterialService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Material(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.materials[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
