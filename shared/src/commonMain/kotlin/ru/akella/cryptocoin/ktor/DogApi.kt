package ru.akella.cryptocoin.ktor

import ru.akella.cryptocoin.response.BreedResult

interface DogApi {
    suspend fun getJsonFromApi(): BreedResult
}
