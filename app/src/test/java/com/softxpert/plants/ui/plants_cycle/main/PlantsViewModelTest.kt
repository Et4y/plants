import androidx.paging.PagingData
import com.softxpert.plants.MainDispatcherRule
import com.softxpert.plants.domain.model.plants.Links
import com.softxpert.plants.domain.model.plants.PlantModel
import com.softxpert.plants.domain.repo.PlantsRepository
import com.softxpert.plants.domain.util.UiState
import com.softxpert.plants.ui.plants_cycle.main.PlantsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class PlantsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)


    private val repository: PlantsRepository = mockk(relaxed = true)
    private var viewModel: PlantsViewModel = PlantsViewModel(repository)


    // Arrange
    private val mockPlantModel = PlantModel(
        id = 1,
        commonName = "Mock Plant",
        slug = "mock-plant",
        scientificName = "Mockus plantus",
        year = 2021,
        bibliography = "Mock bibliography",
        author = "Mock Author",
        status = "accepted",
        rank = "species",
        familyCommonName = "Mock family",
        genusId = 1,
        imageUrl = "https://example.com/mock-plant.jpg",
        synonyms = listOf("Mock plant", "Fake plant"),
        genus = "Mockus",
        family = "Mockaceae",
        links = Links(
            self = "https://example.com/mock-plant",
            plant = "https://example.com/mock-plant",
            genus = "https://example.com/mock-genus"
        )
    )

    @Test
    fun `test success with non-empty Data`() =testScope.runTest {

        // Arrange
        val pagingData = PagingData.from(listOf(mockPlantModel))
        coEvery { repository.getPlants() } returns flowOf(pagingData)


        // Act
        viewModel.getData()


        // Assert
        val job = launch {
            viewModel.uiState.collectLatest { uiState ->
                if (uiState is UiState.Success) {
                    assertEquals(uiState.data, pagingData)
                }
            }
        }

        // Wait for coroutine execution
        advanceUntilIdle()

        // Clean up
        job.cancel()
    }


    @Test
    fun `test getData error flow`() = testScope.runTest {
        // Arrange
        val exception = Exception("Network Error")
        coEvery { repository.getPlants() } returns flow { throw exception }

        // Act
        viewModel.getData()

        // Assert
        val job = launch {
            viewModel.uiState.collectLatest {
                if (it is UiState.Error) {
                    assertEquals(it.throwable.message, exception.message ?: "")
                }
            }
        }

        // Wait for coroutine execution
        advanceUntilIdle()

        // Clean up
        job.cancel()
    }

}

