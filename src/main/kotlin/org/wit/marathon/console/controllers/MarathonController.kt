package org.wit.marathon.console.controllers

import mu.KotlinLogging
import org.wit.marathon.console.models.MarathonJSONStore
import org.wit.marathon.console.models.MarathonModel
import org.wit.marathon.console.views.MarathonView

class MarathonController {

    // val marathons = MarathonMemStore()
    val marathons = MarathonJSONStore()
    val marathonView = MarathonView()
    val logger = KotlinLogging.logger {}

    init {
        logger.info { "Launching Marathon Console App" }
        println("Marathon Kotlin App Version 1.1")
    }

    fun start() {
        var input: Int

        do {
            input = menu()
            when (input) {
                1 -> add()
                2 -> update()
                3 -> list()
                4 -> search()
                5 -> delete()
                -99 -> dummyData()
                -1 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != -1)
        logger.info { "Shutting Down Marathon Console App" }
    }

    fun menu() :Int { return marathonView.menu() }

    fun add(){
        var aMarathon = MarathonModel()

        if (marathonView.addMarathonData(aMarathon))
            marathons.create(aMarathon)
        else
            logger.info("Marathon Run Not Added")
    }

    fun list() {
        marathonView.listMarathons(marathons)
    }

    fun update() {

        marathonView.listMarathons(marathons)
        var searchId = marathonView.getId()
        val aMarathon = search(searchId)

        if(aMarathon != null) {
            if(marathonView.updateMarathonData(aMarathon)) {
                marathons.update(aMarathon)
                marathonView.showMarathon(aMarathon)
                logger.info("Marathon Run Updated : [ $aMarathon ]")
            }
            else
                logger.info("Marathon Run Not Updated")
        }
        else
            println("Marathon Run Not Updated...")
    }

    fun search() {
        val aMarathon = search(marathonView.getId())!!
        marathonView.showMarathon(aMarathon)
    }


    fun search(id: Long) : MarathonModel? {
        var foundMarathon = marathons.findOne(id)
        return foundMarathon
    }

    fun delete() {
        marathonView.listMarathons(marathons)
        var searchId = marathonView.getId()
        val aMarathon = search(searchId)

        if(aMarathon != null) {
            marathons.delete(aMarathon)
            println("Marathon Run Deleted...")
            marathonView.listMarathons(marathons)
        }
        else
            println("Marathon Run Not Deleted...")
    }

    fun dummyData() {
        marathons.create(
            MarathonModel(
                place = "New York",
                distance = "50km"
            )
        )
        marathons.create(
            MarathonModel(
                place = "Shanghai",
                distance = "37km"
            )
        )
        marathons.create(
            MarathonModel(
                place = "Waterford City",
                distance = "33km"
            )
        )
    }
}