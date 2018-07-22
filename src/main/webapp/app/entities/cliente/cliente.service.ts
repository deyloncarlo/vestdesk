import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Cliente } from './cliente.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Cliente>;

@Injectable()
export class ClienteService {

    private resourceUrl =  SERVER_API_URL + 'api/clientes';

    constructor(private http: HttpClient) { }

    create(cliente: Cliente): Observable<EntityResponseType> {
        const copy = this.convert(cliente);
        return this.http.post<Cliente>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(cliente: Cliente): Observable<EntityResponseType> {
        const copy = this.convert(cliente);
        return this.http.put<Cliente>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Cliente>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Cliente[]>> {
        const options = createRequestOption(req);
        return this.http.get<Cliente[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Cliente[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Cliente = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Cliente[]>): HttpResponse<Cliente[]> {
        const jsonResponse: Cliente[] = res.body;
        const body: Cliente[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Cliente.
     */
    private convertItemFromServer(cliente: Cliente): Cliente {
        const copy: Cliente = Object.assign({}, cliente);
        return copy;
    }

    /**
     * Convert a Cliente to a JSON which can be sent to the server.
     */
    private convert(cliente: Cliente): Cliente {
        const copy: Cliente = Object.assign({}, cliente);
        return copy;
    }
}
