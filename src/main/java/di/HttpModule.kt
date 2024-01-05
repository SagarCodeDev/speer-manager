package di

import dagger.Module
import dagger.Provides
import exceptions.SpeerExceptionMapper
import jdk.jfr.Name
import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import resources.NotesResource
import resources.UserResource
import utils.CORSFilter
import utils.SPEER_MANAGER_IP
import utils.SPEER_MANAGER_PORT
import javax.inject.Named
import javax.inject.Singleton
import javax.ws.rs.core.UriBuilder

@Module
open class HttpModule {

    @Provides
    @Singleton
    fun provideRestApplicationBootstrap(
        userResource: UserResource,
        notesResource: NotesResource
    ): ResourceConfig{
        return ResourceConfig()
            .register(CORSFilter())
            .register(SpeerExceptionMapper::class.java)
            .register(userResource)
            .register(notesResource)
    }

    @Singleton
    @Provides
    fun server(
        @Named(SPEER_MANAGER_IP) host: String,
        @Named(SPEER_MANAGER_PORT) port: Int,
        resourceConfig: ResourceConfig
    ): HttpServer{
        val url = UriBuilder.fromUri(host).port(port).build()
        return GrizzlyHttpServerFactory.createHttpServer(url, resourceConfig)
    }
}