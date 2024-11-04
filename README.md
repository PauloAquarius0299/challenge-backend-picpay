# Desafio backend PicPay Simplificado
![Captura de Tela (297)](https://github.com/user-attachments/assets/b33d42db-6a34-40e4-9284-c9ce4da80e03)
Projeto de desafio backend da picpay criando uma api rest com Java Spring 
### Conclusão
Criei as classes do usuario e tipos de usuario como logista ou não e a classe de transferencia sendo senderId (usuario que vai mandar o dinheito) e receiverId (usuario que vai receber o dinheiro).
No arquivo DTO crie as classes records em Java que é uma maneira concisa de declarar uma classe de dados imutável.
Em services desenvolvi as funcionalidades que juntos gerenciam usuários e transações em uma aplicação de pagamentos simplificada, com implementação estruturada para garantir a segurança dos dados, verificar transações, permissões, saldo e resposta da autorização com uma notificação de mensagem para o cliente.
Desenvolvi o REST em controller para usuario (POST/GET) e transações (POST) para receber as classes no corpo de requisição e chamar os serviços.
Com banco de dados em memoria criei os repositorios para encontrar as entidades usuario e transição com a interface "JpaRepository" que prove metodos básicos para operações em CRUD.
### Ferramentas
* Java
* Spring
### Link do Desafio 
```
https://github.com/PicPay/picpay-desafio-backend
```
