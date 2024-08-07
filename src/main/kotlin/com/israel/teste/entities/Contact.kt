package com.israel.teste.entities

import lombok.AllArgsConstructor
import lombok.Data
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts")
 data class Contact(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @field:NotNull
    @field:Size(min = 5, max = 50)
    var name: String,

    @field:NotNull
    @field:Email
    var email: String,

    @NotNull
    @field:Size(max = 11)
    var phone_number: String

)