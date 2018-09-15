import { BaseEntity } from './../../shared';

export class Produto implements BaseEntity {
    constructor(
        public id?: number,
        public oid?: number,
        public quantidadeEstoque?: number,
        public descricao?: string,
        public configuracaoProdutoId?: number,
        public listaCors?: BaseEntity[],
    ) {
    }
}
