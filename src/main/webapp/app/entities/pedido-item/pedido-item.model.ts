import { BaseEntity } from './../../shared';
import { Pedido } from '../pedido/pedido.model';
import { Produto } from '../produto';

export const enum FormaPagamento {
    'DINHEIRO',
    'DEBITO',
    'CREDITO',
    'TRANSFERENCIA',
    'BOLETO'
}

export class PedidoItem implements BaseEntity {
    constructor(
        public id?: number,
        public produto?: Produto,
        public pedido?: Pedido,
        public telefone?: string,
        public nomeRoupa?: string,
        public quantidade?: number,
        public observacao?: string,
        public clienteCamisa?: string,
        public primeiroPagamento?: number,
        public formaPrimeiroPagamento?: FormaPagamento,
        public valor?: number
    ) {
    }
}
