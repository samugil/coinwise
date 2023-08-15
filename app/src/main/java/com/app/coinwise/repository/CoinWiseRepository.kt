package com.app.coinwise.repository

import com.app.coinwise.data.local.Dao
import com.app.coinwise.data.local.Table
import com.app.coinwise.data.local.Value
import com.app.coinwise.data.remote.ItemDto
import com.app.coinwise.data.remote.Service
import retrofit2.Response

// Criando parametros das conexões local e remota.
class CoinWiseRepository(
    private val local: Dao,
    private val remote: Service
) {

    val chartItem = local.getLastChartItem()

    suspend fun getChartItems(): Response<ItemDto> {
        return remote.getChartItemsApi1year("1year")
    }

    //Pegando os dados da API para aplicar no gráfico, e fazendo o insert na base local (ROOM)
    suspend fun refreshChartItems() {

        val response = remote.getChartItemsApi1year("1year")
        if (response.isSuccessful) {
            val bitcoin = response.body()
            bitcoin?.let { itemDto ->
                insertChartItem(itemDto)
            }
        }
    }

     suspend fun insertChartItem(chartItem: ItemDto) {
        val table = mapItemToChart(chartItem)
        local.insert(table)
    }

    // mapeamento dos dados, convertendo da base online para a base local.
     fun mapItemToChart(itemDto: ItemDto): Table {
        return Table(
            description = itemDto.description,
            name = itemDto.name,
            period = itemDto.period,
            status = itemDto.status,
            unit = itemDto.unit,
            values = itemDto.values.map { axisDto ->
                Value(
                    x = axisDto.x,
                    y = axisDto.y
                )
            }
        )
    }
}