import { BaseEntity } from './../../shared';
import { Produto } from '../produto';

export class ResumoEstoque implements BaseEntity {
    constructor(
        public id?: any,
        public quantidadeEstoque?: number,
        public produto?: Produto
    ) {
    }
}
