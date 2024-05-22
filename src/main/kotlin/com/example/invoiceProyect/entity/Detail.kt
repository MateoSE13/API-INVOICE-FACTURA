package com.example.invoiceProyect.entity

import jakarta.persistence.*

@Entity
data class Detail(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var quantity: Int = 0,
    var price: Double = 0.0,
    var subtotal: Double = 0.0,

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    var invoice: Invoice? = null,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null
) {
    fun calculateSubTotal() {
        subtotal = quantity * price
    }
}

