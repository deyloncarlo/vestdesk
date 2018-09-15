import { BaseEntity } from './../../shared';

export class EtapaProducao implements BaseEntity {
    constructor(
        public id?: number,
        public oid?: number,
        public nome?: string,
        public prazoExecucao?: number,
    ) {
    }
}
