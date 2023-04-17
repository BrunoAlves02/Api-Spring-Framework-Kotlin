package com.mercadolivro.repository

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<CustomerModel, Int> {

// MÉTODO PAGINADO
//    fun findByNameContaining(name: String, pageable: Pageable) : Page<CustomerModel>
    fun findByNameContaining(name: String) : List<CustomerModel>

    fun findByStatus(status: CustomerStatus, pageable: Pageable): Page<CustomerModel>

    override fun findAll(pageable: Pageable): Page<CustomerModel>
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): CustomerModel

}