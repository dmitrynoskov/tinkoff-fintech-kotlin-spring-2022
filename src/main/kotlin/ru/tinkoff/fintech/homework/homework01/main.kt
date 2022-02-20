import ru.tinkoff.fintech.homework.homework01.*

fun main() {

    val boeing = PassengersAircraft(
        manufacturer = "Boeing",
        model = "737-800",
        maxPassengersCount = 170,
        fuselageType = PassengersAircraft.FuselageType.NarrowBody
    )
    val airbus = PassengersAircraft(
        manufacturer = "Airbus",
        model = "350-1000",
        maxPassengersCount = 475,
        fuselageType = PassengersAircraft.FuselageType.WideBody
    )
    val cargo = CargoAircraft(manufacturer = "McDonnell Douglas", model = "MD-11F", maxCargoWeight = 102)
    val woodie = Woodpecker(name = "Woodie")

    boeing.prepareForFlight()
    boeing.loadPeople(100)

    val planes = listOf(boeing, airbus, cargo)
    for (plane in planes) {
        println("--------------")
        plane.flight("Washington", "Dallas")
        plane.getInfo()
    }

    val somethingFlying = listOf(boeing, airbus, woodie)
    for (flying in somethingFlying) {
        println("--------------")
        flying.fly()
    }

    println("--------------")
    cargo.loadCargo(500)
    cargo.loadCargo(10)
    cargo.flight("Vancouver", "Montreal")

    val alaskaAirlines = Airline("AlaskaAirlines", boeing, airbus, cargo)
    println("--------------")
    alaskaAirlines.transferPeople("Boston", "Chicago", 500)
    println("--------------")
    alaskaAirlines.transferCargo("Miami", "New-York", 1000)

}