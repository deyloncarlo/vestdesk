import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EtapaProducao } from './etapa-producao.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EtapaProducao>;

@Injectable()
export class EtapaProducaoService {

    private resourceUrl =  SERVER_API_URL + 'api/etapa-producaos';

    constructor(private http: HttpClient) { }

    create(etapaProducao: EtapaProducao): Observable<EntityResponseType> {
        const copy = this.convert(etapaProducao);
        return this.http.post<EtapaProducao>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(etapaProducao: EtapaProducao): Observable<EntityResponseType> {
        const copy = this.convert(etapaProducao);
        return this.http.put<EtapaProducao>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EtapaProducao>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EtapaProducao[]>> {
        const options = createRequestOption(req);
        return this.http.get<EtapaProducao[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EtapaProducao[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EtapaProducao = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EtapaProducao[]>): HttpResponse<EtapaProducao[]> {
        const jsonResponse: EtapaProducao[] = res.body;
        const body: EtapaProducao[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EtapaProducao.
     */
    private convertItemFromServer(etapaProducao: EtapaProducao): EtapaProducao {
        const copy: EtapaProducao = Object.assign({}, etapaProducao);
        return copy;
    }

    /**
     * Convert a EtapaProducao to a JSON which can be sent to the server.
     */
    private convert(etapaProducao: EtapaProducao): EtapaProducao {
        const copy: EtapaProducao = Object.assign({}, etapaProducao);
        return copy;
    }
}
