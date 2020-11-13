package com.moecrow.demo.dao.reporitory;

import com.moecrow.demo.dao.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author willz
 * @date 2020.11.13
 */
public interface UserRepository extends MongoRepository<User, String> {
}
