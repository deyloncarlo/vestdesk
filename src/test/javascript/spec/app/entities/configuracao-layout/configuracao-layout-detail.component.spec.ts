/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { VestdeskTestModule } from '../../../test.module';
import { ConfiguracaoLayoutDetailComponent } from '../../../../../../main/webapp/app/entities/configuracao-layout/configuracao-layout-detail.component';
import { ConfiguracaoLayoutService } from '../../../../../../main/webapp/app/entities/configuracao-layout/configuracao-layout.service';
import { ConfiguracaoLayout } from '../../../../../../main/webapp/app/entities/configuracao-layout/configuracao-layout.model';

describe('Component Tests', () => {

    describe('ConfiguracaoLayout Management Detail Component', () => {
        let comp: ConfiguracaoLayoutDetailComponent;
        let fixture: ComponentFixture<ConfiguracaoLayoutDetailComponent>;
        let service: ConfiguracaoLayoutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VestdeskTestModule],
                declarations: [ConfiguracaoLayoutDetailComponent],
                providers: [
                    ConfiguracaoLayoutService
                ]
            })
            .overrideTemplate(ConfiguracaoLayoutDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConfiguracaoLayoutDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfiguracaoLayoutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ConfiguracaoLayout(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.configuracaoLayout).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
