import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ConfiguracaoLayout } from './configuracao-layout.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ConfiguracaoLayout>;

@Injectable()
export class ConfiguracaoLayoutService {

    private resourceUrl =  SERVER_API_URL + 'api/configuracao-layouts';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(configuracaoLayout: ConfiguracaoLayout): Observable<EntityResponseType> {
        const copy = this.convert(configuracaoLayout);
        return this.http.post<ConfiguracaoLayout>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(configuracaoLayout: ConfiguracaoLayout): Observable<EntityResponseType> {
        const copy = this.convert(configuracaoLayout);
        return this.http.put<ConfiguracaoLayout>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ConfiguracaoLayout>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ConfiguracaoLayout[]>> {
        const options = createRequestOption(req);
        return this.http.get<ConfiguracaoLayout[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ConfiguracaoLayout[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ConfiguracaoLayout = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ConfiguracaoLayout[]>): HttpResponse<ConfiguracaoLayout[]> {
        const jsonResponse: ConfiguracaoLayout[] = res.body;
        const body: ConfiguracaoLayout[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ConfiguracaoLayout.
     */
    private convertItemFromServer(configuracaoLayout: ConfiguracaoLayout): ConfiguracaoLayout {
        const copy: ConfiguracaoLayout = Object.assign({}, configuracaoLayout);
        copy.dataCricao = this.dateUtils
            .convertLocalDateFromServer(configuracaoLayout.dataCricao);
        return copy;
    }

    /**
     * Convert a ConfiguracaoLayout to a JSON which can be sent to the server.
     */
    private convert(configuracaoLayout: ConfiguracaoLayout): ConfiguracaoLayout {
        const copy: ConfiguracaoLayout = Object.assign({}, configuracaoLayout);
        copy.dataCricao = this.dateUtils
            .convertLocalDateToServer(configuracaoLayout.dataCricao);
        return copy;
    }
}
