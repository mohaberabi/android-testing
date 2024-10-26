package gaur.himanshu.search.domain.usecase

import gaur.himanshu.common.utils.NetworkResult
import gaur.himanshu.search.domain.generator.FakeRecipeGenerator
import gaur.himanshu.search.domain.repository.SearchRepository
import gaur.himanshu.search.domain.use_cases.GetAllRecipeUseCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetAllRecipesUseCaseTest {


    private lateinit var repo: SearchRepository
    private lateinit var getAllRecipes: GetAllRecipeUseCase

    @Before
    fun setup() {
        repo = mock()
        getAllRecipes = GetAllRecipeUseCase(repo)
    }


    @Test
    fun ` returns the correct recipes from repository`() = runTest {
        val recipes = listOf(FakeRecipeGenerator.recipe)
        `when`(repo.getAllRecipes()).thenReturn(flowOf(recipes))
        val actual = getAllRecipes("").toList()
        assertTrue(actual[0] is NetworkResult.Loading)
        assertTrue(actual[1] is NetworkResult.Success)

    }
}