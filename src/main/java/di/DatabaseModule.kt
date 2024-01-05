package di

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import dagger.Module
import dagger.Provides
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import utils.*
import javax.inject.Named
import javax.inject.Singleton

@Module
open class DatabaseModule {

    @Singleton
    @Provides
    fun provideMongoClient(
        @Named(SPEER_MANAGER_DB_HOST) host: String,
        @Named(SPEER_MANAGER_DB_PORT) port: Int
    ): MongoClient{
        return MongoClients.create("mongodb://${host}:${port}")
    }

    @Singleton
    @Provides
    fun provideRedisClient(
        @Named(SPEER_MANAGER_REDIS_HOST) host: String,
        @Named(SPEER_MANAGER_REDIS_PORT) port: Int
    ): RedisClient{
        return RedisClient.create("redis://$host:$port")
    }

    @Provides
    fun provideRedisConnection(
        redisClient: RedisClient
    ): StatefulRedisConnection<String,String>{
        return redisClient.connect()
    }


    @Provides
    fun provideSpeerManagerDb(
        mongoClient: MongoClient,
        @Named(SPEER_DB_NAME) dbName: String
    ): MongoDatabase{
        return mongoClient.getDatabase(dbName)
    }
}