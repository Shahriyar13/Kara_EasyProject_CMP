package app.duss.easyproject.domain.enums

enum class TransportType(val id: Int) {
    Unknown(0),

    ByTruck(1),
    ByShip(2),
    ByAir(3),

    ByTruckAndShip(12),
    ByTruckAndAir(13),
    ByShipAndAir(23),

    ByTruckAndShipAndAir(123),
}