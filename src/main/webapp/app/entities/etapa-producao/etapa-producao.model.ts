import { BaseEntity } from './../../shared';

export class EtapaProducao implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public prazoExecucao?: number,
    ) {
    }
}
