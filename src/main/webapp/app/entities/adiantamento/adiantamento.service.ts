import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Adiantamento } from './adiantamento.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Adiantamento>;

@Injectable()
export class AdiantamentoService {

    private resourceUrl =  SERVER_API_URL + 'api/adiantamentos';

    constructor(private http: HttpClient) { }

    create(adiantamento: Adiantamento): Observable<EntityResponseType> {
        const copy = this.convert(adiantamento);
        return this.http.post<Adiantamento>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(adiantamento: Adiantamento): Observable<EntityResponseType> {
        const copy = this.convert(adiantamento);
        return this.http.put<Adiantamento>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Adiantamento>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Adiantamento[]>> {
        const options = createRequestOption(req);
        return this.http.get<Adiantamento[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Adiantamento[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Adiantamento = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Adiantamento[]>): HttpResponse<Adiantamento[]> {
        const jsonResponse: Adiantamento[] = res.body;
        const body: Adiantamento[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Adiantamento.
     */
    private convertItemFromServer(adiantamento: Adiantamento): Adiantamento {
        const copy: Adiantamento = Object.assign({}, adiantamento);
        return copy;
    }

    /**
     * Convert a Adiantamento to a JSON which can be sent to the server.
     */
    private convert(adiantamento: Adiantamento): Adiantamento {
        const copy: Adiantamento = Object.assign({}, adiantamento);
        return copy;
    }
}
