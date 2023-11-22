package app.duss.easyproject.domain.enums

enum class Role(val id: Int) {
    NOT_ACTIVE(0),
    BLOCKED(1),
    VIEWER(2),
    COLLECTOR(3),
    EDITOR(4),
    MANAGER(5),
    ADMIN(6),
}