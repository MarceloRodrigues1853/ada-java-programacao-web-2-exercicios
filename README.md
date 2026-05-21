# 📚 Programação Web II - Exercícios e Códigos das Aulas

Bem-vindo ao repositório do nosso grupo! Este espaço foi criado com o objetivo de centralizar todos os exercícios, projetos práticos e códigos desenvolvidos durante as aulas do módulo de **Programação Web II**.

## 🎯 Objetivo do Repositório

* **Facilitar a Replicação:** Permitir que todos os integrantes do grupo consigam clonar, rodar e testar os códigos localmente em suas máquinas sem dificuldades.
* **Fixação e Entendimento:** Servir como base de estudos para revisar a matéria, entender a arquitetura do código e tirar dúvidas sobre a implementação dos conceitos vistos em aula.
* **Trabalho em Equipe:** Garantir que todo mundo esteja alinhado com o progresso das aulas e tenha acesso rápido às soluções dos exercícios e desafios propostos.

---

## 🏗️ Padrões de Arquitetura e Tecnologias do Módulo

Os projetos desenvolvidos aqui seguem as diretrizes e boas práticas exigidas na disciplina de Programação Web II, focando em sistemas distribuídos e consumo de APIs REST:

* **Arquitetura em Camadas (Separation of Concerns):** Organização estruturada do código em pacotes isolados para garantir a responsabilidade única:
  * `Main`: Interface de interação via console e controle do fluxo do usuário.
  * `Service`: Camada de negócio responsável pelas regras de paginação, filtros e comunicação com o cliente HTTP.
  * `DTO (Data Transfer Object)`: Classes que representam a estrutura exata do JSON externo, isolando o formato de transporte da API.
  * `Model`: Classes de domínio que representam as entidades e regras internas da nossa aplicação.
* **Manipulação e Conversão de Dados (Jackson):** Utilização do ecossistema Jackson (`ObjectMapper`, `TypeReference`, anotações) para realizar a desserialização de JSONs complexos (objetos e arrays) vindos da internet diretamente para coleções nativas do Java.
* **Otimização de Código (Lombok):** Uso de anotações como `@Data`, `@NoArgsConstructor` e `@AllArgsConstructor` para eliminar código repetitivo (*boilerplate*) e manter nossas classes de dados limpas e legíveis.
* **Tratamento de Exceções em Rede:** Implementação de tratamento de erros robusto para lidar com falhas de conexão, indisponibilidade de servidores externos, códigos de status HTTP inesperados e entradas inválidas no console.

---

## 🚀 Como usar e replicar os códigos?

1. **Clonar o Repositório:** Abra o terminal na sua pasta de projetos e use o comando:
   ```bash
   git clone [https://github.com/MarceloRodrigues1853/ada-java-programacao-web-2-exercicios.git](https://github.com/MarceloRodrigues1853/ada-java-programacao-web-2-exercicios.git)
   ```
2. Abrir no IntelliJ: Abra a pasta clonada no IntelliJ IDEA e aguarde o Gradle baixar as dependências automaticamente.

3. Executar: Vá até o arquivo `src/main/java/aula2/exercicio/Main.java`, clique no botão de Run (Play verde) e interaja com o menu pelo terminal.

Sinta-se à vontade para revisar o código, sugerir melhorias no grupo e usar como base para os nossos próximos desafios! 💪
