package com.open.exchange.cconverter

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.open.exchange.data.dao.AppDatabase
import com.open.exchange.data.dao.CurrencyDao
import com.open.exchange.domain.models.Currency
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExampleDaoTest {
    private lateinit var currencyDao: CurrencyDao
    private lateinit var db: AppDatabase

    @Before
    fun setupDbAndDao() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        currencyDao = db.getCurrencyListDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun currencyDaoTestOperations() {
        // reading all currencies first, expecting an empty array
        runBlocking {
            var dbList = currencyDao.getList().first()
            assertTrue(dbList.isEmpty())

            val currency1 = Currency(
                currencyName = "Test1",
                currencyCode = "EUD",
                isSelected = false
            )
            val currency2 = Currency(
                currencyName = "Test2",
                currencyCode = "AUD",
                isSelected = true
            )
            var currencyList = arrayListOf(currency1, currency2)

            //saving currency
            currencyDao.insertAll(currencyList)

            // reading all currencies after insert, expecting an non empty array
            dbList = currencyDao.getList().first()
            assertTrue(dbList.isNotEmpty())
            //reading count, and it should be 0
            val count = currencyDao.getConvertCount().first()
            assertEquals(count, 0)
        }




    }
}