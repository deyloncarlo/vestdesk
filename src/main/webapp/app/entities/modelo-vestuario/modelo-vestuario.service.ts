import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ModeloVestuario } from './modelo-vestuario.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ModeloVestuario>;

@Injectable()
export class ModeloVestuarioService {

    private resourceUrl =  SERVER_API_URL + 'api/modelo-vestuarios';

    constructor(private http: HttpClient) { }

    create(modeloVestuario: ModeloVestuario): Observable<EntityResponseType> {
        const copy = this.convert(modeloVestuario);
        return this.http.post<ModeloVestuario>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(modeloVestuario: ModeloVestuario): Observable<EntityResponseType> {
        const copy = this.convert(modeloVestuario);
        return this.http.put<ModeloVestuario>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ModeloVestuario>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ModeloVestuario[]>> {
        const options = createRequestOption(req);
        return this.http.get<ModeloVestuario[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ModeloVestuario[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ModeloVestuario = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ModeloVestuario[]>): HttpResponse<ModeloVestuario[]> {
        const jsonResponse: ModeloVestuario[] = res.body;
        const body: ModeloVestuario[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ModeloVestuario.
     */
    private convertItemFromServer(modeloVestuario: ModeloVestuario): ModeloVestuario {
        const copy: ModeloVestuario = Object.assign({}, modeloVestuario);
        return copy;
    }

    /**
     * Convert a ModeloVestuario to a JSON which can be sent to the server.
     */
    private convert(modeloVestuario: ModeloVestuario): ModeloVestuario {
        const copy: ModeloVestuario = Object.assign({}, modeloVestuario);
        return copy;
    }
}
