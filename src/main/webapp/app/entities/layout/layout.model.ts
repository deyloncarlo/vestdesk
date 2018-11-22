import { BaseEntity } from './../../shared';
import { ConfiguracaoLayout } from '../configuracao-layout';
import { Modelo } from '../modelo-vestuario';

export class Layout implements BaseEntity {
    constructor(
        public id?: number,
        public oid?: number,
        public nome?: string,
        public imagemContentType?: string,
        public imagem?: any,
        public configuracaoLayout?: ConfiguracaoLayout,
        public modelo?: Modelo
    ) {
    }
}
