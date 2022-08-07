package utils

import domain.aggregates.blog_aggregate.value_objects.Time

class TimeUtilityService {
    fun currentTime(): Time {
        return Time(0)
    }
}