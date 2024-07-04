package com.israel.teste.services

import com.israel.teste.entities.Contact
import com.israel.teste.repositories.ContactRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.jupiter.api.*
import org.junit.Test
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.test.*

@Tag("uni test")
class ContactServiceTest {
    @MockK
    private lateinit var contactRepository: ContactRepository

    @InjectMockKs
    private lateinit var contactService: ContactService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }


    @Test
    fun listAllContacts() {
        val contactsInDatabase = listOf(
            Contact(1L, "Israel", "israel@gmail.com", "77981460173"),
            Contact(2L, "José", "zdelivery@gmail.com", "77981460176"))
        every {contactRepository.findAll()} returns contactsInDatabase
        val  contactsFound = contactService.listAllContacts()

        Assertions.assertNotNull(contactsFound)
        Assertions.assertEquals(2,contactsFound.size)
    }

    @Test
    fun createContact(){
        val contact = Contact(1L, "Israel", "isantos@gmail.com", "77981460173")
        every {contactRepository.existsByNameAndEmail(contact.name,contact.email)} returns false
        every {contactRepository.existsByName(contact.name)} returns false
        every {contactRepository.existsByEmail(contact.email)} returns false
        every {contactRepository.save(any())} returns contact

        val savedContact = contactService.createContact(contact)

        assertEquals(savedContact,contact)
    }

    @Test
    fun `Add contacts return Exception Bad Request`(){
        val contact = Contact(1L, "Israel", "isantos@gmail.com", "77981460173")
        every { contactRepository.existsByNameAndEmail(contact.name, contact.email)} returns true
        every {contactRepository.existsByName(contact.name)} returns true
        every {contactRepository.existsByEmail(contact.email)} returns true

        val thrown = assertThrows<ResponseStatusException> { contactService.createContact(contact)}

        assertEquals(thrown.message,"${HttpStatus.BAD_REQUEST} \"Existing name or email\"")

    }

    @Test()
    fun showContact() {
        val contact = Contact(1, "Israel Santos", "isantos@gmail.com", "77981460173")
        every {contactRepository.findById(contact.id)}returns (Optional.of(contact))

        val result = contactService.showContact(contact.id)

        Assertions.assertEquals(result,contact)
    }

    @Test
    fun deleteContact(){
        val contact = Contact(1L, "Israel Santos", "isantos@gmail.com", "77981460173")
        every{contactRepository.findById(contact.id)} returns(Optional.of(contact))
        every{contactRepository.delete(contact)} just runs

        val thrown = assertThrows<ResponseStatusException> { contactService.deleteContact(contact.id)}

        assertEquals(thrown.message,"${HttpStatus.OK} \"The contact was deleted\"")

    }

    @Test
    fun updateContact(){
        val contact = Contact(1, "Israel Santos", "isantos@gmail.com", "77981460173")
        val newContact = Contact(1, "Israel Santana", "isantos1@gmail.com", "77981460173")
        every { contactRepository.findById(contact.id)} returns(Optional.of(contact))
        every { contactRepository.save(any())} returns newContact

        val result = contactService.updateContact(contact.id,newContact)

        Assertions.assertNotEquals(result.name, "Israel Santos")
        Assertions.assertEquals(result.id,contact.id)

    }
}

