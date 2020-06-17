package com.victor.api.dataprovider

import com.victor.api.dataprovider.implementation.PersonServiceImpl
import com.victor.api.dataprovider.model.response.Person
import com.victor.api.dataprovider.repository.PersonRepository
import net.serenitybdd.junit.runners.SerenityRunner
import net.serenitybdd.junit.spring.integration.SpringIntegrationMethodRule
import net.thucydides.core.annotations.WithTag
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@RunWith(SerenityRunner::class)
@WithTag("Unity")
@EnableAutoConfiguration(exclude = [HibernateJpaAutoConfiguration::class])
@EnableMongoRepositories
class PersonServiceImplTest {

    @get:Rule
    var springMethodIntegration = SpringIntegrationMethodRule()

    lateinit var personServiceImpl: PersonServiceImpl

    @MockBean
    private lateinit var personRepository: PersonRepository

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        personServiceImpl = PersonServiceImpl(personRepository)
    }

    @Test
    fun `test should return correct data from database`() {
        Mockito.`when`(personRepository.findAll()).thenReturn(listOf(Person(id = "999", firstName = "Victor", lastName = "Tripeno")))
        val persons = personServiceImpl.findAll()
        Assert.assertEquals("999", persons[0].id)
        Assert.assertEquals("Victor", persons[0].firstName)
        Assert.assertEquals("Tripeno", persons[0].lastName)
    }
}