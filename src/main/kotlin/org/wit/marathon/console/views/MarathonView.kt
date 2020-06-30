package org.wit.marathon.console.views

import org.wit.marathon.console.models.MarathonJSONStore
import org.wit.marathon.console.models.MarathonModel

class MarathonView {

    fun menu() : Int {

        var option : Int
        var input: String?

        println("MAIN MENU")
        println(" 1. Add a Marathon Run")
        println(" 2. Update a Marathon Run")
        println(" 3. Display All Marathon Runs")
        println(" 4. Display Single Marathon Run Details")
        println(" 5. Delete a Marathon Run")
        println("-1. Exit")
        println()
        print("Enter Option : ")
        input = readLine()!!
        option = if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            -9
        return option
    }

    fun listMarathons(marathons: MarathonJSONStore) {
        println("List All Marathon Runs")
        println()
        marathons.logAll()
        println()
    }

    fun showMarathon(marathon : MarathonModel) {
        if(marathon != null)
            println("Marathon Run Details [ $marathon ]")
        else
            println("Marathon Run Not Found...")
    }

    fun addMarathonData(marathon : MarathonModel) : Boolean {

        println()
        print("Enter the Place you run : ")
        marathon.title = readLine()!!
        print("Enter how Far you ran : ")
        marathon.description = readLine()!!

        return marathon.title.isNotEmpty() && marathon.description.isNotEmpty()
    }

    fun updateMarathonData(marathon : MarathonModel) : Boolean {

        var tempTitle: String?
        var tempDescription: String?

        if (marathon != null) {
            print("Enter a new Title for [ " + marathon.title + " ] : ")
            tempTitle = readLine()!!
            print("Enter a new Description for [ " + marathon.description + " ] : ")
            tempDescription = readLine()!!

            if (!tempTitle.isNullOrEmpty() && !tempDescription.isNullOrEmpty()) {
                marathon.title = tempTitle
                marathon.description = tempDescription
                return true
            }
        }
        return false
    }

    fun getId() : Long {
        var strId : String? // String to hold user input
        var searchId : Long // Long to hold converted id
        print("Enter id to Search/Update : ")
        strId = readLine()!!
        searchId = if (strId.toLongOrNull() != null && !strId.isEmpty())
            strId.toLong()
        else
            -9
        return searchId
    }
}