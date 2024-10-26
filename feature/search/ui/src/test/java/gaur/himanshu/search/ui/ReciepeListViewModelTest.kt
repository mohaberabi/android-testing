package gaur.himanshu.search.ui

import gaur.himanshu.common.utils.NetworkResult
import gaur.himanshu.search.domain.use_cases.GetAllRecipeUseCase
import gaur.himanshu.search.screens.recipe_list.RecipeList
import gaur.himanshu.search.screens.recipe_list.RecipeListViewModel
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

class RecipeListViewModelTest {

    @get:Rule
    val mainDispatchersRule = MainDispatchersRule()
    private val getAllRecipes: GetAllRecipeUseCase = mock()
    private lateinit var viewmodel: RecipeListViewModel


    @Before
    fun setup() {
        viewmodel = RecipeListViewModel(getAllRecipes)
    }


    @Test
    fun `when navigation event FavoriteScreen it is collected`() = runTest {

        viewmodel.onEvent(RecipeList.Event.FavoriteScreen)
        assertEquals(viewmodel.navigation.first(), RecipeList.Navigation.GoToFavoriteScreen)
    }

    @Test
    fun `when navigation event  GoToRecipeDetails added it is collected`() = runTest {

        viewmodel.onEvent(RecipeList.Event.GoToRecipeDetails("1"))
        assertEquals(viewmodel.navigation.first(), RecipeList.Navigation.GoToRecipeDetails("1"))
    }

    @Test
    fun `when getAllRecipes`() = runTest {
        val recipes = listOf(FakeRecipeGenerator.recipe)
        `when`(getAllRecipes.invoke("pizza")).thenReturn(
            flowOf(NetworkResult.Success(data = recipes))
        )
        viewmodel.onEvent(RecipeList.Event.SearchRecipe("pizza"))
        val result = viewmodel.uiState.value.data
        assertEquals(recipes, result)
    }
}
