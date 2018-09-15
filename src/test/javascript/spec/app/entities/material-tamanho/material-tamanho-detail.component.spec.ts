/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { MaterialTamanhoDetailComponent } from '../../../../../../main/webapp/app/entities/material-tamanho/material-tamanho-detail.component';
import { MaterialTamanhoService } from '../../../../../../main/webapp/app/entities/material-tamanho/material-tamanho.service';
import { MaterialTamanho } from '../../../../../../main/webapp/app/entities/material-tamanho/material-tamanho.model';

describe('Component Tests', () => {

    describe('MaterialTamanho Management Detail Component', () => {
        let comp: MaterialTamanhoDetailComponent;
        let fixture: ComponentFixture<MaterialTamanhoDetailComponent>;
        let service: MaterialTamanhoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [MaterialTamanhoDetailComponent],
                providers: [
                    MaterialTamanhoService
                ]
            })
            .overrideTemplate(MaterialTamanhoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterialTamanhoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterialTamanhoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MaterialTamanho(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.materialTamanho).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
