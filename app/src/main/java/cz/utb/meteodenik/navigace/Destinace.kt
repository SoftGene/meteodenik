package cz.utb.meteodenik.navigace

import kotlinx.serialization.Serializable

@Serializable
object Seznam

@Serializable
data class Detail(val mestoId: Int)