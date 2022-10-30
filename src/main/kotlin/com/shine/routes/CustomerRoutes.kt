package com.shine.routes

import com.shine.dao.DaoFacade
import com.shine.dao.impl.DaoFacadeImpl
import com.shine.models.Customer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.customerRouting() {
    val dao: DaoFacade = DaoFacadeImpl()
    authenticate("user-jwt") {
        route("/customer") {

            get {
                call.respond(mapOf("customer" to dao.allCustomer()))
            }

            get("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(mapOf("customer" to dao.customer(id)))
            }

            post("search") {
                val formParameters = call.receiveParameters()
                val name = formParameters["name"].toString()
                call.respond(mapOf("customer" to dao.searchCustomerByName(name)))
            }

            post {
                val customer = call.receive<Customer>()
                val ans = dao.addNewCustomer(customer)
                call.respond(mapOf("customer" to ans))
            }

            post("edit") {
                val customer = call.receive<Customer>()
                val ans = dao.editCustomer(customer)
                call.respond(if (ans) HttpStatusCode.OK else HttpStatusCode.BadRequest)
            }

            delete("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                call.respond(mapOf("isDelete" to dao.deleteCustomer(id)))
            }

        }
    }
}