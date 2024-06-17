package com.israel.teste.services

import com.israel.teste.entities.Contact
import com.israel.teste.repositories.ContactRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.jupiter.api.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.stubbing.OngoingStubbing
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.persistence.EntityNotFoundException
import kotlin.jvm.Throws
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.expect


@Tag("uni test")
class ContactServiceTest {

    @MockK
    lateinit var service: ContactService




    @Test
    fun verifyContact() {
        //Arrange
        val repository = mock(ContactRepository::class.java)//cria um mock do repositório
        `when`(repository.existsByNameAndEmail(anyString(), anyString())).thenReturn(true) //Mock do método existsByNameAndEmail
        `when`(repository.existsByName(anyString())).thenReturn(true) //Mock do método existsByName
        `when`(repository.existsByEmail(anyString())).thenReturn(true) //Mock do método existsByEmail

        //Action
        val  contactService = ContactService(repository)//Iniciando o serviço com o mock do repository
        val contact = Contact(1, "Israel", "isantos@gmail.com", "77981460173")
        val exception = try {
            contactService.verifyContact(contact)
            null
        } catch (ex: ResponseStatusException){
            ex
        }
        //Assert
        assert(exception != null)
        assert(exception is ResponseStatusException)
        assert((exception as ResponseStatusException).status == HttpStatus.BAD_REQUEST)
        assert(exception.reason =="Existing name or email")
    }

    @Test
    fun listAllContacts() {
        //Arrange
        val repository = mock(ContactRepository::class.java)
        service = mock(ContactService::class.java)
        val expectedContacts = listOf(
            Contact(1L, "Israel", "israel@gmail.com", "77981460173"),
            Contact(2L, "José", "zdelivery@gmail.com", "77981460176"))
        `when`(repository.findAll()).thenReturn(expectedContacts)

        //Action
        val  actualContacts = repository.findAll()

        //Assert
        Assertions.assertEquals(expectedContacts,actualContacts)
    }

    @Test
    fun addContact(){
        //Arrange
        val repository = mock(ContactRepository::class.java)
        val contact = Contact(1L, "Israel", "isantos@gmail.com", "77981460173")
        `when`(repository.save(contact)).thenReturn(contact)

        //Action
        val savedContact = repository.save(contact)

        //Assert
        verify(repository).save(contact)
        Assertions.assertEquals(contact,savedContact)
    }

    @Test(expected = EntityNotFoundException::class)
    fun showContact() {
        //Arrange
        val repository = Mockito.mock(ContactRepository::class.java)
        val service = Mockito.mock(ContactService::class.java)
        val contact = Contact(1, "Israel Santos", "isantos@gmail.com", "77981460173")

        //Action

        Mockito.`when`(repository.findById(contact.id)).thenReturn(Optional.of(contact))
        val result = service.showContact(contact.id)

        Assertions.assertNotNull(result)
    }

    @Test
    fun alterContact(){
        //Arrange
        val repository = Mockito.mock(ContactRepository::class.java)
        var contact = Contact(1, "Israel Santos", "isantos@gmail.com", "77981460173")
        var newContact = Contact(1, "Israel Santana", "isantos1@gmail.com", "77981460173")

        //Action

            Mockito.`when`(repository.findById(contact.id)).thenReturn(Optional.of(contact))
            contact.apply {
                contact.name = newContact.name
                contact.email = newContact.email
                contact.phone_number = newContact.phone_number
            }
            val result = repository.save(newContact)
            verify(result)
            Assertions.assertNotEquals(newContact.name, contact.name)

    }



    @Test
    fun deleteContact(){
        //Arrange
        val repository = Mockito.mock(ContactRepository::class.java)
        val contact = Contact(1L, "Israel Santos", "isantos@gmail.com", "77981460173")

       //Action

        Mockito.`when`(repository.findById(contact.id)).thenReturn(Optional.of(contact))
        val result = repository.delete(contact)
        assertNull(result)

    }
}

