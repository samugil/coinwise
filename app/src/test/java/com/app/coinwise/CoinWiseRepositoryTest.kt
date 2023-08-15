package com.app.coinwise

import com.app.coinwise.data.local.Dao
import com.app.coinwise.data.remote.ItemDto
import com.app.coinwise.data.remote.Service
import com.app.coinwise.repository.CoinWiseRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CoinWiseRepositoryTest {

    private val dao: Dao = mock()

    private val service: Service = mock()

    private val undertest: CoinWiseRepository by lazy{
            CoinWiseRepository(
                dao,
                service
            )
    }

    @Test
    fun insertChartItemTest() = runTest{
        val itemDTO = ItemDto(
            id = 1,
            description = "description",
            name = "bitcoin",
            period = "month",
            status = "ok",
            unit = "brl",
            values = emptyList()
        )

        undertest.insertChartItem(itemDTO)

        verify(dao).getLastChartItem()
    }
}

