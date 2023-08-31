import org.json.JSONObject
import unibo.basicomm23.utils.ColorsOut
import java.io.File
import java.io.FileNotFoundException

object DomainSystemConfig {

	private var Expirationtime : Long = 0;
    private var Maxweight : Long = -1;
    private var StepTime : Long = 0;

    private var COLDROOMX : Int = -1;
    private var COLDROOMY : Int = -1;
    private var INDOORX : Int = -1;
    private var INDOORY : Int = -1;
    private var HOMEX : Int = -1;
    private var HOMEY : Int = -1;




    init {
        try {
            val config = File("AppConfig.json").readText(Charsets.UTF_8)
            val jsonObject   =  JSONObject( config );

            Expirationtime= jsonObject.getLong("Expirationtime")
            Maxweight= jsonObject.getLong("Maxweight")
            StepTime= jsonObject.getLong("StepTime")
            COLDROOMX= jsonObject.getInt("COLDROOMX")
            COLDROOMY= jsonObject.getInt("COLDROOMY")
            INDOORX= jsonObject.getInt("INDOORX")
            INDOORY= jsonObject.getInt("INDOORY")
            HOMEX= jsonObject.getInt("HOMEX")
            HOMEY= jsonObject.getInt("HOMEY")


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

    fun getColdRoomX() : Int {
        return COLDROOMX;
    }
    fun getColdRoomY() : Int {
        return COLDROOMY;
    }
    fun getIndoorX() : Int {
        return INDOORX;
    }
    fun getIndoorY() : Int {
        return INDOORY;
    }
    fun getHomeX() : Int {
        return HOMEX;
    }
    fun getHomeY() : Int {
        return HOMEY;
    }


    fun getStepTime(): Long {
        return StepTime;
    }

}
