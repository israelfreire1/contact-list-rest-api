package com.israel.teste.repositories

import com.israel.teste.entities.Contact
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
//interface waiting two parameters: entity and primaryKey
interface ContactRepository: JpaRepository<Contact, Long> { //Create a repository using a JpaRepository
    fun existsByNameAndEmail(name: String, email: String): Boolean
    fun existsByName(name: String): Boolean
    fun existsByEmail(email: String): Boolean

}