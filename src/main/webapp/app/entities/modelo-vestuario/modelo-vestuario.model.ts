import { BaseEntity } from './../../shared';

export const enum Modelo {
    'MOLETOM',
    'POLO',
    'BLUSA'
}

export class ModeloVestuario implements BaseEntity {
    constructor(
        public id?: number,
        public oid?: number,
        public modelo?: Modelo,
        public listaConfiguracaoProdutos?: BaseEntity[],
    ) {
    }
}
