package di

import dagger.Component
import org.glassfish.grizzly.http.server.HttpServer
import javax.inject.Singleton

@Singleton
@Component(modules = [ConfigModule::class, HttpModule::class, DatabaseModule::class])
interface AppComponent {
    fun server(): HttpServer
}