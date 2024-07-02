package com.israel.teste.services

import com.israel.teste.entities.Contact
import com.israel.teste.repositories.ContactRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.persistence.EntityNotFoundException

@Service
class ContactService(private val repository: ContactRepository)  {

    fun verifyContact(contact: Contact){
      if(repository.existsByNameAndEmail(contact.name, contact.email) ||
          repository.existsByName(contact.name) ||
          repository.existsByEmail(contact.email)){
          throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Existing name or email")
      }
    }

    fun listAllContacts(): List<Contact>{
        return repository.findAll()
    }

    fun addContact(contact: Contact):Contact{
        verifyContact(contact)
        return repository.save(contact)
    }


    fun showContact(id: Long): Contact{
        return repository.findById(id).orElseThrow { EntityNotFoundException()}
    }

    fun alterContact(id:Long, newContact: Contact): Contact{
        val contact = repository.findById(id).orElseThrow {EntityNotFoundException()}
        contact.apply {
            this.name = newContact.name
            this.email = newContact.email
            this.phone_number = newContact.phone_number
        }
       return repository.save(contact)
    }

    fun deleteContact(id: Long){
        val contact = repository.findById(id).orElseThrow {EntityNotFoundException()}
        repository.delete(contact)
    }
}