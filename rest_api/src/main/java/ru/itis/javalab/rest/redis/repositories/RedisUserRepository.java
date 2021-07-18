package ru.itis.javalab.rest.redis.repositories;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.itis.javalab.rest.redis.models.RedisUser;

public interface RedisUserRepository extends KeyValueRepository<RedisUser, String> {
}
