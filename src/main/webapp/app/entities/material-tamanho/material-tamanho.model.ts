import { BaseEntity } from './../../shared';
import { Produto } from '../produto';

export class MaterialTamanho implements BaseEntity {
    constructor(
        public id?: number,
        public quantidadeMaterial?: number,
        public produto?: Produto,
        public materialId?: number,
    ) {
    }
}
