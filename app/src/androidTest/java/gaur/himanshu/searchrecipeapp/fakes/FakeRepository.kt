package gaur.himanshu.searchrecipeapp.fakes

import gaur.himanshu.search.domain.model.Recipe
import gaur.himanshu.search.domain.model.RecipeDetails
import gaur.himanshu.search.domain.repository.SearchRepository
import gaur.himanshu.searchrecipeapp.utils.getRecipeDetailsList
import gaur.himanshu.searchrecipeapp.utils.getRecipeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeRepository : SearchRepository {
    private var shouldFail: Boolean = false

    val error = RuntimeException("error")
    private val _recipes = mutableListOf<Recipe>()

    val recipes = _recipes.toList()
    private val flow = MutableStateFlow(recipes)


    fun setShouldFail(fail: Boolean) {
        shouldFail = fail
    }

    override suspend fun getRecipes(
        s: String,
    ): Result<List<Recipe>> = fakeResult {
        getRecipeResponse()
    }

    override suspend fun getRecipeDetails(
        id: String,
    ): Result<RecipeDetails> = fakeResult {
        getRecipeDetailsList().first()
    }

    override suspend fun insertRecipe(
        recipe: Recipe,
    ) {
        fakeResult {
            _recipes.add(recipe)
        }
    }

    override suspend fun deleteRecipe(
        recipe: Recipe,
    ) {
        fakeResult {
            _recipes.remove(recipe)
        }
    }

    override fun getAllRecipes(): Flow<List<Recipe>> = flow

    private inline fun <reified T> fakeResult(
        done: () -> T
    ): Result<T> {
        return if (shouldFail) {
            Result.failure(error)
        } else {
            Result.success(done())
        }
    }
}