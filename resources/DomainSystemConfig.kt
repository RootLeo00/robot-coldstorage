import org.json.JSONObject
import unibo.basicomm23.utils.ColorsOut
import java.io.File
import java.io.FileNotFoundException

object DomainSystemConfig {

	private var Expirationtime : Long = 0;
    private var Maxweight : Long = 0;


    init {
        try {
            val config = File("AppConfig.json").readText(Charsets.UTF_8)
            val jsonObject   =  JSONObject( config );

            Expirationtime= jsonObject.getLong("Expirationtime")
            Maxweight= jsonObject.getLong("Maxweight")

        } catch (e : Exception) {
            println(" ${this.javaClass.name}  | ${e.localizedMessage}, activate simulation by default")
        }
    }

    fun getExpirationTime() : Long {
        return Expirationtime;
    }

    fun getMaxWeight() : Long {
        return Maxweight;
    }

}
