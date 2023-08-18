import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException

object DomainSystemConfig {

	private var Expirationtime : Long = 10000;

    init {
        try {
            val config = File("../AppConfig.json").readText(Charsets.UTF_8)
            val jsonObject   =  JSONObject( config );


            Expirationtime= jsonObject.getLong("Expirationtime")
        } catch (e : Exception) {
            println(" ${this.javaClass.name}  | ${e.localizedMessage}, activate simulation by default")
        }
    }

    fun getExpirationTime() : Long {
        return Expirationtime
    }
}
