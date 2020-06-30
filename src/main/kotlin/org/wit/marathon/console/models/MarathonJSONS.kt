package org.wit.marathon.console.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import mu.KotlinLogging
import org.wit.marathon.console.helpers.exists
import org.wit.marathon.console.helpers.read
import org.wit.marathon.console.helpers.write

import java.util.*

private val logger = KotlinLogging.logger {}

val JSON_FILE = "marathon.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<MarathonModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class MarathonJSONStore : MarathonStore {

    var marathons = mutableListOf<MarathonModel>()

    init {
        if (exists(JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<MarathonModel> {
        return marathons
    }

    override fun findOne(id: Long) : MarathonModel? {
        var foundMarathon: MarathonModel? = marathons.find { p -> p.id == id }
        return foundMarathon
    }

    override fun create(marathon: MarathonModel) {
        marathon.id = generateRandomId()
        marathons.add(marathon)
        serialize()
    }

    override fun update(marathon: MarathonModel) {
        var foundMarathon = findOne(marathon.id!!)
        if (foundMarathon != null) {
            foundMarathon.place = marathon.place
            foundMarathon.distance = marathon.distance
        }
        serialize()
    }

    override fun delete(marathon: MarathonModel) {
        marathons.remove(marathon)
        serialize()
    }

    internal fun logAll() {
        marathons.forEach { logger.info("${it}") }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(marathons,
            listType
        )
        write(
            JSON_FILE,
            jsonString
        )
    }

    private fun deserialize() {
        val jsonString =
            read(JSON_FILE)
        marathons = Gson().fromJson(jsonString, listType)
    }
}