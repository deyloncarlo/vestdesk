import { BaseEntity } from './../../shared';

export class MaterialTamanho implements BaseEntity {
    constructor(
        public id?: number,
        public quantidadeMaterial?: number,
        public configuracaoProdutoId?: number,
        public materialId?: number,
    ) {
    }
}
