package com.instil.reactive.client.gui

import javafx.stage.Stage
import tornadofx.App

class CourseBookingApp : App(CourseBookingView::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 600.0
        stage.height = 700.0
    }
}