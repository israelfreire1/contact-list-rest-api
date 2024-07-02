package com.israel.teste.controllers

import com.israel.teste.entities.Contact
import com.israel.teste.repositories.ContactRepository
import com.israel.teste.services.ContactService
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController



@RestController // create a controller
@RequestMapping ("/contacts")//giving a request name for a resource
@Tag(name = "Contact list", description = "Api to store contacts")
class ContactController {

    private val logger = LogManager.getLogger(ContactController::class.java)

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
        logger.info("Waiting for POST request to /contacts with a CONTACT ins JSON body. Will return status 200 with ${contact} $this")
        return service.addContact(contact)
    }

    //Do a GET type request at API endpoint
    @GetMapping("/{id}") //Query parameter
    //Getting query parameter through @PathVariable and body JSON through @RequestBody
    fun show(@PathVariable("id") id : Long): Contact {
        //return repository.findById(id).orElseThrow {EntityNotFoundException()}
        logger.info("Waiting for GET request to /contacts/${id} with ID in JSON body. Will Return status 200 with user ID in response.${this}")
        return service.showContact(id)
    }

    //Do a PUT type request at API endpoint
    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody newContact: Contact) : Contact{
        logger.info("Waiting for PUT request to /contacts/${id} with a new name, email e phone number in JSON body. Will return status 200 with contact saved in repository. ${this}")
       return service.alterContact(id, newContact)
    }

    //Do a DELETE type request at API endpoint
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) {
        logger.info("Waiting for DELETE request to /contacts/${id} with valid contact ID in URL. Will return status 200 with no response body. ${this}")
        service.deleteContact(id)
    }
}