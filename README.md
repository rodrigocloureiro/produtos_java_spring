
# Assessment - Desenvolvimento de Serviços Web e Testes com Java

### Enunciado

- Desenvolva uma aplicação com Spring Boot que deverá ficar ouvindo na porta 8080.Sua aplicação deve possuir pelo menos 4 endpoints, sendo pelo menos um para cada verbo HTTP. (GET,POST,DELETE,PUT).
- No Endpoint que receberá um POST você deverá receber um JSON com pelo menos um campo do tipo String, um do tipo número e um array de qualquer tipo.No endpoint do tipo GET você deverá receber 2 parâmetros opcionais.
- Algum de seus métodos deve tratar a requisição do usuário, em caso de erro retorne algum código de erro e em caso de sucesso retorne 200.
- Em um dos seus endpoints você deverá consumir alguma API externa à sua escolha.
- Você deverá converter a resposta dessa chamada de JSON para um objeto java. Imprima(com Log) o status code dessa resposta.
- Você deverá criar os testes para o seus métodos. Pelo menos um dos testes deverá ter um assertThrows. Não utilize System.out e sim o mecanismo de LOG.
- Utilize o Lombok.

### Rubricas

1. Preparar o ambiente para desenvolvimento local com Java
- O aluno utilizou o Spring Initiliz para criar seu projeto?	
- O aluno utilizou as Goals do maven para iniciar e pausar seu projeto?	
- O aluno utilizou o maven para gerar um fatjar do seu projeto?	
- O aluno importou o projeto Lombok?	

2. Desenvolver testes unitários usando JUnit
- O aluno importou a biblioteca do JUNIT5?	
- O aluno testou seus endpoints com as Assertions do Junit?	
- O aluno utilizou a Assert Throws do Junit?	
- O aluno utilizou o Junit ao invés de criar o método main para testar seu código?	

3. Consumir serviços web (RESTful APIs) com Java
- O aluno utilizou a api de Requests do JAVA 11 para realizar uma chamada a uma api externa?	
- O aluno imprimiu com LOG o status code da Resposta?	
- O aluno converteu o resultado da requisição de um JSON para um objeto JAVA?	
- O aluno testou a requisição externa?	

4. Desenvolver serviços web (RESTful API) usando Spark Java
- O aluno criou um serviço web ouvindo na porta 8080?	
- O aluno criou pelo menos um endpoint para cada verbo HTTP(GET,POST,DELETE,PUT)?	
- O aluno recebeu um JSON no corpo da requisição do método POST?	
- O aluno desenvolveu um código de erro como tratamento de um método problemático?	


### Prints

1. Preparar o ambiente para desenvolvimento local com Java
- O aluno utilizou o Spring Initiliz para criar seu projeto?
![](https://i.imgur.com/A0L3dOy.png)	

- O aluno utilizou as Goals do maven para iniciar e pausar seu projeto?
![](https://i.imgur.com/1mCVbBg.png)

![](https://i.imgur.com/Ai5uePp.png)

- O aluno utilizou o maven para gerar um fatjar do seu projeto?
![](https://i.imgur.com/Cxs5qdT.png)

![](https://i.imgur.com/x00t19D.png)

- O aluno importou o projeto Lombok?
![](https://i.imgur.com/g0oAlkl.png)

2. Desenvolver testes unitários usando JUnit
- O aluno importou a biblioteca do JUNIT5?
![](https://i.imgur.com/vX8K4ty.png)

- O aluno testou seus endpoints com as Assertions do Junit?
![](https://i.imgur.com/yBTnyQo.png)

- O aluno utilizou a Assert Throws do Junit?
![](https://i.imgur.com/i5X5YeM.png)

- O aluno utilizou o Junit ao invés de criar o método main para testar seu código?
![](https://i.imgur.com/I8asjvu.png)

3. Consumir serviços web (RESTful APIs) com Java
- O aluno utilizou a api de Requests do JAVA 11 para realizar uma chamada a uma api externa?
![](https://i.imgur.com/cRQRNn7.png)

- O aluno imprimiu com LOG o status code da Resposta?
![](https://i.imgur.com/IoHoGlD.png)

- O aluno converteu o resultado da requisição de um JSON para um objeto JAVA?
![](https://i.imgur.com/CcRrzLr.png)

- O aluno testou a requisição externa?
![](https://i.imgur.com/lkPCsVY.png)

4. Desenvolver serviços web (RESTful API) usando Spark Java
- O aluno criou um serviço web ouvindo na porta 8080?
![](https://i.imgur.com/AGV0vjR.png)

- O aluno criou pelo menos um endpoint para cada verbo HTTP(GET,POST,DELETE,PUT)?
![](https://i.imgur.com/rS8qD58.png)

- O aluno recebeu um JSON no corpo da requisição do método POST?
![](https://i.imgur.com/reacfla.png)

- O aluno desenvolveu um código de erro como tratamento de um método problemático?
![](https://i.imgur.com/iodGJG5.png)

![](https://i.imgur.com/1rohtPY.png)