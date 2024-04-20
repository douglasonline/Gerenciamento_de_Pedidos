# Gerenciamento de pedidos

# 📑 Sobre o projeto

Esse projeto é uma API em Java com Spring Boot que implementa um sistema de gerenciamento de pedidos para um restaurante.  

# O projeto segue os seguintes padrões

- Criar, listar, atualizar e deletar produtos
- Cada produto tem um nome, preço e uma categoria (bebida, entrada, prato principal, sobremesa)
- Criar e listar pedidos
- Cada pedido contem um ou mais produtos e o valor total do pedido
- É tem a funcionalidade de adicionar produtos a um pedido
- Validações para garantir a integridade dos dados
- Responda com mensagens de erro claras e status HTTP apropriados
- Todos os endpoints da API com Swagger
- Endpoints com Paginação 
- Registro de logs
- Dockerização da aplicação
- Autenticação com JWT para proteger as rotas da API
 

# 🛠️ Tecnologias utilizadas
## Back end
- Banco de dados PostgreSQL
- Java 11+
- Spring Boot
- Maven 
- JPA
- Swagger
- Docker 

# 🐘 Banco de Dados Utilizado
- PostgreSQL

# ▶️ Como executar o projeto

## Back end
Pré-requisitos: PostgreSQL, Java, Maven 

```bash
# Clonar repositório

```
- Para iniciar o projeto deve-se configurar o Banco de Dados PostgreSQL no application.properties passando o usuário (username) e a senha (password) conforme mostra na imagem abaixo:

![Configurar Banco De Dados Pedido](https://github.com/douglasonline/Imagens/blob/master/Configurar_Banco_De_Dados_Pedido.png) 

```bash

# Após essas configurações do Banco de Dados pode-se executar o projeto que o Database e as tabelas serão criados automaticamentes 

```

# 📘 Como acessar o Swagger do Back end 

- Coloque no navegador o endereço: http://localhost:8080/swagger-ui/index.html#/

## Com o Swagger podemos ver as requisições que podemos realizar 

![Requisicoes de Pedidos Parte1](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_de_Pedidos_Parte1.png) 

![Requisicoes de Pedidos Parte2](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_de_Pedidos_Parte2.png) 

![Requisicoes de Pedidos Parte3](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_de_Pedidos_Parte3.png) 

![Requisicoes de Pedidos Parte4](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_de_Pedidos_Parte4.png)

# 📝 Pasta com os logs 

![Logs Pedidos](https://github.com/douglasonline/Imagens/blob/master/Logs_Pedidos.png)  

# 🐳 Como utilizar o Docker

- Primeiro deve-se descomentar o código: spring.profiles.active=docker no application.properties

![Desconmentar Codigo](https://github.com/douglasonline/Imagens/blob/master/Desconmentar_Codigo.png)

- Para subir a aplicação no Docker execute o comando abaixo na pasta do projeto

```bash

# docker-compose up

```

# 🍴 Como consumir o projeto

- O primeiro passo e colocar dados no nosso Banco de Dados

- Para colocar os dados no nosso Banco de Dados estou utilizando o Postman

- Primeiro deve-se cadastra um Usuário 

- A nossa URL para cadastra um Usuário fica assim 

- http://localhost:8080/user/users

![Cadastrando Usuario no order_management](https://github.com/douglasonline/Imagens/blob/master/Cadastrando_Usuario_no_order_management.png) 

- Após o cadastra de Usuário deve-se fazer login para poder obter o Token de acesso

- A nossa URL para obter o Token de acesso fica assim 

- http://localhost:8080/user/login

![Login do Usuario no order_management](https://github.com/douglasonline/Imagens/blob/master/Login_do_Usuario_no_order_management.png)

- Para podermos criar um produto o usuário deve-se está autenticado

- A nossa URL para adicionar um produto fica assim

http://localhost:8080/product/create

![Autentica para criar Product](https://github.com/douglasonline/Imagens/blob/master/Autentica_para_criar_Product.png)

![Criar Product](https://github.com/douglasonline/Imagens/blob/master/Criar_Product.png)

- Depois podemos listar todos os produtos para isso o usuário deve-se está autenticado

- A nossa URL para listar todos os produtos fica assim 

http://localhost:8080/product/all

![Lista todos os Product](https://github.com/douglasonline/Imagens/blob/master/Lista_todos_os_Product.png)

- Para podermos atualizar um produto precisamos passa o id do produto que queremos atualizar é o usuário deve-se está autenticado

- A nossa URL para atualizar um produto fica assim 

http://localhost:8080/product/update/{seu-id-do-produto}

![Atualizar Product](https://github.com/douglasonline/Imagens/blob/master/Atualizar_Product.png)

- Caso o produto não exista será retornado uma mensagem de aviso  

![Product nao existe](https://github.com/douglasonline/Imagens/blob/master/Product_nao_existe.png)

- Caso o id passado esteja incorreto será retornado uma mensagem de aviso    

![Product id incorreto](https://github.com/douglasonline/Imagens/blob/master/Product_id_incorreto.png)

- Para podermos deleta (excluir), um produto precisamos passa o id do produto que queremos deleta é o usuário deve-se está autenticado

- A nossa URL para deleta um produto fica assim 

http://localhost:8080/product/delete/{seu-id-do-produto}

![Deletar Product](https://github.com/douglasonline/Imagens/blob/master/Deletar_Product.png)

- Caso o produto não exista será retornado uma mensagem de aviso  

![Product nao existe para deleta](https://github.com/douglasonline/Imagens/blob/master/Product_nao_existe_para_deleta.png)

- Caso o id passado esteja incorreto será retornado uma mensagem de aviso    

![Product id incorreto para deleta](https://github.com/douglasonline/Imagens/blob/master/Product_id_incorreto_para_deleta.png)

- Para podermos buscar um produto precisamos passa o id do produto que queremos buscar é o usuário deve-se está autenticado

- A nossa URL para Buscar um produto fica assim 

http://localhost:8080/product/{seu-id-do-produto}

![Buscar Product](https://github.com/douglasonline/Imagens/blob/master/Buscar_Product.png)

- Caso o produto não exista será retornado uma mensagem de aviso  

![Product nao existe por id](https://github.com/douglasonline/Imagens/blob/master/Product_nao_existe_por_id.png)

- Caso o id passado esteja incorreto será retornado uma mensagem de aviso    

![Product id incorreto na busca por id](https://github.com/douglasonline/Imagens/blob/master/Product_id_incorreto_na_busca_por_id.png)

- Criando Pedido 

- Para criar um pedido é necessário que os produtos estejam cadastrado no Banco de Dados é o usuário deve está autenticado o cálculo do pedido é feito automaticamente

- A nossa URL para criar um pedido fica assim

http://localhost:8080/purchaseOrder/create

![Criar PurchaseOrder](https://github.com/douglasonline/Imagens/blob/master/Criar_PurchaseOrder.png)

![Criar PurchaseOrder2](https://github.com/douglasonline/Imagens/blob/master/Criar_PurchaseOrder2.png)

- Depois podemos listar todos os pedidos para isso o usuário deve-se está autenticado

- A nossa URL para listar todos os pedidos fica assim 

http://localhost:8080/purchaseOrder/all

![Lista todos os PurchaseOrder](https://github.com/douglasonline/Imagens/blob/master/Lista_todos_os_PurchaseOrder.png)

- Para podermos atualizar um pedido precisamos passa o id do pedido que queremos atualizar é o usuário deve-se está autenticado assim **implementamos a funcionalidade de adicionar produtos a um pedido**

- A nossa URL para atualizar um pedido fica assim 

http://localhost:8080/purchaseOrder/update/{seu-id-do-pedido}

![Atualizar PurchaseOrder](https://github.com/douglasonline/Imagens/blob/master/Atualizar_PurchaseOrder.png)

![Atualizar PurchaseOrder2](https://github.com/douglasonline/Imagens/blob/master/Atualizar_PurchaseOrder2.png)

- Caso o pedido não exista será retornado uma mensagem de aviso  

![PurchaseOrder nao existe](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_nao_existe.png)

- Caso o id passado esteja incorreto será retornado uma mensagem de aviso    

![PurchaseOrder id incorreto](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_id_incorreto.png)

- Para podermos deleta (excluir), um pedido precisamos passa o id do pedido que queremos deleta é o usuário deve-se está autenticado

- A nossa URL para deleta um pedido fica assim 

http://localhost:8080/purchaseOrder/delete/{seu-id-do-pedido}

![Deletar PurchaseOrder](https://github.com/douglasonline/Imagens/blob/master/Deletar_PurchaseOrder.png)

- Caso o pedido não exista será retornado uma mensagem de aviso  

![PurchaseOrder nao existe para deleta](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_nao_existe_para_deleta.png)

- Caso o id passado esteja incorreto será retornado uma mensagem de aviso    

![PurchaseOrder id incorreto para deleta](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_id_incorreto_para_deleta.png)

- Para podermos buscar um pedido precisamos passa o id do pedido que queremos buscar é o usuário deve-se está autenticado

- A nossa URL para Buscar um pedido fica assim 

http://localhost:8080/purchaseOrder/{seu-id-do-pedido}

![Buscar PurchaseOrder](https://github.com/douglasonline/Imagens/blob/master/Buscar_PurchaseOrder.png)

- Caso o pedido não exista será retornado uma mensagem de aviso  

![PurchaseOrder nao existe por id](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_nao_existe_por_id.png)

- Caso o id passado esteja incorreto será retornado uma mensagem de aviso    

![PurchaseOrder id incorreto na busca por id](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_id_incorreto_na_busca_por_id.png)

## Endpoints com Paginação

- Para criar um produto e ele retornar em paginação o usuário deve-se está autenticado

- A nossa URL fica assim

http://localhost:8080/product/createPagination

![Criar Product paginacao](https://github.com/douglasonline/Imagens/blob/master/Criar_Product_paginacao.png)

- Para podemos listar todos os produtos com paginação o usuário deve-se está autenticado

- A nossa URL fica assim 

http://localhost:8080/product/allPagination?page=0&size=10

![Lista todos os Product com paginacao](https://github.com/douglasonline/Imagens/blob/master/Lista_todos_os_Product_com_paginacao.png)

- Para podermos atualizar um produto e ele retornar em paginação precisamos passa o id do produto que queremos atualizar é o usuário deve-se está autenticado

- A nossa URL fica assim

http://localhost:8080/product/updatePagination/{seu-id-do-produto}

![Atualizar Product com paginacao](https://github.com/douglasonline/Imagens/blob/master/Atualizar_Product_com_paginacao.png)

- Caso o produto não exista será retornado uma mensagem de aviso  

![Product nao existe com paginacao](https://github.com/douglasonline/Imagens/blob/master/Product_nao_existe_com_paginacao.png)

- Para podermos deteta (excluir), um produto e ele retornar em paginação precisamos passa o id do produto que queremos deleta é o usuário deve-se está autenticado

- A nossa URL fica assim

http://localhost:8080/product/deletePagination/{seu-id-do-produto}

![Deleta Product com paginacao](https://github.com/douglasonline/Imagens/blob/master/Deleta_Product_com_paginacao.png)

- Caso o produto não exista será retornado uma mensagem de aviso  

![Product nao existe com paginacao para deleta](https://github.com/douglasonline/Imagens/blob/master/Product_nao_existe_com_paginacao_para_deleta.png)

- Caso o id passado esteja incorreto será retornado uma mensagem de aviso    

![Product id incorreto com paginacao para deleta](https://github.com/douglasonline/Imagens/blob/master/Product_id_incorreto_com_paginacao_para_deleta.png)

- Para podermos Buscar um produto e ele retornar em paginação precisamos passa o id do produto que queremos buscar é o usuário deve-se está autenticado

- A nossa URL fica assim

http://localhost:8080/product/pagination/{seu-id-do-produto}

![Buscar Product por id com paginacao](https://github.com/douglasonline/Imagens/blob/master/Buscar_Product_por_id_com_paginacao.png)

- Caso o produto não exista será retornado uma mensagem de aviso  

![Product nao existe na busca com paginacao](https://github.com/douglasonline/Imagens/blob/master/Product_nao_existe_na_busca_com_paginacao.png)

- Caso o id passado esteja incorreto será retornado uma mensagem de aviso    

![Product id incorreto na busca com paginacao](https://github.com/douglasonline/Imagens/blob/master/Product_id_incorreto_na_busca_com_paginacao.png)

- Para criar um pedido e ele retornar em paginação o usuário deve-se está autenticado

- A nossa URL fica assim

http://localhost:8080/purchaseOrder/createPagination

![Criar PurchaseOrder paginacao](https://github.com/douglasonline/Imagens/blob/master/Criar_PurchaseOrder_paginacao.png)

![Criar PurchaseOrder paginacao2](https://github.com/douglasonline/Imagens/blob/master/Criar_PurchaseOrder_paginacao2.png)

- Para podemos listar todos os pedidos com paginação o usuário deve-se está autenticado

- A nossa URL fica assim 

http://localhost:8080/purchaseOrder/allPagination?page=0&size=10

![Lista todos os PurchaseOrder com paginacao](https://github.com/douglasonline/Imagens/blob/master/Lista_todos_os_PurchaseOrder_com_paginacao.png)

- Para podermos atualizar um pedido e ele retornar em paginação precisamos passa o id do pedido que queremos atualizar é o usuário deve-se está autenticado assim **implementamos a funcionalidade de adicionar produtos a um pedido**

- A nossa URL fica assim

http://localhost:8080/purchaseOrder/updatePagination/{seu-id-do-pedido}

![Atualizar PurchaseOrder com paginacao](https://github.com/douglasonline/Imagens/blob/master/Atualizar_PurchaseOrder_com_paginacao.png)

- Caso o pedido não exista será retornado uma mensagem de aviso  

![PurchaseOrder nao existe com paginacao](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_nao_existe_com_paginacao.png)

- Caso o id passado esteja incorreto será retornado uma mensagem de aviso    

![PurchaseOrder id incorreto com paginacao](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_id_incorreto_com_paginacao.png)

- Para podermos deteta (excluir), um pedido e ele retornar em paginação precisamos passa o id do pedido que queremos deleta é o usuário deve-se está autenticado

- A nossa URL fica assim

http://localhost:8080/purchaseOrder/deletePagination/{seu-id-do-pedido}

![Deleta PurchaseOrder com paginacao](https://github.com/douglasonline/Imagens/blob/master/Deleta_PurchaseOrder_com_paginacao.png)

- Caso o pedido não exista será retornado uma mensagem de aviso  

![PurchaseOrder nao existe com paginacao para deleta](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_nao_existe_com_paginacao_para_deleta.png)

- Caso o id passado esteja incorreto será retornado uma mensagem de aviso    

![PurchaseOrder id incorreto com paginacao para deleta](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_id_incorreto_com_paginacao_para_deleta.png)

- Para podermos Buscar um pedido e ele retornar em paginação precisamos passa o id do pedido que queremos buscar é o usuário deve-se está autenticado

- A nossa URL fica assim

http://localhost:8080/purchaseOrder/pagination/{seu-id-do-pedido}

![Buscar PurchaseOrder por id com paginacao](https://github.com/douglasonline/Imagens/blob/master/Buscar_PurchaseOrder_por_id_com_paginacao.png)

- Caso o pedido não exista será retornado uma mensagem de aviso  

![PurchaseOrder nao existe na busca com paginacao](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_nao_existe_na_busca_com_paginacao.png)

- Caso o id passado esteja incorreto será retornado uma mensagem de aviso    

![PurchaseOrder id incorreto na busca com paginacao](https://github.com/douglasonline/Imagens/blob/master/PurchaseOrder_id_incorreto_na_busca_com_paginacao.png)
   

# 👤 Autor

Douglas

https://www.linkedin.com/in/douglas-j-b2194a232/

