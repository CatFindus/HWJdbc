package mapper;

import model.Entity;

import java.io.Reader;

public interface ViewMapper {
    <T extends Entity> T getDtoFromRequest(Class<T> clazz, Reader reader);
}
