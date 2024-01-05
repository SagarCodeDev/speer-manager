package resources

import exceptions.UnAuthorizedException
import services.JWTService
import services.UserService
import utils.BASE_PATH
import utils.INVALID_USER
import utils.Response
import utils.TOKEN_NOT_VALID
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.HeaderParam
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Singleton
@Path("/$BASE_PATH/api/auth")
class UserResource @Inject constructor(
    private val userService: UserService,
    private val jwtService: JWTService
) {

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    fun createUser(
        request: String
    ): String{
        val response =  userService.createUser(request)
        return Response.ok(response).toString()
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    fun loginUser(
        request: String
    ): String{
        val response = userService.authenticateUser(request)
        return Response.ok(response).toString()
    }

    @GET
    @Path("/logout/userId")
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    fun logoutUser(
        @PathParam("userId") userId: String,
        @HeaderParam("Authorization") header: String?
    ): String{
        val token = header?.split(" ")?.get(1) ?: throw UnAuthorizedException(TOKEN_NOT_VALID)
        val tokenUserId = jwtService.parseTokenAndGetUser(token)
        if(userId != tokenUserId){
            throw UnAuthorizedException(INVALID_USER)
        }
        jwtService.deleteToken(userId)
        return Response.ok("OK").toString()
    }

}