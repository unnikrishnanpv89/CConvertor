package com.open.exchange.data

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.open.exchange.data.mappers.currency.CurrencyMapper
import com.open.exchange.domain.models.Currency
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class TestCurrencyMapper {
    private lateinit var currencyMapper: CurrencyMapper
    private lateinit var jsonElement: JsonElement
    val jsonString = """
            {
              "AED": "United Arab Emirates Dirham",
              "AFN": "Afghan Afghani",
              "ALL": "Albanian Lek"
            }
        """
    @Before
    fun setUp(){
        currencyMapper = CurrencyMapper()
        jsonElement = JsonParser().parse(jsonString)
    }

    @Test
    fun mapFromEntity_withValidJsonObject() {

        // Parse the JSON string into a JsonObject
        val jsonObject = jsonElement.asJsonObject

        // Call the mapFromEntity function
        val currencyMapper = CurrencyMapper()
        val result = currencyMapper.mapFromEntity(jsonObject)

        // Define the expected result
        val expectedCurrencyList = listOf(
            Currency( "AED","United Arab Emirates Dirham", false),
            Currency( "AFN", "Afghan Afghani", false),
            Currency("ALL", "Albanian Lek", false)
        )

        // Assert that the result matches the expected result
        assertEquals(expectedCurrencyList, result)
    }

    @Test
    fun mapFromEntity_withEmptyJsonObject() {
        // Create an empty JsonObject
        val jsonObject = JsonObject()

        // Call the mapFromEntity function
        val currencyMapper = CurrencyMapper()
        val result = currencyMapper.mapFromEntity(jsonObject)

        // Define the expected result (an empty list)
        val expectedCurrencyList = emptyList<Currency>()

        // Assert that the result matches the expected result
        assertEquals(expectedCurrencyList, result)
    }
}