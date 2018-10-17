import { BaseEntity } from './../../shared';

export class PedidoItem implements BaseEntity {
    constructor(
        public id?: number,
        public nomeRoupa?: string,
    ) {
    }
}
