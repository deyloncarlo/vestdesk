import { BaseEntity } from './../../shared';

export class Cliente implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public endereco?: string,
        public telefone?: string,
        public observacao?: string,
        public email?: string,
    ) {
    }
}
