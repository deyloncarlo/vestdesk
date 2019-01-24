import { BaseEntity } from './../../shared';
import { ConfiguracaoProduto } from '../configuracao-produto/configuracao-produto.model';

export enum Modelo {
    'MOLETOM',
    'POLO',
    'CAMISA'
}

export class ModeloVestuario implements BaseEntity {
    constructor(
        public id?: number,
        public modelo?: Modelo,
        public nome?: String,
        public listaConfiguracaoProdutos?: ConfiguracaoProduto[],
    ) {
    }
}
