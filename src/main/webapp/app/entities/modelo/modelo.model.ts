import { BaseEntity } from './../../shared';

export class Modelo implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
    ) {
    }
}
