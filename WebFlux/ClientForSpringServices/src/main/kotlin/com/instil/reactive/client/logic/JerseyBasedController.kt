package com.instil.reactive.client.logic

import com.instil.reactive.client.model.dto.Course
import org.glassfish.jersey.media.sse.EventInput
import tornadofx.Controller
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType

class JerseyBasedController : Controller() {
    private val client = ClientBuilder.newClient()
    private val baseURL = "http://localhost:8080/courses/"

    fun loadAllCourses(): List<Course> {
        val items = mutableListOf<Course>()
        val target = client.target(baseURL)
        val input = target.request().get(EventInput::class.java)
        while(!input.isClosed) {
            val sseEvent = input.read()
            if(sseEvent != null) {
                val course = sseEvent.readData(Course::class.java, MediaType.APPLICATION_JSON_TYPE)
                items.add(course)
            }
        }
        return items.toList()
    }

    fun loadSingleCourse(id: String): Course? {
        return client
            .target(baseURL)
            .path(id)
            .request(MediaType.APPLICATION_JSON)
            .get(Course::class.java)
    }

    fun deleteCourse(id: String): String {
        val message = client
            .target(baseURL)
            .path(id)
            .request()
            .delete(String::class.java)
        return message
    }

    fun updateCourse(course: Course): String {
        return client
            .target(baseURL)
            .path(course.id)
            .request()
            .put(Entity.json(course),String::class.java)
    }
}