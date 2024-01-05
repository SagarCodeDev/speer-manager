package resources

import exceptions.UnAuthorizedException
import services.NotesService
import services.UserService
import utils.BASE_PATH
import utils.Response
import utils.TOKEN_NOT_VALID
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Singleton
@Path("$BASE_PATH/notes/v1/api")
class NotesResource @Inject constructor(
    private val userService: UserService,
    private val notesService: NotesService
) {

    @POST
    @Path("/notes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    fun saveNotes(
        @HeaderParam("Authorization") authorization: String?,
        request: String
    ): String{
        //Authenticate user
        val token = authorization?.split(" ")?.get(1) ?: throw UnAuthorizedException(TOKEN_NOT_VALID)
        val userId = userService.parseTokenAndGetUser(token)
        userService.verifyUserId(userId)
        val response = notesService.createNotes(request, userId)
        return Response.ok(response).toString()
    }

    @GET
    @Path("/notes")
    @Produces(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    fun getNotesByUserId(
        @HeaderParam("Authorization") authorization: String?,
        @QueryParam("limit") limit: Int?,
        @QueryParam("offset") offset: Int?,
        @QueryParam("filters") filters: String?,
        @QueryParam("search") search: String?,
        @QueryParam("projections") projections: String?
    ): String{
        val token = authorization?.split(" ")?.get(1) ?: throw UnAuthorizedException(TOKEN_NOT_VALID)
        val userId = userService.parseTokenAndGetUser(token)
        userService.verifyUserId(userId = userId)
        val include = projections?.split(",") ?: listOf()
        val response = notesService.getNotes(
            filters = filters,
            search = search,
            projections = include,
            limit = limit ?: 50,
            offset = offset ?: 0,
            userId = userId
        )
        return Response.ok(response).toString()
    }

    @GET
    @Path("/notes/{id}")
    @Throws(Exception::class)
    @Produces(MediaType.APPLICATION_JSON)
    fun getNoteById(
        @HeaderParam("Authorization") authorization: String?,
        @PathParam("id") id: String
    ){
        val token = authorization?.split(" ")?.get(1) ?: throw UnAuthorizedException(TOKEN_NOT_VALID)
        val userId = userService.parseTokenAndGetUser(token)
        userService.verifyUserId(userId)
        val response = notesService.getNoteById(
            id = id,
            userId = userId
        )
    }

    @PUT
    @Path("/notes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    fun updateNote(
        @HeaderParam("Authorization") authorization: String?,
        request: String
    ): String{
        val token = authorization?.split(" ")?.get(1) ?: throw UnAuthorizedException(TOKEN_NOT_VALID)
        val userId = userService.parseTokenAndGetUser(token)
        userService.verifyUserId(userId)
        val response = notesService.updateNote(request, userId)
        return Response.ok(response).toString()
    }

    @DELETE
    @Path("/notes/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    fun deleteNote(
        @HeaderParam("Authorization") authorization: String?,
        @PathParam("id") id: String
    ): String{
        val token = authorization?.split(" ")?.get(1) ?: throw UnAuthorizedException(TOKEN_NOT_VALID)
        val userId = userService.parseTokenAndGetUser(token)
        userService.verifyUserId(userId)
        val response = notesService.deleteNote(id, userId)
        return Response.ok(response).toString()
    }

    @POST
    @Path("/notes/{id}/share")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Throws(Exception::class)
    fun shareNoteWithUsers(
        @HeaderParam("Authorization") authorization: String?,
        @PathParam("id") id: String,
        @QueryParam("userIds") userIds: String
    ): String{
        val token = authorization?.split(" ")?.get(1) ?: throw UnAuthorizedException(TOKEN_NOT_VALID)
        val userId = userService.parseTokenAndGetUser(token)
        userService.verifyUserId(userId)
        val response = notesService.shareNoteWithUsers(id, userIds.split(","), userId)
        return Response.ok(response).toString()
    }
}