import { BaseEntity } from './../../shared';

export class Cor implements BaseEntity {
    constructor(
        public id?: number,
        public oid?: number,
        public codigo?: string,
        public listaProdutos?: BaseEntity[],
    ) {
    }
}
