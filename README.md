# pagamento-api

Este projeto foi feito com Java 8, Spring Boot e PostgreSQL.

Tentei focar primariamente no clean code, nas validações e nos testes. Para demonstrar conhecimento de teste unitário, criei um método de salvar que iria requerer validações. No método de consultar todos, resolvi faze-lo paginado para evitar trazer um json grande demais.

Criei ExceptionHandlers específicos para a necessidade. Um ExceptionHandler foi dedicado às validações, o outro a erros genéricos (para não levar para o cliente informações de back end possivelmente sensíveis).

No teste unitário foquei em validar apenas o necessário. Primeiramente, testei o cenário feliz na qual tudo funciona. Depois testei se realmente estava jogando exception e o exception que eu especificamente previ. Depois testei se ele estava validando a relação entre o Tipo de Forma de Pagamento e o Parcelamento (se for AVISTA, não pode haver mais de uma parcela). E por fim testei se todas as diferentes lógicas de validação estavam corretas, usando exemplares específicos.