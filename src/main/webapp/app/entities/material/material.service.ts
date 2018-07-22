import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Material } from './material.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Material>;

@Injectable()
export class MaterialService {

    private resourceUrl =  SERVER_API_URL + 'api/materials';

    constructor(private http: HttpClient) { }

    create(material: Material): Observable<EntityResponseType> {
        const copy = this.convert(material);
        return this.http.post<Material>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(material: Material): Observable<EntityResponseType> {
        const copy = this.convert(material);
        return this.http.put<Material>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Material>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Material[]>> {
        const options = createRequestOption(req);
        return this.http.get<Material[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Material[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Material = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Material[]>): HttpResponse<Material[]> {
        const jsonResponse: Material[] = res.body;
        const body: Material[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Material.
     */
    private convertItemFromServer(material: Material): Material {
        const copy: Material = Object.assign({}, material);
        return copy;
    }

    /**
     * Convert a Material to a JSON which can be sent to the server.
     */
    private convert(material: Material): Material {
        const copy: Material = Object.assign({}, material);
        return copy;
    }
}
