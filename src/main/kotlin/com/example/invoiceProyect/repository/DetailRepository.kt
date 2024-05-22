package com.example.invoiceProyect.repository

import com.example.invoiceProyect.entity.Detail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DetailRepository : JpaRepository<Detail, Long> {
    fun findByInvoiceId(invoiceId: Long): List<Detail>
    fun findByProductId(productId: Long): List<Detail>
}
