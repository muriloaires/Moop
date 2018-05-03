package brmobi.moop.data.network.model

/**
 * Created by murilo aires on 19/02/2018.
 */
data class GenericListResponse<T>(var data: List<T>, var diaSemana: String)