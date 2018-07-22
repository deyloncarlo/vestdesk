import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Layout } from './layout.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Layout>;

@Injectable()
export class LayoutService {

    private resourceUrl =  SERVER_API_URL + 'api/layouts';

    constructor(private http: HttpClient) { }

    create(layout: Layout): Observable<EntityResponseType> {
        const copy = this.convert(layout);
        return this.http.post<Layout>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(layout: Layout): Observable<EntityResponseType> {
        const copy = this.convert(layout);
        return this.http.put<Layout>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Layout>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Layout[]>> {
        const options = createRequestOption(req);
        return this.http.get<Layout[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Layout[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Layout = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Layout[]>): HttpResponse<Layout[]> {
        const jsonResponse: Layout[] = res.body;
        const body: Layout[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Layout.
     */
    private convertItemFromServer(layout: Layout): Layout {
        const copy: Layout = Object.assign({}, layout);
        return copy;
    }

    /**
     * Convert a Layout to a JSON which can be sent to the server.
     */
    private convert(layout: Layout): Layout {
        const copy: Layout = Object.assign({}, layout);
        return copy;
    }
}
