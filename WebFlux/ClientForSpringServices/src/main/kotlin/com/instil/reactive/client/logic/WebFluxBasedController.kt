package com.instil.reactive.client.logic

import com.instil.reactive.client.model.dto.Course
import javafx.collections.ObservableList
import org.springframework.web.reactive.function.BodyInserters
import tornadofx.Controller
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class WebFluxBasedController : Controller() {
    private val webClient = WebClient.create("http://localhost:8080/courses/")

    fun loadAllCourses(action: (Course) -> Unit) {
        webClient.get()
            .exchange()
            .subscribe { response ->
                response.bodyToFlux(Course::class.java)
                    .subscribe(action)
            }
    }

    fun loadSingleCourse(id: String, action: (Course) -> Unit) {
        webClient.get()
            .uri(id)
            .retrieve()
            .bodyToMono(Course::class.java)
            .subscribe(action)
    }

    fun deleteCourse(
        id: String,
        action: (String) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        webClient.delete()
            .uri(id)
            .retrieve()
            .bodyToMono(String::class.java)
            .subscribe(action, errorHandler)
    }

    fun updateCourse(course: Course, action: (String) -> Unit) {
        webClient.put()
            .uri(course.id)
            .body(BodyInserters.fromPublisher(Mono.just(course), Course::class.java))
            .retrieve()
            .bodyToMono(String::class.java)
            .subscribe(action)
    }
}