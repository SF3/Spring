package com.instil.reactive.client.model.ui

import com.instil.reactive.client.model.dto.Course
import com.instil.reactive.client.model.CourseDifficulty
import tornadofx.getProperty
import tornadofx.property

class FxCourse(
    id: String,
    title: String,
    difficulty: CourseDifficulty,
    duration: Int
) {
    companion object {
        fun default() : FxCourse {
            return FxCourse(
                "YZ98",
                "Default Title",
                CourseDifficulty.BEGINNER,
                1
            )
        }
    }
    var id by property(id)
    fun idProperty() = getProperty(FxCourse::id)

    var title by property(title)
    fun titleProperty() = getProperty(FxCourse::title)

    var difficulty by property(difficulty.toString())
    fun difficultyProperty() = getProperty(FxCourse::difficulty)

    var duration by property(duration)
    fun durationProperty() = getProperty(FxCourse::duration)

    fun reset(input: Course) {
        id = input.id
        title = input.title
        difficulty = input.difficulty.toString()
        duration = input.duration
    }

    fun toDTO(): Course {
        return Course(
            id,
            title,
            CourseDifficulty.valueOf(difficulty),
            duration
        )
    }
}

