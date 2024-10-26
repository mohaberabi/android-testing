package gaur.himanshu.search.ui

import gaur.himanshu.common.utils.NetworkResult
import gaur.himanshu.search.domain.use_cases.DeleteRecipeUseCase
import gaur.himanshu.search.domain.use_cases.GetRecipeDetailsUseCase
import gaur.himanshu.search.domain.use_cases.InsertRecipeUseCase
import gaur.himanshu.search.screens.details.RecipeDetails
import gaur.himanshu.search.screens.details.RecipeDetailsViewModel
import gaur.himanshu.search.ui.generator.FakeRecipeGenerator
import gaur.himanshu.search.ui.rules.MainDispatchersRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RecipeDetailViewModelTest {


    @get:Rule
    val mainDispatchersRule = MainDispatchersRule()
    private lateinit var viewmodel: RecipeDetailsViewModel


    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase = mock()
    private val deleteRecipeUseCase: DeleteRecipeUseCase = mock()
    private val insertRecipeUseCase: InsertRecipeUseCase = mock()


    @Before
    fun setup() {
        viewmodel = RecipeDetailsViewModel(
            getRecipeDetailsUseCase,
            deleteRecipeUseCase,
            insertRecipeUseCase
        )
    }


    @Test
    fun `when event is GoToRecipeListScreen it is collected`() = runTest {

        viewmodel.onEvent(RecipeDetails.Event.GoToRecipeListScreen)
        val result = viewmodel.navigation.first()
        assertEquals(result, RecipeDetails.Navigation.GoToRecipeListScreen)
    }

    @Test
    fun `when event is GoToMediaPlayer it is collected`() = runTest {

        viewmodel.onEvent(RecipeDetails.Event.GoToMediaPlayer("y"))
        val result = viewmodel.navigation.first()
        assertEquals(result, RecipeDetails.Navigation.GoToMediaPlayer("y"))
    }


    @Test

    fun `when addRecipeDetailEvent it is added `() = runTest {

        `when`(insertRecipeUseCase(FakeRecipeGenerator.recipe)).thenReturn(flowOf())


    }

    @Test
    fun `when getRecipe reutns correctly`() = runTest {

        `when`(getRecipeDetailsUseCase("id")).thenReturn(
            flowOf(
                NetworkResult.Success(
                    FakeRecipeGenerator.details
                )
            )
        )
        viewmodel.onEvent(RecipeDetails.Event.FetchRecipeDetails("id"))

        val result = viewmodel.uiState.value.data
        assertEquals(result, FakeRecipeGenerator.details)
    }

}