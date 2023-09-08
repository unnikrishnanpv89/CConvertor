package com.open.exchange.data

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.open.exchange.data.mappers.convert.ConvertMapper
import com.open.exchange.data.models.ConvertModel
import com.open.exchange.domain.models.ConvertModelD
import com.open.exchange.domain.models.ConvertRateD
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class TestConvertMapper {

    private lateinit var convertMapper: ConvertMapper
    private lateinit var jsonElement1: JsonElement
    private lateinit var jsonElement2: JsonElement
    val validString = "\"{USD:1.0,EUR:0.85}\""
    val emptyList = "{}"
    @Before
    fun setup() {
        convertMapper = ConvertMapper()
        jsonElement1 = JsonParser().parse(validString)
        jsonElement2 = JsonParser().parse(emptyList)
    }

    @Test
    fun mapFromEntity_withValidData() {
        // Create a ConvertModel with some sample data
        val convertModel = ConvertModel(
            rates = jsonElement1.asJsonObject,
            timestamp = 1631020212,
            base = String(),
            disclaimer = "Disclaimer",
            license = "No License"
        )

        // Call the mapFromEntity function to convert it
        val result = convertMapper.mapFromEntity(convertModel)

        // Define the expected result
        val expectedResult = ConvertModelD(
            rates = listOf(
                ConvertRateD(currencyCode = "USD", rate = 1.0f),
                ConvertRateD(currencyCode = "EUR", rate = 0.85f)
            ),
            timestamp = 1631020212
        )

        // Assert that the result matches the expected result
        assertEquals(expectedResult, result)
    }

    @Test
    fun mapFromEntity_withInvalidData() {
        // Create a ConvertModel with invalid data
        val convertModel = ConvertModel(
            rates  = jsonElement1.asJsonObject,
            timestamp = null,
            base = String(),
            disclaimer = "Disclaimer",
            license = "No License"
        )

        // Call the mapFromEntity function to convert it
        val result = convertMapper.mapFromEntity(convertModel)

        // Define the expected result (empty rates list, timestamp set to 0)
        val expectedResult = ConvertModelD(
            rates = listOf(
                ConvertRateD(currencyCode = "USD", rate = 1.0f),
                ConvertRateD(currencyCode = "EUR", rate = 0.85f)
            ),
            timestamp = 0
        )

        // Assert that the result matches the expected result
        assertEquals(expectedResult, result)
    }

    @Test
    fun mapFromEntity_withInvalidRateJSon() {
        // Create a ConvertModel with invalid data
        val convertModel = ConvertModel(
            rates  = jsonElement2.asJsonObject,
            timestamp = 1631020212,
            base = String(),
            disclaimer = "Disclaimer",
            license = "No License"
        )

        // Call the mapFromEntity function to convert it
        val result = convertMapper.mapFromEntity(convertModel)

        // Define the expected result (empty rates list, timestamp set to 0)
        val expectedResult = ConvertModelD(
            rates = emptyList(),
            timestamp = 1631020212
        )

        // Assert that the result matches the expected result
        assertEquals(expectedResult, result)
    }
}