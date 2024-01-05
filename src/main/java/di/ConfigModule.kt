package di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import jdk.jfr.Name
import utils.*
import javax.inject.Named

@Module
open class ConfigModule {
    @Provides
    @Named(SPEER_MANAGER_IP)
    fun provideSpeerManagerIp(): String{
        return Config.getConfigs(SPEER_MANAGER_IP)
    }

    @Provides
    @Named(SPEER_MANAGER_PORT)
    fun provideSpeerManagerPort(): Int{
        return Config.getConfigs(SPEER_MANAGER_PORT).toInt()
    }

    @Provides
    @Named(SPEER_DB_NAME)
    fun provideSpeerManagerDbName(): String{
        return Config.getConfigs(SPEER_DB_NAME)
    }

    @Provides
    @Named(USER_COLLECTION_NAME)
    fun provideUserCollectionName(): String{
        return Config.getConfigs(USER_COLLECTION_NAME)
    }

    @Provides
    @Named(SPEER_MANAGER_DB_HOST)
    fun provideSpeerManagerDbHost(): String{
        return Config.getConfigs(SPEER_MANAGER_DB_HOST)
    }

    @Provides
    @Named(SPEER_MANAGER_DB_PORT)
    fun provideSpeerManagerDbPort(): Int{
        return Config.getConfigs(SPEER_MANAGER_DB_PORT).toInt()
    }

    @Provides
    fun provideGson(): Gson{
        return Gson()
    }

    @Provides
    @Named(JWT_SIGNATURE_KEY)
    fun provideJWTKey(): String{
        return Config.getConfigs(JWT_SIGNATURE_KEY)
    }

    @Provides
    @Named(SPEER_MANAGER_REDIS_HOST)
    fun provideRedisHost(): String{
        return Config.getConfigs(SPEER_MANAGER_REDIS_HOST)
    }

    @Provides
    @Named(SPEER_MANAGER_REDIS_PORT)
    fun provideRedisPort(): Int{
        return Config.getConfigs(SPEER_MANAGER_REDIS_PORT).toInt()
    }

    @Provides
    @Named(NOTES_COLLECTION_NAME)
    fun provideNotesCollectionName(): String{
        return Config.getConfigs(NOTES_COLLECTION_NAME)
    }

    @Provides
    @Named(EXPIRY_TIME)
    fun provideExpiryTime(): Long{
        return Config.getConfigs(EXPIRY_TIME).toLong()
    }

    @Provides
    @Named(RATE_LIMIT)
    fun provideRateLimit(): Long{
        return Config.getConfigs(RATE_LIMIT).toLong()
    }
}