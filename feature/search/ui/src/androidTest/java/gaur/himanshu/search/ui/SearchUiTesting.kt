package gaur.himanshu.search.ui

import androidx.compose.ui.test.junit4.createComposeRule
import gaur.himanshu.search.domain.use_cases.GetAllRecipeUseCase
import gaur.himanshu.search.domain.use_cases.GetAllRecipesFromLocalDbUseCase
import gaur.himanshu.search.domain.use_cases.GetRecipeDetailsUseCase
import gaur.himanshu.search.domain.use_cases.InsertRecipeUseCase
import gaur.himanshu.search.ui.fakes.FakeRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SearchUiTesting {


    @get:Rule
    val composeRule = createComposeRule()


    private lateinit var getAllRecipe: GetAllRecipeUseCase
    private lateinit var getRecipeDetail: GetRecipeDetailsUseCase
    private lateinit var getAllLocal: GetAllRecipesFromLocalDbUseCase
    private lateinit var insertRecipe: InsertRecipeUseCase

    private lateinit var repo: FakeRepository

    @Before
    fun setup() {
        getRecipeDetail = GetRecipeDetailsUseCase(repo)
        insertRecipe = InsertRecipeUseCase(repo)
        getAllLocal = GetAllRecipesFromLocalDbUseCase(repo)
        getAllRecipe = GetAllRecipeUseCase(repo)
    }


}