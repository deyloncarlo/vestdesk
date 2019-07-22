import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { RelatorioVenda } from './relatorio-venda.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RelatorioVenda>;

@Injectable()
export class RelatorioVendaService {

    private resourceUrl =  SERVER_API_URL + 'api/relatorio-venda';

    constructor(private http: HttpClient) { }

    create(relatorioVenda: RelatorioVenda): Observable<EntityResponseType> {
        const copy = this.convert(relatorioVenda);
        return this.http.post<RelatorioVenda>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(relatorioVenda: RelatorioVenda): Observable<EntityResponseType> {
        const copy = this.convert(relatorioVenda);
        return this.http.put<RelatorioVenda>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    produzir(relatorioVenda: RelatorioVenda): Observable<EntityResponseType> {
        const copy = this.convert(relatorioVenda);
        return this.http.post<RelatorioVenda>(this.resourceUrl + '/produzir', copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    concluir(relatorioVenda: RelatorioVenda): Observable<EntityResponseType> {
        const copy = this.convert(relatorioVenda);
        return this.http.post<RelatorioVenda>(this.resourceUrl + '/concluir', copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RelatorioVenda>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RelatorioVenda[]>> {
        const options = createRequestOption(req);
        return this.http.get<RelatorioVenda[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RelatorioVenda[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RelatorioVenda = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RelatorioVenda[]>): HttpResponse<RelatorioVenda[]> {
        const jsonResponse: RelatorioVenda[] = res.body;
        const body: RelatorioVenda[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RelatorioVenda.
     */
    private convertItemFromServer(relatorioVenda: RelatorioVenda): RelatorioVenda {
        const copy: RelatorioVenda = Object.assign({}, relatorioVenda);
        return copy;
    }

    /**
     * Convert a RelatorioVenda to a JSON which can be sent to the server.
     */
    private convert(relatorioVenda: RelatorioVenda): RelatorioVenda {
        const copy: RelatorioVenda = Object.assign({}, relatorioVenda);
        return copy;
    }
}
