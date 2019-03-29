import { BaseEntity, User } from "../../shared";

export class Notificacao implements BaseEntity {
    constructor(
        public id?: number,
        public textoNotificacao?: string,
        public visualizado?: boolean,
        public usuario?: User,
        public dataCriacao?: Date
    ){}
}