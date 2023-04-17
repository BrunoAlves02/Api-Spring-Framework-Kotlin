package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.enums.Role
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
) {

    val customers = mutableListOf<CustomerModel>()

// MÉTODO GETALL PAGINADO
//    fun getAll(name: String?, pageable: Pageable): Page<CustomerModel> {
//        name?.let {
//            return customerRepository.findByNameContaining(it, pageable)
//        }
//
//        return customerRepository.findAll(pageable)
//    }


    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it)
        }

        return customerRepository.findAll().toList()
    }

    fun findCustomerActives(pageable: Pageable): Page<CustomerModel> {
        return customerRepository.findByStatus(CustomerStatus.ATIVO, pageable)
    }

    fun findCustomerInactive(pageable: Pageable): Page<CustomerModel> {
        return customerRepository.findByStatus(CustomerStatus.INATIVO, pageable)
    }

    fun findById(id: Int): CustomerModel {
        return customerRepository.findById(id).orElseThrow{NotFoundException(Errors.ML201.message.format(id),Errors.ML201.code)}
    }

    fun create(customer: CustomerModel) {
        val customerCopy = customer.copy(
            roles = setOf(Role.CUSTOMER),
            password = bCrypt.encode(customer.password)
        )
        customerRepository.save(customerCopy)
    }

    fun update(customer: CustomerModel) {

        if (!customerRepository.existsById(customer.id!!)) {
            throw Exception()
        }

        customerRepository.save(customer)
    }

    fun delete(id: Int) {
        val customer = findById(id)
        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO

        customerRepository.save(customer)
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }

}