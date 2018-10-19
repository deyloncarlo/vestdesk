import { BaseEntity } from './../../shared';
import { Cliente } from '../cliente';
import { PedidoItem } from '../pedido-item';

export const enum TipoPedido {
    'PRODUCAO',
    'VENDA'
}

export const enum TipoEstampa {
    'BORDADO',
    'SILK'
}

export class Pedido implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public tipoPedido?: TipoPedido,
        public tipoEstampaFrente?: TipoEstampa,
        public tipoEstampaCosta?: TipoEstampa,
        public tipoEstampaMangaDireita?: TipoEstampa,
        public tipoEstampaMangaEsquerda?: TipoEstampa,
        public dataCriacao?: any,
        public cliente?: Cliente,
        public nomeResponsavel?: string,
        public listaPedidoItem?: PedidoItem[],
        public dataPrevisao?: Date
    ) {
    }
}
