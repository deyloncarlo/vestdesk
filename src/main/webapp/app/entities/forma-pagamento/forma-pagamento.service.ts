import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { FormaPagamento } from './forma-pagamento.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FormaPagamento>;

@Injectable()
export class FormaPagamentoService {

    private resourceUrl =  SERVER_API_URL + 'api/forma-pagamentos';

    constructor(private http: HttpClient) { }

    create(formaPagamento: FormaPagamento): Observable<EntityResponseType> {
        const copy = this.convert(formaPagamento);
        return this.http.post<FormaPagamento>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(formaPagamento: FormaPagamento): Observable<EntityResponseType> {
        const copy = this.convert(formaPagamento);
        return this.http.put<FormaPagamento>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FormaPagamento>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FormaPagamento[]>> {
        const options = createRequestOption(req);
        return this.http.get<FormaPagamento[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FormaPagamento[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FormaPagamento = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FormaPagamento[]>): HttpResponse<FormaPagamento[]> {
        const jsonResponse: FormaPagamento[] = res.body;
        const body: FormaPagamento[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FormaPagamento.
     */
    private convertItemFromServer(formaPagamento: FormaPagamento): FormaPagamento {
        const copy: FormaPagamento = Object.assign({}, formaPagamento);
        return copy;
    }

    /**
     * Convert a FormaPagamento to a JSON which can be sent to the server.
     */
    private convert(formaPagamento: FormaPagamento): FormaPagamento {
        const copy: FormaPagamento = Object.assign({}, formaPagamento);
        return copy;
    }
}
