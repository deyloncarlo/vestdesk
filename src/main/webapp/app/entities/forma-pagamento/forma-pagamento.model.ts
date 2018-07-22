import { BaseEntity } from './../../shared';

export class FormaPagamento implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
    ) {
    }
}
