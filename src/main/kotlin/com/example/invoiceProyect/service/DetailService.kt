package com.example.invoiceProyect.service

import com.example.invoiceProyect.entity.Detail
import com.example.invoiceProyect.entity.Invoice
import com.example.invoiceProyect.repository.DetailRepository
import com.example.invoiceProyect.repository.InvoiceRepository
import com.example.invoiceProyect.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class DetailService {
    @Autowired
    lateinit var detailRepository: DetailRepository

    @Autowired
    lateinit var invoiceRepository: InvoiceRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    fun list(): List<Detail> {
        return detailRepository.findAll()
    }

    fun save(detail: Detail): Detail {
        detail.calculateSubTotal()
        val invoiceId = detail.invoice?.id ?: throw Exception("Invoice ID is required")
        val invoice = findInvoiceById(invoiceId)
        val product = productRepository.findById(detail.product?.id ?: throw Exception("Product ID is required"))
            .orElseThrow { Exception("Product not found with ID: ${detail.product?.id}") }
        detail.invoice = invoice
        detail.product = product
        return detailRepository.save(detail)
    }

    fun update(id: Long, detail: Detail): Detail {
        if (id != detail.id) {
            throw IllegalArgumentException("ID in path variable does not match ID in request body")
        }
        detail.calculateSubTotal()
        val invoice = invoiceRepository.findById(detail.invoice?.id ?: throw Exception("Invoice ID is required"))
            .orElseThrow { Exception("Invoice not found with ID: ${detail.invoice?.id}") }
        val product = productRepository.findById(detail.product?.id ?: throw Exception("Product ID is required"))
            .orElseThrow { Exception("Product not found with ID: ${detail.product?.id}") }
        detail.invoice = invoice
        detail.product = product
        return detailRepository.save(detail)
    }

    fun delete(id: Long) {
        detailRepository.deleteById(id)
    }

    fun findInvoiceById(id: Long): Invoice {
        return invoiceRepository.findById(id)
            .orElseGet {
                // Crea una nueva factura si no se encuentra una con el ID especificado
                val newInvoice = Invoice() // Aquí puedes inicializar los campos de la nueva factura si es necesario
                invoiceRepository.save(newInvoice)
            }
    }
}
