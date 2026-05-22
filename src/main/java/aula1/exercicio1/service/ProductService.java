package aula1.exercicio1.service;

import aula1.exercicio1.dto.ProductDto;
import aula1.exercicio1.dto.ProductResponse;
import aula1.exercicio1.http.HttpClientUtil;
import aula1.exercicio1.model.Produto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private static final String BASE_URL = "https://dummyjson.com/products";
    private final HttpClientUtil httpClient = new HttpClientUtil();
    private final ObjectMapper mapper = new ObjectMapper();

    // Método para listar produtos padrão (Parte 1 e 2)
    public List<Produto> listarProdutos() throws Exception {
        String json = httpClient.get(BASE_URL);
        return converterParaModel(json);
    }

    // Método para a Busca Dinâmica (Parte 4)
    public List<Produto> buscarProdutos(String termo) throws Exception {
        // Monta a URL de busca: /products/search?q=phone
        String urlBusca = BASE_URL + "/search?q=" + termo.trim().replace(" ", "+");
        String json = httpClient.get(urlBusca);
        return converterParaModel(json);
    }

    // Auxiliar para varrer os DTOs e transformar em uma lista limpa de Models em português
    private List<Produto> converterParaModel(String json) throws Exception {
        ProductResponse response = mapper.readValue(json, ProductResponse.class);
        List<Produto> listaConvertida = new ArrayList<>();

        if (response.getProducts() != null) {
            for (ProductDto dto : response.getProducts()) {
                Produto p = new Produto(dto.getTitle(), dto.getPrice(), dto.getCategory());
                listaConvertida.add(p);
            }
        }
        return listaConvertida;
    }
}
