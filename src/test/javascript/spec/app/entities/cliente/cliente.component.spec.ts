/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { ClienteComponent } from '../../../../../../main/webapp/app/entities/cliente/cliente.component';
import { ClienteService } from '../../../../../../main/webapp/app/entities/cliente/cliente.service';
import { Cliente } from '../../../../../../main/webapp/app/entities/cliente/cliente.model';

describe('Component Tests', () => {

    describe('Cliente Management Component', () => {
        let comp: ClienteComponent;
        let fixture: ComponentFixture<ClienteComponent>;
        let service: ClienteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ClienteComponent],
                providers: [
                    ClienteService
                ]
            })
            .overrideTemplate(ClienteComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClienteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClienteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Cliente(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.clientes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
