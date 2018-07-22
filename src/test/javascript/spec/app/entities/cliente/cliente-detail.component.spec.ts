/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { ClienteDetailComponent } from '../../../../../../main/webapp/app/entities/cliente/cliente-detail.component';
import { ClienteService } from '../../../../../../main/webapp/app/entities/cliente/cliente.service';
import { Cliente } from '../../../../../../main/webapp/app/entities/cliente/cliente.model';

describe('Component Tests', () => {

    describe('Cliente Management Detail Component', () => {
        let comp: ClienteDetailComponent;
        let fixture: ComponentFixture<ClienteDetailComponent>;
        let service: ClienteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ClienteDetailComponent],
                providers: [
                    ClienteService
                ]
            })
            .overrideTemplate(ClienteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClienteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClienteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Cliente(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.cliente).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
