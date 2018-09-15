import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ConfiguracaoProduto } from './configuracao-produto.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ConfiguracaoProduto>;

@Injectable()
export class ConfiguracaoProdutoService {

    private resourceUrl =  SERVER_API_URL + 'api/configuracao-produtos';

    constructor(private http: HttpClient) { }

    create(configuracaoProduto: ConfiguracaoProduto): Observable<EntityResponseType> {
        const copy = this.convert(configuracaoProduto);
        return this.http.post<ConfiguracaoProduto>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(configuracaoProduto: ConfiguracaoProduto): Observable<EntityResponseType> {
        const copy = this.convert(configuracaoProduto);
        return this.http.put<ConfiguracaoProduto>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ConfiguracaoProduto>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ConfiguracaoProduto[]>> {
        const options = createRequestOption(req);
        return this.http.get<ConfiguracaoProduto[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ConfiguracaoProduto[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ConfiguracaoProduto = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ConfiguracaoProduto[]>): HttpResponse<ConfiguracaoProduto[]> {
        const jsonResponse: ConfiguracaoProduto[] = res.body;
        const body: ConfiguracaoProduto[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ConfiguracaoProduto.
     */
    private convertItemFromServer(configuracaoProduto: ConfiguracaoProduto): ConfiguracaoProduto {
        const copy: ConfiguracaoProduto = Object.assign({}, configuracaoProduto);
        return copy;
    }

    /**
     * Convert a ConfiguracaoProduto to a JSON which can be sent to the server.
     */
    private convert(configuracaoProduto: ConfiguracaoProduto): ConfiguracaoProduto {
        const copy: ConfiguracaoProduto = Object.assign({}, configuracaoProduto);
        return copy;
    }
}
