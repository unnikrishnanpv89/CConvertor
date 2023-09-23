package com.open.exchange.cconverter

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.open.exchange.cconverter.presentation.screens.settings.SettingsScreen
import com.open.exchange.cconverter.presentation.screens.settings.SettingsViewModel
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme
import com.open.exchange.data.dao.CurrencyDao
import com.open.exchange.domain.models.Currency
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@HiltAndroidTest
@RunWith(MockitoJUnitRunner::class)
class SettingsScreenTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()
    private lateinit var viewModel: SettingsViewModel
    private lateinit var selectedList: List<Currency>
    private lateinit var allCurrencies: List<Currency>

    @Inject
    lateinit var currencyDao: CurrencyDao

    private val currency1 = Currency(
        currencyName = "Test1",
        currencyCode = "EUD",
        isSelected = true,
        convertRate = 3.67F
    )
    private val currency2 = Currency(
        currencyName = "Test2",
        currencyCode = "AUD",
        isSelected = true,
        convertRate = 1.56F
    )

    private val currency3 = Currency(
        currencyName = "Dollan",
        currencyCode = "USD",
        isSelected = false,
        convertRate = 1.0F
    )

    private val currency4 = Currency(
        currencyName = "Dollan",
        currencyCode = "UNY",
        isSelected = true,
        convertRate = 1.0F
    )

    @Before
    fun init(){
        hiltTestRule.inject()
        runBlocking {
            currencyDao.insertAll(arrayListOf(currency1, currency2, currency3, currency4))
        }
        runBlocking {
            selectedList = currencyDao.getSelectedList(true).first()
            allCurrencies = currencyDao.getList().first()
        }
        TestCase.assertEquals(selectedList.size, 3)
        viewModel = SettingsViewModel(currencyDao = currencyDao)
    }

    /**
     * Make sure title and input text displayed
     */

    @Test
    fun testComposeSettingsScreenInit() {
        // Use Espresso to interact with the UI and verify its behavior
        composeTestRule.setContent {
            CConverterTheme {
                SettingsScreen(viewModel = viewModel)
            }
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.onNodeWithText(context.getString(R.string.search_currency)).assertIsDisplayed()
    }

    /**
     * Make sure currencies are displayed as expected
     */
    @Test
    fun makeSureAllCurrencyDisplayed(){
        composeTestRule.setContent {
            CConverterTheme {
                SettingsScreen(viewModel = viewModel)
            }
        }
        //make sure selected currency are displayed or not
        allCurrencies.forEach {
            composeTestRule.onNodeWithText(it.currencyCode).assertIsDisplayed()
        }
    }

    /**
     * Make sure currencies selected as expected
     */
    @Test
    fun makeSureCurrencySelected(){
        composeTestRule.setContent {
            CConverterTheme {
                SettingsScreen(viewModel = viewModel)
            }
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        //make sure selected currency are displayed or not
        selectedList.forEach {
            composeTestRule.onNodeWithText(it.currencyCode).assertIsDisplayed()
        }
        /**
         * Confirm number of selection is as expected 3
         */
        val nodes = composeTestRule.onAllNodesWithTag(context.getString(R.string.test_selection_tag), true)
        val count = nodes.fetchSemanticsNodes().size
        assertEquals(count, 3)
    }

    /**
     * New selection testing
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun newCurrencySelectionTest(){
        composeTestRule.setContent {
            CConverterTheme {
                SettingsScreen(viewModel = viewModel)
            }
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val nodes = composeTestRule.onAllNodes(hasContentDescription(context.getString(R.string.selected)))
        nodes.assertCountEquals(3)
        //update USD to selected
        runBlocking {
            currencyDao.updateCurrency("USD", true)
            composeTestRule.awaitIdle()
        }
        //wait until all are selected
        composeTestRule.waitUntilNodeCount(hasContentDescription(context.getString(R.string.selected)), 4, 5000)
        //assert that new count is 4
        composeTestRule.onAllNodes(hasContentDescription(context.getString(R.string.selected))).assertCountEquals(4)
    }

    /**
     * Un selection testing
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun removeCurrencySelectionTest(){
        composeTestRule.setContent {
            CConverterTheme {
                SettingsScreen(viewModel = viewModel)
            }
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val nodes = composeTestRule.onAllNodes(hasContentDescription(context.getString(R.string.selected)))
        //assert selection count is by default 3
        nodes.assertCountEquals(3)
        //update AUD to unselected
        runBlocking {
            currencyDao.updateCurrency("AUD", false)
            composeTestRule.awaitIdle()
        }
        //wait until all are selected
        composeTestRule.waitUntilNodeCount(hasContentDescription(context.getString(R.string.selected)), 2, 5000)
        //assert that new count is 2
        composeTestRule.onAllNodes(hasContentDescription(context.getString(R.string.selected))).assertCountEquals(2)
    }

    /**
     * Select All
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun selectAllTest(){
        composeTestRule.setContent {
            CConverterTheme {
                SettingsScreen(viewModel = viewModel)
            }
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val node = composeTestRule.onNodeWithText(context.getString(R.string.select_all))
        //first confirm its displayed
        node.assertIsDisplayed()
        //Also confirm only 3 is selected initially
        composeTestRule.onAllNodes(hasContentDescription(context.getString(R.string.selected))).assertCountEquals(3)
        //click on selectAll button
        node.performClick()
        //wait until all are selected
        composeTestRule.waitUntilNodeCount(hasContentDescription(context.getString(R.string.selected)), 4, 5000)
        //assert that selected count is 4
        composeTestRule.onAllNodes(hasContentDescription(context.getString(R.string.selected))).assertCountEquals(4)
    }

    /**
     * Deselect All
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun deSelectAllTest(){
        composeTestRule.setContent {
            CConverterTheme {
                SettingsScreen(viewModel = viewModel)
            }
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        var node = composeTestRule.onNodeWithText(context.getString(R.string.select_all))
        //first confirm its displayed
        node.assertIsDisplayed()
        //click on selectAll button
        node.performClick()
        //wait until all are selected
        composeTestRule.waitUntilNodeCount(hasContentDescription(context.getString(R.string.selected)), 4, 5000)
        //now the text should change to Deselect All
        node = composeTestRule.onNodeWithText(context.getString(R.string.deselect_all))
        /**
         * confirm changed text button is displayed
         */
        node.assertIsDisplayed()
        //now click on it
        node.performClick()
        //wait until all are unselected
        composeTestRule.waitUntilNodeCount(hasContentDescription(context.getString(R.string.selected)), 0, 5000)
        //assert that selected count is 0
        composeTestRule.onAllNodes(hasContentDescription(context.getString(R.string.selected))).assertCountEquals(0)
    }

}