package models

import java.time.Instant

object TestDataGenerator {
    fun generateTestPet(): Pet {
        val timestamp = Instant.now().toEpochMilli()
        return Pet(
            id = timestamp,
            category = Pet.Category(
                id = 1,
                name = "Dogs"
            ),
            name = "Doggy$timestamp",
            photoUrls = listOf("http://photos.petstore.com/dog1.jpg"),
            tags = listOf(
                Pet.Tag(
                    id = 1,
                    name = "friendly"
                )
            ),
            status = "available"
        )
    }
}