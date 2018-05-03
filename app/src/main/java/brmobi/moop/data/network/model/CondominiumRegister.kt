package brmobi.moop.data.network.model

import java.io.Serializable

/**
 * Created by murilo aires on 26/02/2018.
 */
data class CondominiumRegister(var zipCode: String, var condominiumName: String?, var condominiumAddress: String?,
                               var condominiumAddressNumber: String?, var condominiumPhoneNumber: String?,
                               var isHorizontal: Boolean?) : Serializable