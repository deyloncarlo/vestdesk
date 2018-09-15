import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MaterialTamanho } from './material-tamanho.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MaterialTamanho>;

@Injectable()
export class MaterialTamanhoService {

    private resourceUrl =  SERVER_API_URL + 'api/material-tamanhos';

    constructor(private http: HttpClient) { }

    create(materialTamanho: MaterialTamanho): Observable<EntityResponseType> {
        const copy = this.convert(materialTamanho);
        return this.http.post<MaterialTamanho>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(materialTamanho: MaterialTamanho): Observable<EntityResponseType> {
        const copy = this.convert(materialTamanho);
        return this.http.put<MaterialTamanho>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MaterialTamanho>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MaterialTamanho[]>> {
        const options = createRequestOption(req);
        return this.http.get<MaterialTamanho[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MaterialTamanho[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MaterialTamanho = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MaterialTamanho[]>): HttpResponse<MaterialTamanho[]> {
        const jsonResponse: MaterialTamanho[] = res.body;
        const body: MaterialTamanho[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MaterialTamanho.
     */
    private convertItemFromServer(materialTamanho: MaterialTamanho): MaterialTamanho {
        const copy: MaterialTamanho = Object.assign({}, materialTamanho);
        return copy;
    }

    /**
     * Convert a MaterialTamanho to a JSON which can be sent to the server.
     */
    private convert(materialTamanho: MaterialTamanho): MaterialTamanho {
        const copy: MaterialTamanho = Object.assign({}, materialTamanho);
        return copy;
    }
}
