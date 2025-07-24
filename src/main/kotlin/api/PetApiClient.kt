package org.example.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.example.models.Pet
import org.example.models.PetStatus

/**
 * Клиент для работы с API питомцев PetStore
 *
 * @see <a href="https://petstore.swagger.io">Swagger PetStore API Documentation</a>
 */
class PetApiClient {
    private val baseUrl = "https://petstore.swagger.io/v2"

    @OptIn(ExperimentalSerializationApi::class)
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    explicitNulls = false
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    /**
     * Добавляет нового питомца в магазин
     *
     * @param [pet] Объект питомца для добавления
     * @return Ответ сервера с созданным питомцем
     */
    suspend fun addPet(pet: Pet): HttpResponse {
        return httpClient.post("$baseUrl/pet") {
            contentType(ContentType.Application.Json)
            setBody(pet)
        }
    }

    /**
     * Обновляет существующего питомца
     *
     * @param pet Объект питомца с обновленными данными (должен содержать корректный ID)
     * @return Ответ сервера с обновленным питомцем
     */
    suspend fun updatePet(pet: Pet): HttpResponse {
        return httpClient.put("$baseUrl/pet") {
            contentType(ContentType.Application.Json)
            setBody(pet)
        }
    }

    /**
     * Получает питомца по его идентификатору
     *
     * @param petId Уникальный идентификатор питомца
     * @return Ответ сервера с найденным питомцем
     */
    suspend fun getPetById(petId: Long): HttpResponse {
        return httpClient.get("$baseUrl/pet/$petId")
    }

    /**
     * Удаляет питомца по идентификатору
     *
     * @param petId Уникальный идентификатор питомца для удаления
     * @return Ответ сервера с результатом операции
     */
    suspend fun deletePet(petId: Long): HttpResponse {
        return httpClient.delete("$baseUrl/pet/$petId")
    }

    /**
     * Находит питомцев по статусу
     *
     * @param petStatus Статус питомцев для поиска
     * @return Ответ сервера со списком питомцев
     */
    suspend fun findPetsByStatus(petStatus: PetStatus): HttpResponse {
        return httpClient.get("$baseUrl/pet/findByStatus") {
            parameter("status", petStatus.status)
        }
    }
}