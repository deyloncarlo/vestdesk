import { BaseEntity } from './../../shared';
import { Pedido } from '../pedido/pedido.model';
import { Produto } from '../produto';

export class PedidoItem implements BaseEntity {
    constructor(
        public id?: number,
        public produto?: Produto,
        public pedido?: Pedido,
        public telefone?: string,
        public nomeRoupa?: string
    ) {
    }
}
