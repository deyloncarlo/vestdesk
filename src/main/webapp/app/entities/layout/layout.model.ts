import { BaseEntity } from './../../shared';

export class Layout implements BaseEntity {
    constructor(
        public id?: number,
        public oid?: number,
        public nome?: string,
        public imagemContentType?: string,
        public imagem?: any,
    ) {
    }
}
