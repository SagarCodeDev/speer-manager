package utils

import javax.inject.Inject
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider

@Provider
class CORSFilter @Inject constructor(): ContainerResponseFilter, ContainerRequestFilter {
    override fun filter(requestContext: ContainerRequestContext) {
        TODO("Not yet implemented")
    }

    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {
        responseContext.headers.add("Access-Control-Allow-Origin", "*")
        responseContext.headers.add(
            "Access-Control-Allow-Credentials",
            "origin, content-type, accept, authorization, access-control-allow-methods, access-control-allow-origin"
        )
        responseContext.headers.add("Access-Control-Allow-Credentials", "true")
        responseContext.headers.add(
            "Access-Control-Allow-Methods",
            "GET, POST, PUT, DELETE, OPTIONS, HEAD"
        )
    }
}