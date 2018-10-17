import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PedidoItem } from './pedido-item.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PedidoItem>;

@Injectable()
export class PedidoItemService {

    private resourceUrl =  SERVER_API_URL + 'api/pedido-items';

    constructor(private http: HttpClient) { }

    create(pedidoItem: PedidoItem): Observable<EntityResponseType> {
        const copy = this.convert(pedidoItem);
        return this.http.post<PedidoItem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pedidoItem: PedidoItem): Observable<EntityResponseType> {
        const copy = this.convert(pedidoItem);
        return this.http.put<PedidoItem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PedidoItem>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PedidoItem[]>> {
        const options = createRequestOption(req);
        return this.http.get<PedidoItem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PedidoItem[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PedidoItem = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PedidoItem[]>): HttpResponse<PedidoItem[]> {
        const jsonResponse: PedidoItem[] = res.body;
        const body: PedidoItem[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PedidoItem.
     */
    private convertItemFromServer(pedidoItem: PedidoItem): PedidoItem {
        const copy: PedidoItem = Object.assign({}, pedidoItem);
        return copy;
    }

    /**
     * Convert a PedidoItem to a JSON which can be sent to the server.
     */
    private convert(pedidoItem: PedidoItem): PedidoItem {
        const copy: PedidoItem = Object.assign({}, pedidoItem);
        return copy;
    }
}
