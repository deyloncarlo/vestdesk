/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { MaterialTamanhoComponent } from '../../../../../../main/webapp/app/entities/material-tamanho/material-tamanho.component';
import { MaterialTamanhoService } from '../../../../../../main/webapp/app/entities/material-tamanho/material-tamanho.service';
import { MaterialTamanho } from '../../../../../../main/webapp/app/entities/material-tamanho/material-tamanho.model';

describe('Component Tests', () => {

    describe('MaterialTamanho Management Component', () => {
        let comp: MaterialTamanhoComponent;
        let fixture: ComponentFixture<MaterialTamanhoComponent>;
        let service: MaterialTamanhoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [MaterialTamanhoComponent],
                providers: [
                    MaterialTamanhoService
                ]
            })
            .overrideTemplate(MaterialTamanhoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterialTamanhoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterialTamanhoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MaterialTamanho(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.materialTamanhos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
