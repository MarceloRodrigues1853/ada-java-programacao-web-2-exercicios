package aula2.exercicio.service;

import aula2.exercicio.dto.PostDto;
import aula2.exercicio.http.HttpClientUtil;
import aula2.exercicio.model.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class PostService {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final HttpClientUtil httpClient = new HttpClientUtil();
    private final ObjectMapper mapper = new ObjectMapper();

    // Lista publicações tratando o Array JSON do JSONPlaceholder
    public List<Post> list(int limit, int skip) throws Exception {
        String url = BASE_URL + "/posts";
        String json = httpClient.get(url);

        // Desserializa o Array JSON em uma lista de PostDto
        List<PostDto> dtos = mapper.readValue(json, new TypeReference<List<PostDto>>(){});
        List<Post> posts = new ArrayList<>();

        // Simula a paginação limit/skip na lista de posts
        int contador = 0;
        for (int i = skip; i < dtos.size(); i++) {
            if (contador >= limit) break;

            PostDto dto = dtos.get(i);

            Post post = new Post(
                    dto.getUserId(),
                    dto.getId(),
                    dto.getTitle(),
                    dto.getBody()
            );
            posts.add(post);
            contador++;
        }

        return posts;
    }

    // === MÉTODO RESPONSÁVEL POR RESOLVER O DESAFIO EXTRA ===
    // Faz uma requisição filtrando os posts por ID de usuário e retorna a quantidade total
    public int contarPostsDoUsuario(int userId) throws Exception {
        String url = BASE_URL + "/posts?userId=" + userId;
        String json = httpClient.get(url);

        // Desserializa o array específico de posts daquele usuário
        List<PostDto> dtos = mapper.readValue(json, new TypeReference<List<PostDto>>(){});

        // Retorna a quantidade de elementos (o "size" da lista equivale ao total de posts)
        return dtos.size();
    }
}