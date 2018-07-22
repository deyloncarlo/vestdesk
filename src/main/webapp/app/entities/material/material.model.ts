import { BaseEntity } from './../../shared';

export class Material implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public preco?: number,
        public quantidadeMinima?: number,
        public quantidadeMaxima?: number,
    ) {
    }
}
