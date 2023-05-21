package ru.akella.cryptocoin.data.api

import ru.akella.cryptocoin.data.response.BreedResult

interface DogApi {
    suspend fun getJsonFromApi(): BreedResult
}
