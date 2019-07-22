import { BaseEntity } from '../../shared';
import { Produto } from '../../entities/produto';
import { PedidoItem } from '../../entities/pedido-item';

export class RelatorioVenda implements BaseEntity {
    constructor(
        public id?: number,
        public quantidadeAcumulada?: number,
        public produto?: Produto,
        public listaPedidoItemAcumulado?: PedidoItem[],
        public listaPedidoItemProduzido?: PedidoItem[]
    ) {
    }
}
