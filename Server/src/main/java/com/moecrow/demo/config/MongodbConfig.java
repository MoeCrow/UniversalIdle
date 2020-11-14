package com.moecrow.demo.config;

import com.moecrow.demo.dao.reporitory.BaseRepositoryImpl;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * basePackages:设置Repository的扫描目录
 * mongoTemplateRef:mongo模板类引用,类似于JDBCTemplate
 * repositoryBaseClass:设置默认的Repository实现类为BaseRepositoryImpl,代替SimpleMongoRepository
 */
@Configuration
@EnableMongoRepositories(
        basePackages={"com.moecrow.demo.dao"},
        repositoryBaseClass = BaseRepositoryImpl.class)
public class MongodbConfig {
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MappingMongoConverter converter = new MappingMongoConverter(
                new DefaultDbRefResolver(mongodbFactory()),
                new MongoMappingContext());

        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return new MongoTemplate(mongodbFactory(), converter);
    }

    @Bean
    public MongoDbFactory mongodbFactory() throws Exception {
        MongoClientURI mongoClientUri = new MongoClientURI(mongoUri);
        return new SimpleMongoDbFactory(mongoClientUri);
    }

//    @Bean
//    public IDGenerator idGenerator(){
//        return  new IDGenerator(configProperties.getWorkerId(),0);
//    }
}