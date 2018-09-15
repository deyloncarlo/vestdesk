import { BaseEntity } from './../../shared';

export const enum Tamanho {
    'P',
    'M',
    'G'
}

export class ConfiguracaoProduto implements BaseEntity {
    constructor(
        public id?: number,
        public oid?: number,
        public tamanho?: Tamanho,
        public preco?: number,
        public listaMaterialTamanhos?: BaseEntity[],
        public modeloVestuarioId?: number,
    ) {
    }
}
