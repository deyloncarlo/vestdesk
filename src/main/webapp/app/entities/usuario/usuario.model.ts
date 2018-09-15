import { BaseEntity } from './../../shared';

export const enum Perfil {
    'ADMINISTRADOR',
    'VENDEDOR'
}

export class Usuario implements BaseEntity {
    constructor(
        public id?: number,
        public oid?: number,
        public nome?: string,
        public sigla?: string,
        public email?: string,
        public senha?: string,
        public perfil?: Perfil,
    ) {
    }
}
