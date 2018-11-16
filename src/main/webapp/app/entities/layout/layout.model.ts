import { BaseEntity } from './../../shared';
import { ConfiguracaoLayout } from '../configuracao-layout';

export class Layout implements BaseEntity {
    constructor(
        public id?: number,
        public oid?: number,
        public nome?: string,
        public imagemContentType?: string,
        public imagem?: any,
        public configuracaoLayout?: ConfiguracaoLayout
    ) {
    }
}
