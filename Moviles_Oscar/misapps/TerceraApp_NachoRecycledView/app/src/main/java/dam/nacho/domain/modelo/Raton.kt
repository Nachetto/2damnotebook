package dam.nacho.domain.modelo
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
class Raton(
    @Json(name = "modelo")
    var modelo: String,
    @Json(name = "marca")
    var marca: String,
    @Json(name = "color")
    var color: String,
    @Json(name = "peso")
    var peso: Int,
    @Json(name = "dpi")
    var dpi: Int,
    @Json(name = "id:")
    var id: Int,
    @Json(name = "fechaFabricacion")
    var fechaFabricacion: String
) : Parcelable