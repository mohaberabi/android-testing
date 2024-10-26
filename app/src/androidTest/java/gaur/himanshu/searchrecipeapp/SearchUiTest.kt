package gaur.himanshu.searchrecipeapp

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import gaur.himanshu.search.data.di.SearchDataModule
import gaur.himanshu.search.screens.recipe_list.RecipeListScreenTestTags
import gaur.himanshu.searchrecipeapp.utils.getRecipeResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(SearchDataModule::class)
class SearchUiTest {

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)


    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testDoneRenderCorrect() {
        with(composeRule) {
            onNodeWithTag(RecipeListScreenTestTags.SEARCH).performClick()
            onNodeWithTag(RecipeListScreenTestTags.SEARCH).performTextInput("pizza")
            onNodeWithTag(RecipeListScreenTestTags.LAZY_COL).assertIsDisplayed()
            onNodeWithTag(RecipeListScreenTestTags.LAZY_COL).onChildAt(0)
                .assert(hasTestTag(getRecipeResponse().first().strMeal.plus(0)))
        }
    }


}