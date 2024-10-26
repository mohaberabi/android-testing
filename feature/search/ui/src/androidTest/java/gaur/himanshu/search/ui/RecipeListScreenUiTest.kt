package gaur.himanshu.search.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import gaur.himanshu.search.domain.use_cases.GetAllRecipeUseCase
import gaur.himanshu.search.domain.use_cases.GetAllRecipesFromLocalDbUseCase
import gaur.himanshu.search.domain.use_cases.GetRecipeDetailsUseCase
import gaur.himanshu.search.domain.use_cases.InsertRecipeUseCase
import gaur.himanshu.search.screens.recipe_list.RecipeListScreen
import gaur.himanshu.search.screens.recipe_list.RecipeListScreenTestTags
import gaur.himanshu.search.screens.recipe_list.RecipeListViewModel
import gaur.himanshu.search.ui.fakes.FakeRepository
import gaur.himanshu.search.ui.utils.getRecipeResponse
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RecipeListScreenUiTest {


    @get:Rule
    val composeRule = createComposeRule()


    private lateinit var getAllRecipe: GetAllRecipeUseCase

    private lateinit var repo: FakeRepository

    @Before
    fun setup() {
        repo = FakeRepository()
        getAllRecipe = GetAllRecipeUseCase(repo)
    }


    private fun setupComposble() {
        val viewmodel = RecipeListViewModel(getAllRecipe)

        composeRule.setContent {
            val navController = rememberNavController()
            RecipeListScreen(
                viewModel = viewmodel,
                navHostController = navController,
                onClick = {

                },
            )
        }
    }

    @Test
    fun renderCorrect() {
        setupComposble()
        with(composeRule) {
            onNodeWithTag(RecipeListScreenTestTags.SEARCH).performClick()
            onNodeWithTag(RecipeListScreenTestTags.SEARCH).performTextInput("pizza")
            onNodeWithTag(RecipeListScreenTestTags.LAZY_COL).assertIsDisplayed()
            onNodeWithTag(RecipeListScreenTestTags.LAZY_COL).onChildAt(0)
                .assert(hasTestTag(getRecipeResponse().first().strMeal.plus(0)))

        }
    }

    @Test
    fun renderCorrectWhenErorr() {
        repo.setShouldFail(true)
        setupComposble()

        with(composeRule) {
            onNodeWithTag(RecipeListScreenTestTags.SEARCH).performClick()
            onNodeWithTag(RecipeListScreenTestTags.SEARCH).performTextInput("pizza")
            onNodeWithTag(RecipeListScreenTestTags.LAZY_COL).assertDoesNotExist()
        }
    }


}