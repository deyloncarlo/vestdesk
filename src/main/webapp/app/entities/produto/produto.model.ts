import { BaseEntity } from './../../shared';

export class Produto implements BaseEntity {
    constructor(
        public id?: number,
        public cor?: string,
        public tamanho?: string,
        public modeloVestuarioId?: number,
    ) {
    }
}
