/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VestdeskTestModule } from '../../../test.module';
import { ConfiguracaoLayoutComponent } from '../../../../../../main/webapp/app/entities/configuracao-layout/configuracao-layout.component';
import { ConfiguracaoLayoutService } from '../../../../../../main/webapp/app/entities/configuracao-layout/configuracao-layout.service';
import { ConfiguracaoLayout } from '../../../../../../main/webapp/app/entities/configuracao-layout/configuracao-layout.model';

describe('Component Tests', () => {

    describe('ConfiguracaoLayout Management Component', () => {
        let comp: ConfiguracaoLayoutComponent;
        let fixture: ComponentFixture<ConfiguracaoLayoutComponent>;
        let service: ConfiguracaoLayoutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ConfiguracaoLayoutComponent],
                providers: [
                    ConfiguracaoLayoutService
                ]
            })
            .overrideTemplate(ConfiguracaoLayoutComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConfiguracaoLayoutComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfiguracaoLayoutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ConfiguracaoLayout(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.configuracaoLayouts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
