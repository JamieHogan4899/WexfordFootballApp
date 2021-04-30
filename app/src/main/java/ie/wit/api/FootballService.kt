package ie.wit.api

import com.google.gson.GsonBuilder
import ie.wit.models.TeamModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface FootballService {
    @GET("/donations")
    fun getall(): Call<List<TeamModel>>

    @GET("/donations/{id}")
    fun get(@Path("id") id: String): Call<TeamModel>

    @DELETE("/donations/{id}")
    fun delete(@Path("id") id: String): Call<TeamModel>

    @POST("/donations")
    //@Headers("Content-Type: application/json")
    fun post(@Body team: TeamModel
        /*,@Header("Authorization") token : String*/)
            : Call<FootballWrapper>

    @PUT("/donations/{id}")
    fun put(@Path("id") id: String,
            @Body donation: TeamModel
    ): Call<FootballWrapper>

    companion object {

        val serviceURL = "https://donationweb-hdip-server.herokuapp.com"

        fun create() : FootballService {

            val gson = GsonBuilder().create()

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(serviceURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
            return retrofit.create(FootballService::class.java)
        }
    }
}