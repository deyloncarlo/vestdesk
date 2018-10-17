import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VestdeskModeloModule } from './modelo/modelo.module';
import { VestdeskMaterialModule } from './material/material.module';
import { VestdeskQuantidadeTamanhoModule } from './quantidade-tamanho/quantidade-tamanho.module';
import { VestdeskModeloVestuarioModule } from './modelo-vestuario/modelo-vestuario.module';
import { VestdeskFormaPagamentoModule } from './forma-pagamento/forma-pagamento.module';
import { VestdeskClienteModule } from './cliente/cliente.module';
import { VestdeskEtapaProducaoModule } from './etapa-producao/etapa-producao.module';
import { VestdeskLayoutModule } from './layout/layout.module';
import { VestdeskProdutoModule } from './produto/produto.module';
import { VestdeskAdiantamentoModule } from './adiantamento/adiantamento.module';
import { VestdeskConfiguracaoProdutoModule } from './configuracao-produto/configuracao-produto.module';
import { VestdeskMaterialTamanhoModule } from './material-tamanho/material-tamanho.module';
import { VestdeskCorModule } from './cor/cor.module';
import { VestdeskUsuarioModule } from './usuario/usuario.module';
import { VestdeskPedidoModule } from './pedido/pedido.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        VestdeskModeloModule,
        VestdeskMaterialModule,
        VestdeskQuantidadeTamanhoModule,
        VestdeskModeloVestuarioModule,
        VestdeskFormaPagamentoModule,
        VestdeskClienteModule,
        VestdeskEtapaProducaoModule,
        VestdeskLayoutModule,
        VestdeskProdutoModule,
        VestdeskAdiantamentoModule,
        VestdeskConfiguracaoProdutoModule,
        VestdeskMaterialTamanhoModule,
        VestdeskCorModule,
        VestdeskUsuarioModule,
        VestdeskPedidoModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VestdeskEntityModule {}
