package com.israel.teste.controllers

import com.israel.teste.entities.Contact
import com.israel.teste.repositories.ContactRepository
import com.israel.teste.services.ContactService
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping ("/contacts")
class ContactController {

    private val logger = LogManager.getLogger(ContactController::class.java)

    @Autowired
    lateinit var repository: ContactRepository

    @Autowired
    lateinit var service: ContactService

    @GetMapping
    fun index(): List<Contact> {
        return service.listAllContacts()

    }
   
    @PostMapping
    fun create(@RequestBody contact: Contact ): Contact {
        logger.info("Waiting for POST request to /contacts with a CONTACT ins JSON body. Will return status 200 with ${contact} $this")
        return service.createContact(contact)
    }

    @GetMapping("/{id}")
    fun show(@PathVariable("id") id : Long): Contact {
        logger.info("Waiting for GET request to /contacts/${id} with ID in JSON body. Will Return status 200 with user ID in response.${this}")
        return service.showContact(id)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody newContact: Contact) : Contact{
        logger.info("Waiting for PUT request to /contacts/${id} with a new name, email e phone number in JSON body. Will return status 200 with contact saved in repository. ${this}")
       return service.updateContact(id, newContact)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) {
        logger.info("Waiting for DELETE request to /contacts/${id} with valid contact ID in URL. Will return status 200 with no response body. ${this}")
        service.deleteContact(id)
    }
}