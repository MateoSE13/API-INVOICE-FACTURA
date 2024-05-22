package com.example.invoiceProyect.service

import com.example.invoiceProyect.entity.Invoice
import com.example.invoiceProyect.repository.InvoiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class InvoiceService {
    @Autowired
    lateinit var invoiceRepository: InvoiceRepository

    fun list(): List<Invoice> {
        return invoiceRepository.findAll()
    }

    fun save(invoice: Invoice): Invoice {
        return invoiceRepository.save(invoice)
    }

    fun update(invoice: Invoice): Invoice {
        try {
            invoiceRepository.findById(invoice.id)?: throw Exception("Id no Encontrado")
            return invoiceRepository.save(invoice)
        }catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun updateTotal(invoice: Invoice): Invoice {
        try {

            var response = invoiceRepository.findById(invoice.id) ?: throw Exception("Ya existe este ID")
            response.apply {
                total=invoice.total

            }
            return invoiceRepository.save(response)
        }
        catch(ex:Exception){
            throw  ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun  delete(id: Long) {
        try {

            var response = invoiceRepository.findById(id).orElseThrow{throw ResponseStatusException(HttpStatus.NOT_FOUND, "No Existe con el Id:  $id")}
            invoiceRepository.delete(response)
        }
        catch(ex:Exception){
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar", ex)
        }
    }

}