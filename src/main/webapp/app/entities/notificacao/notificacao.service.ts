import { Injectable } from "@angular/core";
import { SERVER_API_URL } from '../../app.constants';
import { HttpClient, HttpResponse } from "@angular/common/http";
import { Notificacao } from "./notificacao.model";
import { createRequestOption } from '../../shared';
import { Observable } from "rxjs/Observable";

export type EntityResponseType = HttpResponse<Notificacao>;

@Injectable()
export class NotificacaoService {

    private resourceUrl =  SERVER_API_URL + 'api/notificacaos';

    constructor(private http: HttpClient) { }

    query(req?: any): Observable<HttpResponse<Notificacao[]>> {
        const options = createRequestOption(req);
        return this.http.get<Notificacao[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Notificacao[]>) => this.convertArrayResponse(res));
    }

    setReadNotifications(listNotification: Notificacao[]): Observable<HttpResponse<Notificacao[]>> {
        const copy = this.convert(listNotification);
        return this.http.post<Notificacao[]>(this.resourceUrl + '/setReadNotifications', copy, { observe: 'response' })
            .map((res: HttpResponse<Notificacao[]>) => this.convertArrayResponse(res));
    }

    private convertArrayResponse(res: HttpResponse<Notificacao[]>): HttpResponse<Notificacao[]> {
        const jsonResponse: Notificacao[] = res.body;
        const body: Notificacao[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    private convertItemFromServer(notificacao: Notificacao): Notificacao {
        const copy: Notificacao = Object.assign({}, notificacao);
        return copy;
    }

    private convert(listNotification: Notificacao[]): Notificacao[] {
        let listConverted: Notificacao[] = [];
        listNotification.forEach((notification) => {
            const copy: Notificacao = Object.assign({}, notification);
            listConverted.push(copy);
        });
        return listConverted;
    }
}