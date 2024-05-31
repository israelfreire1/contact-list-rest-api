package com.israel.teste.services

import com.israel.teste.entities.Contact
import com.israel.teste.repositories.ContactRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ContactService(private val repository: ContactRepository)  {
    fun addContact(contact: Contact): Contact{
        if (repository.existsByNameAndEmail(contact.name, contact.email))
        {

            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Existing name or email"
            )
        }
        else if(repository.existsByName(contact.name))
        {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Existing name or email"
            )
        }
        else if(repository.existsByEmail(contact.email))
        {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Existing name or email"
            )
        }
        return repository.save(contact)
    }
}