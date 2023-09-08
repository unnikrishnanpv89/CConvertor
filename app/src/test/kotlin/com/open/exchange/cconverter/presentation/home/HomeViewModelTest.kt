package com.open.exchange.cconverter.presentation.home

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import com.open.exchange.cconverter.presentation.screens.home.HomeScreenViewModel
import com.open.exchange.data.dao.CurrencyDao
import com.open.exchange.data.local.datastore.SharedPrefenceStore
import com.open.exchange.data.local.datastore.SharedPrefenceStore.Companion.DEFAULT_CURRENCY
import com.open.exchange.domain.models.Currency
import com.open.exchange.domain.models.common.Response
import com.open.exchange.domain.repository.OpenExchangeRepository
import com.open.exchange.domain.usecase.convert.GetConvertRateUseCase
import io.mockk.every
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {
    @Mock
    lateinit var openExchangeRespository: OpenExchangeRepository
    @Mock
    lateinit var currencyDao: CurrencyDao
    @Mock
    lateinit var prefenceStore: SharedPrefenceStore

    lateinit var homeScreenViewModel: HomeScreenViewModel
    private lateinit var getConvertRateUseCase: GetConvertRateUseCase
    private val testDispatcher = TestCoroutineDispatcher()

    val currency1 = Currency(
        currencyName = "Test1",
        currencyCode = "EUD",
        isSelected = true,
        convertRate = 3.67F
    )
    val currency2 = Currency(
        currencyName = "Test2",
        currencyCode = "AUD",
        isSelected = true,
        convertRate = 1.56F
    )

    val currency3 = Currency(
        currencyName = "Dollan",
        currencyCode = "USD",
        isSelected = false,
        convertRate = 1.0F
    )


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        getConvertRateUseCase = GetConvertRateUseCase(openExchangeRespository)
        runBlocking {
            Mockito.`when`(currencyDao.getSelectedList())
                .thenReturn(
                    flow {
                        emit(arrayListOf(currency1, currency2))
                    }
                )
            Mockito.`when`(currencyDao.getList())
                .thenReturn(
                    flow {
                        emit(arrayListOf(currency1, currency2, currency3))
                    }
                )
            Mockito.`when`(currencyDao.getRateForCurrency("EUD"))
                .thenReturn(currency1.convertRate)
            Mockito.`when`(currencyDao.getRateForCurrency("AUD"))
                .thenReturn(currency2.convertRate)
            Mockito.`when`(prefenceStore.getStringValueFromDataStore(DEFAULT_CURRENCY))
                .thenReturn(
                    flow {
                        emit("EUD")
                    }
                )
        }
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        homeScreenViewModel = HomeScreenViewModel(currencyDao, prefenceStore,
            getConvertRateUseCase)
    }

    @Test
    fun getAllCurrencyTest() {
        runBlocking {
            Mockito.`when`(getConvertRateUseCase.execute())
                .thenReturn(
                    flow {
                        emit(Response.Success(data = true))
                    }
                )
            val result = homeScreenViewModel.getConvertRate()
            assertNotNull(result)
        }
    }

    @Test
    fun getSelectedList(){
        runBlocking {
            Mockito.`when`(currencyDao.getSelectedList())
                .thenReturn(
                    flow {
                        emit(
                        listOf(currency2))
                    }
                )
            val result = currencyDao.getSelectedList().first()
            assertEquals(result.size, 1)
        }
    }

    /**
     * Main test case where we test proper conversion are happening or not
     */
    @Test
    fun testConversion(){
        val input = 10.0
        val expectedResult = "4.25"
        //4.2734675
        homeScreenViewModel.covertCurrency(TextFieldValue(input.toString()))
        val result = homeScreenViewModel.uiState.value.resultList
        //confirm both two selected result are available
        assertEquals(result.size, 2)
        //verify actual convert rate is same as expected
        val result2 = homeScreenViewModel.uiState.value.resultList[1]
        val actualResult =  String.format("%.2f", result2.result)
        assertEquals(actualResult, expectedResult)
    }

    @After
    fun close() {
        Mockito.clearAllCaches()
    }
}