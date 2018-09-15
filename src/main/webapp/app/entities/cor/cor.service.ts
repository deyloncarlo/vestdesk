import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Cor } from './cor.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Cor>;

@Injectable()
export class CorService {

    private resourceUrl =  SERVER_API_URL + 'api/cors';

    constructor(private http: HttpClient) { }

    create(cor: Cor): Observable<EntityResponseType> {
        const copy = this.convert(cor);
        return this.http.post<Cor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(cor: Cor): Observable<EntityResponseType> {
        const copy = this.convert(cor);
        return this.http.put<Cor>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Cor>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Cor[]>> {
        const options = createRequestOption(req);
        return this.http.get<Cor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Cor[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Cor = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Cor[]>): HttpResponse<Cor[]> {
        const jsonResponse: Cor[] = res.body;
        const body: Cor[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Cor.
     */
    private convertItemFromServer(cor: Cor): Cor {
        const copy: Cor = Object.assign({}, cor);
        return copy;
    }

    /**
     * Convert a Cor to a JSON which can be sent to the server.
     */
    private convert(cor: Cor): Cor {
        const copy: Cor = Object.assign({}, cor);
        return copy;
    }
}
