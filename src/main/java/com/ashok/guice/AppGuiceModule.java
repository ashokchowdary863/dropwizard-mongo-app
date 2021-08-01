package com.ashok.guice;

import com.ashok.AppConfig;
import com.ashok.dao.UserDAO;
import com.ashok.dao.UserDAOImpl;
import com.ashok.entity.User;
import com.ashok.exception.AppExceptionMapper;
import com.ashok.exception.ConstraintViolationExceptionMapper;
import com.ashok.filter.AppFilter;
import com.ashok.health.DefaultCheck;
import com.ashok.health.MongoHealthCheck;
import com.ashok.models.MongoConfig;
import com.ashok.resources.UserResource;
import com.ashok.service.UserService;
import com.ashok.service.UserServiceImpl;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;

public class AppGuiceModule extends DropwizardAwareModule<AppConfig> {


    @Override
    protected void configure() {

        //DAO registration
        MongoConfig mongoConfig = configuration().getMongoConfig();
        MongoClient mongoClient = new MongoClient(mongoConfig.getHost(), Integer.parseInt(mongoConfig.getPort()));
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoDatabase database = mongoClient.getDatabase(mongoConfig.getDatabase()).withCodecRegistry(pojoCodecRegistry);
        MongoCollection<User> collection = database.getCollection(mongoConfig.getCollection(), User.class);
        UserDAOImpl userDao = new UserDAOImpl(database, collection);
        bind(UserDAO.class).toInstance(userDao);

        //Health Checks
        final DefaultCheck defaultCheck = new DefaultCheck();
        environment().healthChecks().register("default", defaultCheck);

        final MongoHealthCheck mongoHealthCheck = new MongoHealthCheck(database);
        environment().healthChecks().register("mongo", defaultCheck);

        //Exception mappers
        bind(AppExceptionMapper.class).in(Singleton.class);
        bind(ConstraintViolationExceptionMapper.class).in(Singleton.class);

        //Resources
        bind(UserResource.class).in(Singleton.class);

        //Service
        bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);

        //Filters
        environment().jersey().register(AppFilter.class);


    }


}
