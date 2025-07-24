package org.example.tests.pet

import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.test.runTest
import org.example.api.PetApiClient
import org.example.extensions.step
import org.example.models.InfoResponse
import org.example.models.Pet
import org.example.models.TestDataGenerator
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals


@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PetApiTests {

    companion object {
        private lateinit var petApiClient: PetApiClient

        /**
         * Инициализация клиента API перед всеми тестами.
         * Вызывается один раз перед выполнением всех тестов в классе.
         */
        @BeforeAll
        @JvmStatic
        fun setup() {
            petApiClient = PetApiClient()
        }
    }

    /**
     * Тест создания нового питомца.
     */
    @Test
    fun createPetTest() = runTest {
        val testPet = TestDataGenerator.generateTestPet()
        lateinit var createdPet: Pet

        step("Создаём нового питомца") {
            val response = petApiClient.addPet(testPet)
            assertEquals(200, response.status.value)
        }

        step("Находим созданного питомца по id: '${testPet.id}' и проверяем данные о нём") {
            createdPet = petApiClient.getPetById(testPet.id ?: -1).body<Pet>()
            assertEquals(testPet, createdPet)
        }
    }

    /**
     * Тест получения информации о питомце по ID.
     */
    @Test
    fun getPetByIdTest() = runTest {
        val testPet = addNewPet()

        step("Находим питомца по id: '${testPet.id}' и проверяем данные о нём") {
            val response = petApiClient.getPetById(testPet.id ?: -1)

            Assertions.assertEquals(200, response.status.value)

            val retrievedPet = response.body<Pet>()
            assertEquals(testPet, retrievedPet)
        }
    }

    /**
     * Тест удаления питомца.
     */
    @Test
    fun deletePetTest() = runTest {
        val testPet = addNewPet()

        step("Удаляем созданного питомца по id: '${testPet.id}' и проверяем статус удаления") {
            val deleteResponse = petApiClient.deletePet(testPet.id ?: -1)
            assertEquals(200, deleteResponse.status.value)
        }

        step("Проверяем, что питомца не получается найти по id: '${testPet.id}'") {
            val response = petApiClient.getPetById(testPet.id ?: -1)
            assertEquals(404, response.status.value)
            val infoResponse = response.body<InfoResponse>()
            assertEquals("Pet not found", infoResponse.message)
        }
    }

    /**
     * Тест обновления информации о питомце.
     */
    @Test
    fun updatePetTest() = runTest {
        var testPet = addNewPet()
        lateinit var response: HttpResponse
        lateinit var updatedPet: Pet

        step("Обновляем имя питомца") {
            updatedPet = testPet.copy(name = testPet.name + " updatedName")
            response = petApiClient.updatePet(updatedPet)
        }

        step("Проверяем изменение имени питомца") {
            assertEquals(200, response.status.value)
            testPet = response.body<Pet>()
            assertEquals(updatedPet.name, testPet.name)
        }
    }

    /**
     * Вспомогательный метод для создания нового тестового питомца.
     *
     * @return Созданного питомца
     */
    private suspend fun addNewPet(): Pet {
        val testPet = TestDataGenerator.generateTestPet()

        step("Создаём нового питомца") {
            val addPetCode = petApiClient.addPet(testPet).status.value
            assertEquals(200, addPetCode)
        }

        return testPet
    }
}