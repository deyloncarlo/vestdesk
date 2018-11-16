import { BaseEntity } from './../../shared';
import { Layout } from '../layout/layout.model';
import { TipoEstampa } from "../pedido";

export class ConfiguracaoLayout implements BaseEntity {
    constructor(
        public id?: number,
        public dataCricao?: any,
        public layout?: Layout,
        public tipoEstampaFrente?: TipoEstampa,
        public tipoEstampaCosta?: TipoEstampa,
        public tipoEstampaMangaDireita?: TipoEstampa,
        public tipoEstampaMangaEsquerda?: TipoEstampa
    ) {
    }
}
