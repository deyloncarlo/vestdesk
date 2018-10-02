import { BaseEntity } from './../../shared';
import { Cor } from './../cor/cor.model';

export const enum UnidadeMedida {
    'KG', 'UN'
}

export class Material implements BaseEntity {
    constructor(
        public id?: number,
        public codigo?: string,
        public nome?: string,
        public preco?: number,
        public quantidadeEstoque?: number,
        public quantidadeMinima?: number,
        public unidadeMedida?:  UnidadeMedida,
        public cor?: Cor,
        public corId?: number
    ) {
    }
}
