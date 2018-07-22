import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { QuantidadeTamanho } from './quantidade-tamanho.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<QuantidadeTamanho>;

@Injectable()
export class QuantidadeTamanhoService {

    private resourceUrl =  SERVER_API_URL + 'api/quantidade-tamanhos';

    constructor(private http: HttpClient) { }

    create(quantidadeTamanho: QuantidadeTamanho): Observable<EntityResponseType> {
        const copy = this.convert(quantidadeTamanho);
        return this.http.post<QuantidadeTamanho>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(quantidadeTamanho: QuantidadeTamanho): Observable<EntityResponseType> {
        const copy = this.convert(quantidadeTamanho);
        return this.http.put<QuantidadeTamanho>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<QuantidadeTamanho>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<QuantidadeTamanho[]>> {
        const options = createRequestOption(req);
        return this.http.get<QuantidadeTamanho[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<QuantidadeTamanho[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: QuantidadeTamanho = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<QuantidadeTamanho[]>): HttpResponse<QuantidadeTamanho[]> {
        const jsonResponse: QuantidadeTamanho[] = res.body;
        const body: QuantidadeTamanho[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to QuantidadeTamanho.
     */
    private convertItemFromServer(quantidadeTamanho: QuantidadeTamanho): QuantidadeTamanho {
        const copy: QuantidadeTamanho = Object.assign({}, quantidadeTamanho);
        return copy;
    }

    /**
     * Convert a QuantidadeTamanho to a JSON which can be sent to the server.
     */
    private convert(quantidadeTamanho: QuantidadeTamanho): QuantidadeTamanho {
        const copy: QuantidadeTamanho = Object.assign({}, quantidadeTamanho);
        return copy;
    }
}
