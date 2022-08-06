package utils

import model.Id

class IdGenerator {
    var counter = 0
    fun generate(): Id {
        return Id(counter++)
    }
}