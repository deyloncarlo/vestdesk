import { BaseEntity } from './../../shared';

export class QuantidadeTamanho implements BaseEntity {
    constructor(
        public id?: number,
        public tamanho?: string,
        public quantidadeMaterial?: number,
        public modeloVestuarioId?: number,
    ) {
    }
}
