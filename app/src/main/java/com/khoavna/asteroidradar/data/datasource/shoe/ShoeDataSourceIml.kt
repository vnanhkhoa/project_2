package com.khoavna.shoestore.data.datasource.shoe

import com.khoavna.asteroidradar.data.model.Shoe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class ShoeDataSourceIml : ShoeDataSource {
    private val shoes = MutableStateFlow(
        listOf(
            Shoe(
                id = 1,
                name = "NiKe",
                price = 120,
                description = "Nike is best known for its footwear, apparel, and equipment. " +
                        "Founded in 1964 as Blue Ribbon Sports, the company became Nike in 1971 " +
                        "after the Greek goddess of victory. One of the most valuable brands among " +
                        "sport businesses, Nike employs over 76,000 people worldwide."
            ),
            Shoe(
                id = 2,
                name = "Puma",
                price = 90,
                description = "Puma SE is a German multinational corporation that designs and " +
                        "manufactures athletic and casual footwear, apparel and accessories," +
                        " which is headquartered in Herzogenaurach, Bavaria, Germany. " +
                        "Puma is the third largest sportswear manufacturer in the world."
            ),
            Shoe(
                id = 3,
                name = "Adidas",
                price = 80,
                description = "Adidas AG stylized as adidas since 1949)[4] is a German athletic " +
                        "apparel and footwear corporation headquartered in Herzogenaurach, Bavaria, " +
                        "Germany. It is the largest sportswear manufacturer in Europe, and the second " +
                        "largest in the world, after Nike."
            ),
            Shoe(
                id = 4,
                name = "Reebok",
                price = 120,
                description = "Reebok International Limited is an American fitness footwear and " +
                        "clothing brand that is a part of Authentic Brands Group. It was established" +
                        " in England in 1958 as a companion company to J.W. Foster and Sons, " +
                        "a sporting goods company which had been founded in 1895 in Bolton, Lancashire."
            ),
            Shoe(
                id = 5,
                name = "Converse",
                price = 160,
                description = "Converse is an American lifestyle brand that markets, distributes," +
                        " and licenses footwear, apparel, and accessories. Founded by Marquis Mills " +
                        "Converse in 1908 as the Converse Rubber Shoe Company in Malden, Massachusetts, " +
                        "it has been acquired by several companies before becoming a subsidiary " +
                        "of Nike, Inc. in 2003."
            ),
        )
    )

    override fun findAll(): StateFlow<List<Shoe>> = shoes.asStateFlow()

    override fun findOne(id: Int): Flow<Shoe?> = flow {
        shoes.value.find { it.id == id }
    }

    override fun delete(shoe: Shoe) {
        shoes.update {
            it.filter { item ->
                item.id == shoe.id
            }
        }
    }

    override fun insert(shoe: Shoe) {
        shoes.update {
            it + shoe.copy(id = it.size)
        }
    }

    override fun update(shoe: Shoe) {
        shoes.update {
            it.map { item ->
                if (item.id == shoe.id) shoe else item
            }
        }
    }
}