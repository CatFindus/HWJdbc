package mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;


public class JsonMapper implements ViewMapper {
    private final ObjectMapper mapper;

    public JsonMapper() {
        mapper = new ObjectMapper();
    }

    @Override
    public <T extends Entity> T getDtoFromRequest(Class<T> clazz, Reader reader) {
        T result = null;
        try (BufferedReader br = new BufferedReader(reader)) {
            StringBuilder json = new StringBuilder();
            while (br.ready()) {
                json.append(br.readLine());
            }
            result = mapper.readValue(json.toString(), clazz);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String getStringFromResponse(List<Entity> response) {
        String result = null;
        try {
            result = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
