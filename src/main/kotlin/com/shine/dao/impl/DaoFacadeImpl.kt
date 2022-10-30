package com.shine.dao.impl

import com.shine.dao.DaoFacade
import com.shine.models.Customer
import com.shine.models.Customers
import com.shine.util.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*


class DaoFacadeImpl : DaoFacade {

    private fun resultRowToCustomer(row: ResultRow) = Customer(
        id = row[Customers.id],
        name = row[Customers.name],
        age = row[Customers.age],
        gender = row[Customers.gender]
    )

    override suspend fun allCustomer(): List<Customer> = dbQuery {
        Customers
            .selectAll()
            .map(::resultRowToCustomer)
    }

    override suspend fun customer(id: Int): Customer? = dbQuery {
        Customers
            .select { Customers.id eq id }
            .map(::resultRowToCustomer)
            .singleOrNull()
    }

    override suspend fun addNewCustomer(customer: Customer): Customer? = dbQuery {
        val insertStatement = Customers.insert {
            it[name] = customer.name
            it[age] = customer.age
            it[gender] = customer.gender
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCustomer)
    }

    override suspend fun editCustomer(customer: Customer): Boolean = dbQuery {
        Customers.update({ Customers.id eq customer.id }) {
            it[Customers.name] = customer.name
            it[Customers.age] = customer.age
            it[Customers.gender] = customer.gender
        } > 0
    }

    override suspend fun deleteCustomer(id: Int): Boolean = dbQuery {
        Customers.deleteWhere { Customers.id eq id } > 0
    }

    override suspend fun searchCustomerByName(name: String): List<Customer> = dbQuery {
        Customers
            .select { Customers.name eq name }
            .map(::resultRowToCustomer)
    }
}
