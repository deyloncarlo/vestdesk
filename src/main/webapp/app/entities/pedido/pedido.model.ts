import { BaseEntity } from './../../shared';
import { Cliente } from '../cliente';
import { PedidoItem } from '../pedido-item';
import { Layout } from '../layout';
import { ConfiguracaoLayout } from '../configuracao-layout';

export const enum TipoPedido {
    'PRODUCAO' = 'PRODUCAO',
    'VENDA' = 'VENDA'
}

export const enum TipoEstampa {
    'BORDADO',
    'SILK'
}

export const enum StatusPedido {
    'CONCLUIDO' = 'CONCLUIDO',
    'ENVIADO' = 'ENVIADO',
    'FINALIZADO' = 'FINALIZADO',
    'RASCUNHO' = 'RASCUNHO',
    'EM_ANDAMENTO' = ' EM_ANDAMENTO'
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
        public dataPrevisao?: Date,
        public statusPedido?: any,
        public layout?: Layout,
        public listaConfiguracaoLayout?: ConfiguracaoLayout[],
        public nomeCliente?: string
    ) {
    }
}
