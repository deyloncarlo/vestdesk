ALTER TABLE pedido_item ADD COLUMN aux DECIMAL(19,2);

UPDATE pedido_item SET aux=valor;

SELECT pedido_item.valor, produto.descricao, produto.preco FROM
 pedido_item INNER JOIN 
 produto ON 
 pedido_item.produto_id=produto.id;

UPDATE pedido_item
 INNER JOIN produto p ON 
 pedido_item.produto_id=p.id SET pedido_item.valor = p.preco;

UPDATE pedido_item SET valor=0 WHERE valor IS NULL;

ALTER TABLE pedido_item DROP COLUMN aux;