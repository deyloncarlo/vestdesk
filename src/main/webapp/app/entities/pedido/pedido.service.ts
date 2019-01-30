import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Pedido } from './pedido.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Pedido>;

@Injectable()
export class PedidoService {

    private resourceUrl =  SERVER_API_URL + 'api/pedidos';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(pedido: Pedido): Observable<EntityResponseType> {
        const copy = this.convert(pedido);
        return this.http.post<Pedido>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pedido: Pedido): Observable<EntityResponseType> {
        const copy = this.convert(pedido);
        return this.http.put<Pedido>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Pedido>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Pedido[]>> {
        const options = createRequestOption(req);
        return this.http.get<Pedido[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Pedido[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    obterQuantidadePedidoStatusRascunho(): Observable<Number> {
        let url = this.resourceUrl + '/quantidadePedidoStatusRascunho';
        return this.http.get<Number>(url, {observe: 'response'}).map((res: any) => this.convertNumber(res));
    }
    obterQuantidadePedidoSeraoFechados10Dias(): Observable<Number> {
        let url = this.resourceUrl + '/obterQuantidadePedidoSeraoFechados10Dias';
        return this.http.get<Number>(url, {observe: 'response'}).map((res: any) => this.convertNumber(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Pedido = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Pedido[]>): HttpResponse<Pedido[]> {
        const jsonResponse: Pedido[] = res.body;
        const body: Pedido[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Pedido.
     */
    private convertItemFromServer(pedido: Pedido): Pedido {
        const copy: Pedido = Object.assign({}, pedido);
        copy.dataCriacao = this.dateUtils
            .convertLocalDateFromServer(pedido.dataCriacao);
        return copy;
    }

    /**
     * Convert a Pedido to a JSON which can be sent to the server.
     */
    private convert(pedido: Pedido): Pedido {
        const copy: Pedido = Object.assign({}, pedido);
        copy.dataCriacao = this.dateUtils
            .convertLocalDateToServer(pedido.dataCriacao);
        return copy;
    }

    private convertNumber(quantidade: any): Number {
        return quantidade.body;
    }

}
