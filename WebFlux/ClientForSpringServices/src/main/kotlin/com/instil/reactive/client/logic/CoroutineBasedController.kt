package com.instil.reactive.client.logic

import com.instil.reactive.client.model.dto.Course
import javafx.collections.ObservableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.glassfish.jersey.media.sse.EventInput
import tornadofx.Controller
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.sse.InboundSseEvent

class CoroutineBasedController : Controller() {
    private val client = ClientBuilder.newClient()
    private val baseURL = "http://localhost:8080/courses/"

    private suspend fun addCourse(courses: ObservableList<Course>,
                                  event: InboundSseEvent) {
        withContext(Dispatchers.Main) {
            val course = event.readData(Course::class.java, MediaType.APPLICATION_JSON_TYPE)
            courses.add(course)
        }
    }

    suspend fun loadAllCourses(courses: ObservableList<Course>) {
        withContext(Dispatchers.IO) {
            val target = client.target(baseURL)
            val input = target.request().get(EventInput::class.java)
            while(!input.isClosed) {
                val sseEvent = input.read()
                if(sseEvent != null) {
                    addCourse(courses, sseEvent)
                }
            }
        }
    }

    suspend fun updateCourse(course: Course): String {
        return withContext(Dispatchers.IO) {
            client
                .target(baseURL)
                .path(course.id)
                .request()
                .put(Entity.json(course), String::class.java)
        }
    }
}