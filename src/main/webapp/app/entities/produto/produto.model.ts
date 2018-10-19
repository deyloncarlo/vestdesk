import { BaseEntity } from './../../shared';
import { ModeloVestuario } from '../modelo-vestuario';
import { Tamanho } from '../configuracao-produto';
import { Modelo } from '../modelo-vestuario';
import { Material } from '../material';
import { Cor } from '../cor';
import { MaterialTamanho } from '../material-tamanho';

export class Produto implements BaseEntity {
    constructor(
        public id?: number,
        public codigo?: string,
        public descricao?: string,
        public quantidadeInicial?: number,
        public totalEntrada?: number,
        public quantidadeMinima?: number,
        public totalSaida?: number,
        public quantidadeAtualizada?: number,
        public tamanho?: Tamanho,
        public modelo?: Modelo,
        public quantidadeEstoque?: number,
        public listaMaterial?: Material[],
        public listaMaterialTamanho?: MaterialTamanho[],
        public listaCor?: Cor[],
        public nomeRoupa?: string,
        public preco?: number
    ) {
    }
}
