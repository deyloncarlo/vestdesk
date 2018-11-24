import { BaseEntity } from './../../shared';
import { Produto } from '../produto';
import { PedidoItem } from '../pedido-item';

export class VendaAcumulada implements BaseEntity {
    constructor(
        public id?: number,
        public quantidadeAcumulada?: number,
        public produto?: Produto,
        public listaPedidoItemAcumulado?: PedidoItem[],
        public listaPedidoItemProduzido?: PedidoItem[]
    ) {
    }
}
