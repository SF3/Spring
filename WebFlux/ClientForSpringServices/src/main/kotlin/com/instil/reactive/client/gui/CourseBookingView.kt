package com.instil.reactive.client.gui

import com.instil.reactive.client.logic.CoroutineBasedController
import com.instil.reactive.client.logic.WebFluxBasedController
import com.instil.reactive.client.logic.JerseyBasedController
import com.instil.reactive.client.model.CourseDifficulty
import com.instil.reactive.client.model.dto.Course
import com.instil.reactive.client.model.ui.FxCourse
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections.observableArrayList
import javafx.geometry.HPos
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.layout.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tornadofx.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class CourseBookingView : View("Course Booking UI via Kotlin and TornadoFX") {
    //The controllers which communicate with the server
    private val jerseyController: JerseyBasedController by inject()
    private val coroutineController: CoroutineBasedController by inject()
    private val webfluxController: WebFluxBasedController by inject()

    //The list of courses to be displayed (empty by default)
    private val courses = mutableListOf<Course>().asObservable()
    //A property for the current message to show the user
    private val currentMessage = SimpleStringProperty("")
    //The current course being edited (initially hidden)
    private val selectedCourse = FxCourse.default()
    //A property to control if the selected course should be shown
    private val showSelectedCourse = SimpleBooleanProperty(false)

    override val root = form {
        fieldset("Our Current Portfolio") {
            vbox {
                style {
                    borderStyle += BorderStrokeStyle.SOLID
                    borderColor += box(c("#000000"))
                    padding = box(10.px)
                }
                hbox(spacing = 5) {
                    button("Load Sync") {
                        action(::loadAllCoursesSync)
                    }
                    button("Load Async Via Reactor") {
                        action(::loadAllCoursesAsyncReactor)
                    }
                    button("Load Async Via Coroutines") {
                        action {
                            GlobalScope.launch(Dispatchers.Main) {
                                loadAllCoursesAsyncCoroutines()
                            }
                        }
                    }
                    button("Reset") {
                        action(::resetUI)
                    }
                }
            }
            tableview(courses) {
                readonlyColumn("Course ID", Course::id)
                readonlyColumn("Course Title", Course::title).remainingWidth()
                readonlyColumn("Difficulty", Course::difficulty)
                readonlyColumn("Duration", Course::duration)
                smartResize()
                selectionModel.selectionMode = SelectionMode.SINGLE
                onSelectionChange(::newCourseSelected)
            }
        }
        fieldset("The Selected Course") {
            field("ID") {
                label().bind(selectedCourse.idProperty())
            }
            field("Title") {
                textfield() {
                    hgrow = Priority.ALWAYS
                }.bind(selectedCourse.titleProperty())
            }
            field("Difficulty") {
                combobox(selectedCourse.difficultyProperty()) {
                    items = observableArrayList(CourseDifficulty.values().map { it.toString() })
                }
            }
            field("Duration") {
                combobox(selectedCourse.durationProperty()) {
                    items = observableArrayList(1, 2, 3, 4, 5)
                }
            }
            hbox(10) {
                button("Update") {
                    action(::updateCourse)
                }
                button("Delete") {
                    action {
                        deleteCourse(selectedCourse.id)
                    }
                }
                alignment = Pos.BASELINE_RIGHT
            }
        }.visibleWhen(showSelectedCourse)
        fieldset("Messages") {
            label(currentMessage)
        }
    }

    private fun changeMessageWithTime(text: String) {
        Platform.runLater {
            val time = LocalTime.now().format(DateTimeFormatter.ISO_TIME)
            currentMessage.value = "$text at $time"
        }
    }

    private fun changeMessage(text: String) {
        Platform.runLater {
            currentMessage.value = text
        }
    }

    private fun newCourseSelected(selected: Course?) {
        if (selected != null) {
            webfluxController.loadSingleCourse(selected.id) { course ->
                Platform.runLater {
                    displaySelectedCourse(course)
                }
            }
        }
    }

    private fun displaySelectedCourse(course: Course) {
        selectedCourse.reset(course)
        showSelectedCourse.value = true
        changeMessage("Displaying ${course.id}")
    }

    private fun updateCourse() {
        webfluxController.updateCourse(selectedCourse.toDTO()) { message ->
            loadAllCoursesAsyncReactor()
            changeMessage(message)
        }
    }

    private fun deleteCourse(id: String) {
        fun success(message: String) {
            loadAllCoursesAsyncReactor()
            changeMessage(message)
        }

        fun failure(error: Throwable) {
            val msg = error.message ?: "No error message"
            changeMessage("Deletion failed with $msg")
        }

        webfluxController.deleteCourse(id, ::success, ::failure)
    }

    private fun resetUI() {
        courses.clear()
        showSelectedCourse.value = false
    }

    private fun loadAllCoursesAsyncReactor() {
        resetUI()
        webfluxController.loadAllCourses { course ->
            Platform.runLater {
                courses.add(course)
            }
        }
    }

    private suspend fun loadAllCoursesAsyncCoroutines() {
        resetUI()
        coroutineController.loadAllCourses(courses)
    }

    private fun loadAllCoursesSync() {
        resetUI()
        courses.addAll(jerseyController.loadAllCourses())
    }
}