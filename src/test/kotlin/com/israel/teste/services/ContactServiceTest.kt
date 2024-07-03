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
    //Geralmente fazemos um mock geral para evitar a repetição do código em varios testes e paupar tempo
    @MockK
    private lateinit var contactRepository: ContactRepository

    @InjectMockKs
    private lateinit var contactService: ContactService

    //inicia os mocks
    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

// Dan: Não é muito necessário nesse momento esse teste visto que já vamos testar esse trecho quando testarmos o addContact
//    @Test
//    fun verifyContact() {
//        //Arrange
//        val repository = mock(ContactRepository::class.java)//cria um mock do repositório
//        `when`(repository.existsByNameAndEmail(anyString(), anyString())).thenReturn(true) //Mock do método existsByNameAndEmail
//        `when`(repository.existsByName(anyString())).thenReturn(true) //Mock do método existsByName
//        `when`(repository.existsByEmail(anyString())).thenReturn(true) //Mock do método existsByEmail
//
//        //Action
//        val  contactService = ContactService(repository)//Iniciando o serviço com o mock do repository
//        val contact = Contact(1, "Israel", "isantos@gmail.com", "77981460173")
//        val exception = try {
//            contactService.verifyContact(contact)
//            null
//        } catch (ex: ResponseStatusException){
//            ex
//        }
//        //Assert
//        assert(exception != null)
//        assert(exception is ResponseStatusException)
//        assert((exception as ResponseStatusException).status == HttpStatus.BAD_REQUEST)
//        assert(exception.reason =="Existing name or email")
//    }

    @Test
    //Nome de teste geralmente damos algo que explicite o cenário testado. EX; ListAllContactsOk ou ListAllContactsFail
    fun listAllContacts() {
        //Arrange
        //Aqui mockamos 2 contatos
        val contactsInDatabase = listOf(
            Contact(1L, "Israel", "israel@gmail.com", "77981460173"),
            Contact(2L, "José", "zdelivery@gmail.com", "77981460176"))
        //Aqui estabelecemos o que esperamos de retorno
        every {contactRepository.findAll()} returns contactsInDatabase
        //Action
        //aqui chamamos exatamente o que estamos esperando
        val  contactsFound = contactService.listAllContacts()
        //Assert
        //Aqui validamos que foi encontrado algo, no caso os contatos mockados
        Assertions.assertNotNull(contactsFound)
        //Aqui validamos se o numero de contatos encontras foi o esperado
        Assertions.assertEquals(2,contactsFound.size)
    }

    @Test
    fun addContact(){
        //Arrange
        val contact = Contact(1L, "Israel", "isantos@gmail.com", "77981460173")
        every {contactRepository.existsByNameAndEmail(contact.name,contact.email)} returns false
        every {contactRepository.existsByName(contact.name)} returns false
        every {contactRepository.existsByEmail(contact.email)} returns false
        every {contactRepository.save(any())} returns contact

        //Action
        val savedContact = contactService.addContact(contact)

        //Assert
        assertEquals(savedContact,contact)
    }

    @Test
    fun `Add contacts return Exception Bad Request`(){
        //Arrange
        val contact = Contact(1L, "Israel", "isantos@gmail.com", "77981460173")
        every { contactRepository.existsByNameAndEmail(contact.name, contact.email)} returns true
        every {contactRepository.existsByName(contact.name)} returns true
        every {contactRepository.existsByEmail(contact.email)} returns true
        //Action

        val thrown = assertThrows<ResponseStatusException> { contactService.addContact(contact)}
        //Assert
        assertEquals(thrown.message,"${HttpStatus.BAD_REQUEST} \"Existing name or email\"")

    }


    @Test()
    fun showContact() {
        //Arrange
        val contact = Contact(1, "Israel Santos", "isantos@gmail.com", "77981460173")
        every {contactRepository.findById(contact.id)}returns (Optional.of(contact))
        //Action
        val result = contactService.showContact(contact.id)
        //Assert
        Assertions.assertEquals(result,contact)
    }

    @Test
    fun deleteContact(){
        //Arrange
        val contact = Contact(1L, "Israel Santos", "isantos@gmail.com", "77981460173")
        every{contactRepository.findById(contact.id)} returns(Optional.of(contact))
        every{contactRepository.delete(contact)} just runs
        //Action
        val thrown = assertThrows<ResponseStatusException> { contactService.deleteContact(contact.id)}
        //Assert
        assertEquals(thrown.message,"${HttpStatus.OK} \"The contact was deleted\"")
    }


     //\"The contact was deleted \"
    @Test
    fun updateContact(){
        //Arrange

        val contact = Contact(1, "Israel Santos", "isantos@gmail.com", "77981460173")
        val newContact = Contact(1, "Israel Santana", "isantos1@gmail.com", "77981460173")
        every { contactRepository.findById(contact.id)} returns(Optional.of(contact))
        every { contactRepository.save(any())} returns newContact
        //Action
        val result = contactService.updateContact(contact.id,newContact)
        //Assert
        Assertions.assertNotEquals(result.name, "Israel Santos")
        Assertions.assertEquals(result.id,contact.id)

    }
}

