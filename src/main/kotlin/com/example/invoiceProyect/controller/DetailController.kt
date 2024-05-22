package com.example.invoiceProyect.controller

import com.example.invoiceProyect.entity.Detail
import com.example.invoiceProyect.service.DetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/detail")
class DetailController(val detailService: DetailService) {

    @GetMapping
    fun list(): ResponseEntity<List<Detail>> {
        val details = detailService.list()
        return ResponseEntity.ok(details)
    }

    @PostMapping
    fun save(@RequestBody detail: Detail): ResponseEntity<Detail> {
        val savedDetail = detailService.save(detail)
        return ResponseEntity(savedDetail, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody detail: Detail): ResponseEntity<Detail> {
        if (id != detail.id) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        val updatedDetail = detailService.update(id, detail)
        return ResponseEntity(updatedDetail, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        detailService.delete(id)
        return ResponseEntity("Detalle eliminado", HttpStatus.OK)
    }
}