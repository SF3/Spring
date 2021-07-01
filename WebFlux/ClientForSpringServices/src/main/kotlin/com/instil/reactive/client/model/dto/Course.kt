package com.instil.reactive.client.model.dto

import com.instil.reactive.client.model.CourseDifficulty

class Course(
    var id: String,
    var title: String,
    var difficulty: CourseDifficulty,
    var duration: Int
) {
    constructor() : this("", "", CourseDifficulty.BEGINNER, 0)

    override fun toString(): String {
        return "$id called $title"
    }
}
