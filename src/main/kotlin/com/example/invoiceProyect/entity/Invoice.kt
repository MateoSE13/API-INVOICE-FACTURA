package com.example.invoiceProyect.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "invoice")
class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    var id: Long? = null
    var code: String? = null
    @Column(name = "created_at") // Corregido el nombre de la columna
    var createdAt: Date? = null
    var total: Double? = null
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    var client: Client? = null
}
