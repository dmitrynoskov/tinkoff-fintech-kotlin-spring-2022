package ru.tinkoff.fintech.homework.homework05

object AircraftService {
    private const val currencyRate = 1000
    private const val distanceUnitRate = 2

    fun convertToDescription(fleet: Collection<Aircraft>) =
        fleet.asSequence()
            .map {
                AircraftDescription(
                    manufacturer = it.manufacturer.toString(),
                    model = it.model,
                    fuselageType = it.fuselageType.toString(),
                    passengersCount = it.passengersCount,
                    maxDistance = it.maxDistance * distanceUnitRate,
                    price = it.price * currencyRate
                )
            }
            .sortedBy { it.price }
            .toList()

    fun groupByFuselageType(fleet: Collection<Aircraft>) = fleet.groupBy { it.fuselageType }

    fun findAircraftsWithGivenPredicate (fleet: Collection<Aircraft>, predicate: (Aircraft) -> Boolean) =
        fleet.asSequence()
            .filter { predicate(it) }
            .map { "${it.manufacturer} ${it.model}" }
            .take(3)
            .toList()

}
