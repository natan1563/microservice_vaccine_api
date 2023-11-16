# Projeto Java - README.md

Este README fornece instruções detalhadas sobre como configurar, instalar e executar um projeto Java utilizando a JDK 17 e a IDE IntelliJ IDEA. Além disso, inclui informações sobre como baixar o projeto do GitHub [microservice_vaccine_api](https://github.com/natan1563/microservice_vaccine_api) e realizar testes na aplicação.

## Pré-requisitos
Certifique-se de ter os seguintes pré-requisitos instalados em seu sistema:

- [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)

## Configuração do Projeto no IntelliJ IDEA
https://erangel-unime.postman.co/workspace/SpringBoot-APIS~ba414c40-dcc8-44b1-af28-c9f406479095/folder/17428469-a6e74851-6c89-4e4b-912e-343238af80fe?ctx=documentation
1. Clone o Repositório:
   ```bash
   git clone https://github.com/natan1563/microservice_vaccine_api.git
   ```

2. Abra o IntelliJ IDEA:
   Abra a IDE IntelliJ IDEA e clique em "File" > "Open". Navegue até o diretório do projeto clonado e selecione o arquivo de configuração.

3. Configurar JDK:
    - Vá para "File" > "Project Structure".
    - Em "Project", assegure-se de que a versão do Projeto e a SDK correspondam à JDK 17.

4. Baixe as Dependências:
    - Aguarde o IntelliJ IDEA reconhecer o projeto e baixar as dependências automaticamente.

5. Collection dos endpoints: 
   - [Collection](https://documenter.getpostman.com/view/17428469/2s9YXe6Pa9)
   - Selecione a pasta Vaccine

## Executando o Projeto

1. Configurar a Classe Principal:
    - Abra a classe principal do projeto: `src/main/java/api/vacinacao/vacina/VaccineApplication`.
    - Clique com o botão direito na classe e selecione "Run" para executar a aplicação.
    - Realize uma requisição para o endpoint de mock: `http://localhost:8080/vaccine/mock-vaccines`

## Testando a Aplicação

1. Executar Testes:
    - Para executar os testes, vá até a classe de teste correspondente: `src/test/java/api/vacinacao/vacina/controller/VaccineControllerTest`.
    - Clique com o botão direito na classe de teste e selecione "Run" para executar os testes.

Agora você está pronto para trabalhar com o nosso projeto Java usando a JDK 17 e o IntelliJ IDEA. Certifique-se de seguir essas instruções cuidadosamente para garantir uma configuração correta.
