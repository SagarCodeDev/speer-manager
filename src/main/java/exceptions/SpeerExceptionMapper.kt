package exceptions

import utils.UNAUTHORIZED
import javax.inject.Inject
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper

class SpeerExceptionMapper @Inject constructor(): ExceptionMapper<SpeerException> {
    override fun toResponse(exception: SpeerException): Response {
        println("Inside exception mapper")
        return when(exception){
            is UnAuthorizedException -> {
                Response.ok(utils.Response.error(UNAUTHORIZED).toString(), MediaType.APPLICATION_JSON).build()
            }

            is NotAllowedException -> {
                Response.ok(utils.Response.error(exception.message), MediaType.APPLICATION_JSON).build()
            }

            is DatabaseException -> {
                println("Database exception occured: ${exception.message}")
                Response.serverError().build()
            }


            else -> {
                exception.printStackTrace()
                Response.serverError().build()
            }
        }
    }

}