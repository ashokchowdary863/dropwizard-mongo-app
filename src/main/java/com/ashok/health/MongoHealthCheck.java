package com.ashok.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoHealthCheck extends HealthCheck {
    private final MongoDatabase mongoDatabase;

    public MongoHealthCheck(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    protected Result check() throws Exception {
        try {
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = mongoDatabase.runCommand(command);
            return Result.healthy();
        } catch (MongoException me) {
            return Result.unhealthy("Not able to connect to Mongo");
        }
    }
}
