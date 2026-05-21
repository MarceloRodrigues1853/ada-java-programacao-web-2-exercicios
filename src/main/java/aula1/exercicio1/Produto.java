package aula1.exercicio1;

public class Produto {
    // Atributos solicitados na Parte 3
    private String titulo;
    private double preco;
    private String categoria;

    // Construtor para criar o objeto
    public Produto(String titulo, double preco, String categoria) {
        this.titulo = titulo;
        this.preco = preco;
        this.categoria = categoria;
    }

    // Getters: Importantes para o Jackson (ObjectMapper) converter para JSON depois
    public String getTitulo() {
        return titulo;
    }

    public double getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }
}