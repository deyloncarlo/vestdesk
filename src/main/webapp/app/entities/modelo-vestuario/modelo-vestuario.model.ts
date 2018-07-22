import { BaseEntity } from './../../shared';

export class ModeloVestuario implements BaseEntity {
    constructor(
        public id?: number,
        public preco?: number,
        public materialId?: number,
        public modeloId?: number,
    ) {
    }
}
