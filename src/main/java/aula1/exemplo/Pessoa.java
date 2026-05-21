package aula1.exemplo;

public class Pessoa {

    // Atributos da classe
    // Representam os dados da pessoa
    private String nome;
    private String curso;

    // Construtor
    // Utilizado para criar objetos Pessoa
    public Pessoa(String nome, String curso) {
        this.nome = nome;
        this.curso = curso;
    }

    // Getter do nome
    // Jackson utiliza getters para converter objeto em JSON
    public String getNome() {
        return nome;
    }

    // Getter do curso
    public String getCurso() {
        return curso;
    }
}
