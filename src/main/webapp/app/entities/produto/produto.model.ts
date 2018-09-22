import { BaseEntity } from './../../shared';
import { ModeloVestuario } from '../modelo-vestuario';

export class Produto implements BaseEntity {
    constructor(
        public id?: number,
        public oid?: number,
        public quantidadeEstoque?: number,
        public descricao?: string,
        public configuracaoProdutoId?: number,
        public listaCors?: BaseEntity[],
        public modeloVestuario?: ModeloVestuario
    ) {
    }
}
