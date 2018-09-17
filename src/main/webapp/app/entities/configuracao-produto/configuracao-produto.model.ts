import { BaseEntity } from './../../shared';
import { MaterialTamanho } from '../material-tamanho/material-tamanho.model';

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
        public listaMaterialTamanhos?: MaterialTamanho[],
        public modeloVestuarioId?: number,
    ) {
    }
}
