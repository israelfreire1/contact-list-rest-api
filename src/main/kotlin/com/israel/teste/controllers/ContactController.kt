package com.israel.teste.controllers

import com.israel.teste.entities.Contact
import com.israel.teste.repositories.ContactRepository
import com.israel.teste.services.ContactService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException


@RestController // create a controller
@RequestMapping ("/contacts")//giving a request name for a resource
class ContactController {
    //Spring vai gerenciar as intâncias de repositories
    @Autowired
    lateinit var repository: ContactRepository

    @Autowired
    lateinit var service: ContactService

    @GetMapping
    fun index(): List<Contact> {
        return service.listAllContacts()
    }

    //Do a POST type request at API endpoint
    @PostMapping
    fun create(@RequestBody contact: Contact ): Contact {
        return service.addContact(contact)
    }

    //Do a GET type request at API endpoint
    @GetMapping("/{id}") //Query parameter
    //Getting query parameter through @PathVariable and body JSON through @RequestBody
    fun show(@PathVariable("id") id : Long): Contact {
        //return repository.findById(id).orElseThrow {EntityNotFoundException()}
        return service.showContact(id)
    }

    //Do a PUT type request at API endpoint
    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody newContact: Contact) : Contact{
       return service.alterContact(id, newContact)
    }

    //Do a DELETE type request at API endpoint
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) {
       service.deleteContact(id)
    }
}