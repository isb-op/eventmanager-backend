📅 API para gerenciamento de eventos

Uma API para gerenciamento de eventos, com funcionalidades de autenticação de usuários, criação, atualização e consulta de eventos. A API também utiliza a integração com a API ViaCEP para preenchimento de endereços a partir do CEP.

🚀 Funcionalidades

Gerenciamento de Usuários:
- Registro e autenticação de usuários com diferentes níveis de permissão.
- Retorno de token.
- Verificação de permissões para ações específicas.
  
Gerenciamento de Eventos:
- Criação de eventos; 
- Atualização de informações dos eventos existentes.
- Exclusão do evento.
- Associação de eventos aos usuários que os criaram.
  
Integração com ViaCEP:
- Consulta automática de endereços com base no CEP fornecido.

🛠️ Tecnologias Utilizadas
Linguagem: Java
Framework: Spring Boot
Banco de Dados: PostgreSQL
Ferramentas: Postman e Swagger (testes de API), Spring Security (autenticação)
Integração Externa: ViaCEP API.
