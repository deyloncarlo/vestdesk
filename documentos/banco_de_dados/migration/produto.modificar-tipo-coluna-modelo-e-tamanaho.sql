alter table produto add column aux_modelo int(11);
update produto set aux_modelo = modelo;
alter table produto add column aux_tamanho int(11);
update produto set aux_tamanho = tamanho;

-- Modificanto tipo da coluna
alter table produto modify column modelo varchar(50);
alter table produto modify column tamanho varchar(10);

-- Atualizando valores
update produto set modelo="MOLETOM" where modelo="0";
update produto set modelo="POLO" where modelo="1";
update produto set modelo="CAMISA" where modelo="2";
update produto set modelo="COLLEGE" where modelo="3";

update produto set tamanho="P" where tamanho="0";
update produto set tamanho="PP" where tamanho="1";
update produto set tamanho="M" where tamanho="2";
update produto set tamanho="G" where tamanho="3";
update produto set tamanho="GG" where tamanho="4";
update produto set tamanho="XG" where tamanho="5";
update produto set tamanho="PPBL" where tamanho="6";
update produto set tamanho="PBL" where tamanho="7";
update produto set tamanho="MBL" where tamanho="8";
update produto set tamanho="GBL" where tamanho="9";
update produto set tamanho="GGBL" where tamanho="10";
update produto set tamanho="XGBL" where tamanho="11";