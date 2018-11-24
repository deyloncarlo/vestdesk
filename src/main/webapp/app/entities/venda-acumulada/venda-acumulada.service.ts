import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { VendaAcumulada } from './venda-acumulada.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VendaAcumulada>;

@Injectable()
export class VendaAcumuladaService {

    private resourceUrl =  SERVER_API_URL + 'api/venda-acumuladas';

    constructor(private http: HttpClient) { }

    create(vendaAcumulada: VendaAcumulada): Observable<EntityResponseType> {
        const copy = this.convert(vendaAcumulada);
        return this.http.post<VendaAcumulada>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(vendaAcumulada: VendaAcumulada): Observable<EntityResponseType> {
        const copy = this.convert(vendaAcumulada);
        return this.http.put<VendaAcumulada>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    produzir(vendaAcumulada: VendaAcumulada): Observable<EntityResponseType> {
        const copy = this.convert(vendaAcumulada);
        return this.http.post<VendaAcumulada>(this.resourceUrl + '/produzir', copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<VendaAcumulada>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VendaAcumulada[]>> {
        const options = createRequestOption(req);
        return this.http.get<VendaAcumulada[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<VendaAcumulada[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        debugger
        const body: VendaAcumulada = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VendaAcumulada[]>): HttpResponse<VendaAcumulada[]> {
        const jsonResponse: VendaAcumulada[] = res.body;
        const body: VendaAcumulada[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VendaAcumulada.
     */
    private convertItemFromServer(vendaAcumulada: VendaAcumulada): VendaAcumulada {
        const copy: VendaAcumulada = Object.assign({}, vendaAcumulada);
        return copy;
    }

    /**
     * Convert a VendaAcumulada to a JSON which can be sent to the server.
     */
    private convert(vendaAcumulada: VendaAcumulada): VendaAcumulada {
        const copy: VendaAcumulada = Object.assign({}, vendaAcumulada);
        return copy;
    }
}
