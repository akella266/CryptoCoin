package ru.akella.cryptocoin

import ru.akella.cryptocoin.db.Breed
import ru.akella.cryptocoin.models.BreedRepository
import ru.akella.cryptocoin.models.BreedViewModel
import ru.akella.cryptocoin.models.CallbackViewModel
import co.touchlab.kermit.Logger

@Suppress("Unused") // Members are called from Swift
class BreedCallbackViewModel(
    breedRepository: BreedRepository,
    log: Logger
) : CallbackViewModel() {

    override val viewModel = BreedViewModel(breedRepository, log)

    val breeds = viewModel.breedState.asCallbacks()

    fun refreshBreeds() {
        viewModel.refreshBreeds()
    }

    fun updateBreedFavorite(breed: Breed) {
        viewModel.updateBreedFavorite(breed)
    }
}
