import di.ConfigModule
import di.DaggerAppComponent
import exceptions.SpeerException
import utils.Config

fun main(args: Array<String>) {
    try{
        Config.loadConfigs(args)
        val configModule = ConfigModule()
        val component = DaggerAppComponent.builder().configModule(configModule).build()
        val httpServer = component.server()
        httpServer.start()
        println("\n----------------Server Start----------------\n\n")
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                println("Shutting Down.......")
            }
        })
    }catch (e: Exception){
        println("-----Error-----")
        e.printStackTrace()
    }
}