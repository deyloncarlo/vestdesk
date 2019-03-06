import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ResumoEstoque } from './resumo-estoque.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ResumoEstoque>;

@Injectable()
export class ResumoEstoqueService {

    private resourceUrl =  SERVER_API_URL + 'api/venda-acumuladas';

    constructor(private http: HttpClient) { }

    query(req?: any): Observable<HttpResponse<ResumoEstoque[]>> {
        const options = createRequestOption(req);
        return this.http.get<ResumoEstoque[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ResumoEstoque[]>) => this.convertArrayResponse(res));
    }

    private convertArrayResponse(res: HttpResponse<ResumoEstoque[]>): HttpResponse<ResumoEstoque[]> {
        const jsonResponse: ResumoEstoque[] = res.body;
        const body: ResumoEstoque[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ResumoEstoque.
     */
    private convertItemFromServer(resumoEstoque: ResumoEstoque): ResumoEstoque {
        const copy: ResumoEstoque = Object.assign({}, resumoEstoque);
        return copy;
    }
}
