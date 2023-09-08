package com.open.exchange.cconverter.presentation.home

import com.open.exchange.cconverter.presentation.screens.currencyselection.CurrencySelectionViewModel
import com.open.exchange.data.dao.CurrencyDao
import com.open.exchange.data.local.datastore.SharedPrefenceStore
import com.open.exchange.data.local.datastore.SharedPrefenceStore.Companion.IS_FIRSTTIME
import com.open.exchange.domain.models.Currency
import com.open.exchange.domain.models.common.Response
import com.open.exchange.domain.usecase.convert.GetConvertRateUseCase
import com.open.exchange.domain.usecase.currency.GetCurrencyListUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TestCurrencySelectionViewModel {
    @Mock
    lateinit var getCurrencyListUseCase: GetCurrencyListUseCase
    @Mock
    lateinit var getConvertRateUseCase: GetConvertRateUseCase
    @Mock
    lateinit var currencyDao: CurrencyDao
    @Mock
    lateinit var  preferenceStore: SharedPrefenceStore
    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var currencySelectionViewModel: CurrencySelectionViewModel

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

    val currency4 = Currency(
        currencyName = "Dollan",
        currencyCode = "USD",
        isSelected = true,
        convertRate = 1.0F
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        runBlocking {
            Mockito.`when`(currencyDao.getSelectedList())
                .thenReturn(
                    flow {
                        emit(arrayListOf(currency1, currency2))
                    }
                )
            Mockito.`when`(getCurrencyListUseCase.execute())
                .thenReturn(
                    flow {
                        emit(Response.Success(data = true))
                    }
                )
            Mockito.`when`(currencyDao.getList())
                .thenReturn(
                    flow {
                        emit(arrayListOf(currency1, currency2, currency3))
                    }
                ).thenReturn(
                    flow {
                        emit(arrayListOf(currency1, currency2, currency4))
                    }
                )
        }

        currencySelectionViewModel = CurrencySelectionViewModel(
            getCurrencyListUseCase,
            getConvertRateUseCase,
            currencyDao,
            preferenceStore
        )
    }

    @Test
    fun testSelection(){
        //first time it should be false
        val result = currencySelectionViewModel.uiState.value.isSelectedAll
        assertEquals(result, false)
    }

    @Test
    fun testIfFirstTime(){
        runBlocking {
            //it should be false already
            val result = preferenceStore.getBoolValueFromDataStore(IS_FIRSTTIME).first()
            assertEquals(result, false)
            //check if true after
            preferenceStore.saveBoolToDataStore(IS_FIRSTTIME, true)
            assertEquals(result, true)
        }
    }

    @Test
    fun testGetCurrencyList(){
        currencySelectionViewModel.getCurrencyList {
            assertEquals(it, true)
        }
        val list = currencySelectionViewModel.uiState.value.currencyList
        assertEquals(list.size, 3)
    }


}