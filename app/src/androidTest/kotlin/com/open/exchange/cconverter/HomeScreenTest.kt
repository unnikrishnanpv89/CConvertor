package com.open.exchange.cconverter

import android.os.SystemClock
import android.util.Log
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.open.exchange.cconverter.presentation.screens.home.HomeScreen
import com.open.exchange.cconverter.presentation.screens.home.HomeScreenViewModel
import com.open.exchange.data.dao.CurrencyDao
import com.open.exchange.data.local.datastore.SharedPrefenceStore
import com.open.exchange.domain.models.Currency
import com.open.exchange.domain.usecase.convert.GetConvertRateUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()
    private lateinit var viewModel: HomeScreenViewModel

    @Inject
    lateinit var currencyDao: CurrencyDao
    @Inject
    lateinit var preferenceStore: SharedPrefenceStore
    @Inject
    lateinit var getConvertRateUseCase: GetConvertRateUseCase

    private lateinit var selectedList: List<Currency>

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

    /**
     * Make sure db insert works and selected list size is as expected
     */
    @Before
    fun init(){
        hiltTestRule.inject()
        runBlocking {
            currencyDao.insertAll(arrayListOf(currency1, currency2, currency3, currency4))
        }
        runBlocking {
            selectedList = currencyDao.getSelectedList(true).first()
        }
        assertEquals(selectedList.size, 3)
        viewModel = HomeScreenViewModel(currencyDao = currencyDao,
            preferenceStore = preferenceStore,
            getConvertRateUseCase = getConvertRateUseCase)
    }

    /**
     * Make sure title and input text displayed
     */

    @Test
    fun testComposeHomeScreenInit() {
        // Use Espresso to interact with the UI and verify its behavior
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel)
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.onNodeWithText(context.getString(R.string.home_title)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.currency_label)).assertIsDisplayed()
    }

    /**
     * Make sure selections are saved as expected
     */
    @Test
    fun makeSureAllSelectedCurrencyDisplayed(){
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel)
        }
        //make sure selected currency are displayed or not
        selectedList.forEach {
            composeTestRule.onNodeWithText(it.currencyCode).assertIsDisplayed()
        }
    }

    /**
     * Make sure UI doesnt break on text input and currency input
     */
    @Test
    fun testCurrencySelection() {
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel)
        }

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.onNodeWithText(context.getString(R.string.currency_label))
            .performTextInput("JPY: Japanese Yen")
        composeTestRule.onNodeWithText(context.getString(R.string.textfield_hint)).performTextInput("100")
    }

    /**
     * make sure conversions are not null on valid input
     */
    @Test
    fun testConversion(){
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel)
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeTestRule.onNodeWithText(context.getString(R.string.textfield_hint)).performTextInput("100")
        //make sure selected currency are displayed and not zero
        val nodes = composeTestRule.onAllNodesWithTag(context.getString(R.string.test_result))
        nodes.fetchSemanticsNodes().forEach{
            it.config.getOrNull(SemanticsProperties.Text)?.get(0)?.let { result ->
                Log.i(
                    "TEST", "Result is $result"
                )
                assertTrue(result.isNotEmpty()) }
        }
    }

    /**
     * make sure conversions are null on no input
     */
    @Test
    fun testConversionOnNoInput(){
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel)
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeTestRule.onNodeWithText(context.getString(R.string.textfield_hint)).performTextInput("")
        //make sure selected currency are displayed and not zero
        val nodes = composeTestRule.onAllNodesWithTag(context.getString(R.string.test_result))
        nodes.fetchSemanticsNodes().forEach{
            it.config.getOrNull(SemanticsProperties.Text)?.get(0)?.let { result ->
                Log.i(
                    "TEST", "Result is $result"
                )
                assertTrue(result.isEmpty()) }
        }
    }


    /**
     * make sure conversions is done for newly added currency
     */
    @Test
    fun testConversionWithNewCurrency(){
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel)
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeTestRule.onNodeWithText(context.getString(R.string.textfield_hint)).performTextInput("100")
        var nodes = composeTestRule.onAllNodesWithTag(context.getString(R.string.test_result))
        var count = nodes.fetchSemanticsNodes().size
        /**
         * By default there should be only 3 currencies in selected
         * hence count should be true
         */
        assertEquals(count, 3)

        /**
         *  now add new selected currency(USD) which is not selected now
         *  and confirm conversion is calculated for it in UI
         */
        runBlocking {
            currencyDao.updateCurrency("USD", true)
            SystemClock.sleep(2000)
        }
        nodes = composeTestRule.onAllNodesWithTag(context.getString(R.string.test_result))
        count = nodes.fetchSemanticsNodes().size
        /**
         * Since we added one more, there should be 4 currencies
         */
        assertEquals(count, 4)
        /**
         * Finally confirm all 4 text has conversion result non empty
         */
        nodes.fetchSemanticsNodes().forEach{
            it.config.getOrNull(SemanticsProperties.Text)?.get(0)?.let { result ->
                Log.i(
                    "TEST", "Result is $result"
                )
                assertTrue(result.isNotEmpty()) }
        }
    }


    /**
     * make sure conversions is done for newly added currency
     */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testConversionWithUnSelectCurrency(){
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel)
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.onNodeWithText(context.getString(R.string.textfield_hint))
            .performTextInput("100")
        runBlocking {
            val codeList = currencyDao.getSelectedList().first().map { it.currencyCode }
            codeList.forEach {
                composeTestRule.onNodeWithText(it).assertIsDisplayed()
            }
        }
        /**
         *  now add new selected currency(USD) which is not selected now
         *  and confirm conversion is calculated for it in UI
         */
        runBlocking {
            currencyDao.updateCurrency("EUD", false)
            composeTestRule.waitUntilDoesNotExist(hasText("EUD"), 25000)
        }
        //make sure EUD is not displayed
        composeTestRule.onNodeWithText("EUD").assertDoesNotExist()
        /**
         * Finally confirm all remaining 2 text has conversion result non empty
         */
        val nodes = composeTestRule.onAllNodesWithTag(context.getString(R.string.test_result))
        nodes.fetchSemanticsNodes().forEach {
            it.config.getOrNull(SemanticsProperties.Text)?.get(0)?.let { result ->
                assertTrue(result.isNotEmpty())
            }
        }
    }
}

