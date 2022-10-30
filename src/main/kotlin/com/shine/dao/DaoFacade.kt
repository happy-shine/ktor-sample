package com.shine.dao

import com.shine.models.Customer

interface DaoFacade {

    suspend fun allCustomer(): List<Customer>

    suspend fun customer(id: Int): Customer?

    suspend fun addNewCustomer(customer: Customer): Customer?

    suspend fun editCustomer(customer: Customer): Boolean

    suspend fun deleteCustomer(id: Int): Boolean

    suspend fun searchCustomerByName(name: String): List<Customer>

}