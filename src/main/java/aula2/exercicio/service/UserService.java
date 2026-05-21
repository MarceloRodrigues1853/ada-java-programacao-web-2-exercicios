package aula2.exercicio.service;

import aula2.exercicio.dto.UserDto;
import aula2.exercicio.http.HttpClientUtil;
import aula2.exercicio.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final HttpClientUtil httpClient = new HttpClientUtil();
    private final ObjectMapper mapper = new ObjectMapper();

    // Lista usuários tratando o Array JSON do JSONPlaceholder
    public List<User> list(int limit, int skip) throws Exception {
        String url = BASE_URL + "/users";
        String json = httpClient.get(url);

        // Desserializa o Array JSON em uma lista de DTOs
        List<UserDto> dtos = mapper.readValue(json, new TypeReference<List<UserDto>>(){});
        List<User> usuarios = new ArrayList<>();

        // Aplica a paginação manualmente na lista recebida
        int contador = 0;
        for (int i = skip; i < dtos.size(); i++) {
            if (contador >= limit) break;

            UserDto dto = dtos.get(i);

            // Converte o DTO em Model (mantendo os atributos em inglês conforme o exercício)
            User user = new User(
                    dto.getId(),
                    dto.getName(),
                    dto.getEmail(),
                    dto.getPhone(),
                    dto.getWebsite()
            );
            usuarios.add(user);
            contador++;
        }

        return usuarios;
    }

    // Busca usuários por texto filtrando localmente na aplicação
    public List<User> search(String term) throws Exception {
        String url = BASE_URL + "/users";
        String json = httpClient.get(url);

        List<UserDto> dtos = mapper.readValue(json, new TypeReference<List<UserDto>>(){});
        List<User> resultados = new ArrayList<>();

        for (UserDto dto : dtos) {
            if (dto.getName() != null && dto.getName().toLowerCase().contains(term.toLowerCase())) {
                User user = new User(
                        dto.getId(),
                        dto.getName(),
                        dto.getEmail(),
                        dto.getPhone(),
                        dto.getWebsite()
                );
                resultados.add(user);
            }
        }

        return resultados;
    }
}