import { BaseEntity } from './../../shared';

export class Adiantamento implements BaseEntity {
    constructor(
        public id?: number,
        public valor?: number,
        public formaPagementoId?: number,
    ) {
    }
}
