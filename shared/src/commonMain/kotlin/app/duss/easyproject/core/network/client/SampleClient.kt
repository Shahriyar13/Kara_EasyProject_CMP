package app.duss.easyproject.core.network.client

import app.duss.easyproject.core.network.helper.handleErrors
import app.duss.easyproject.core.network.model.ServerResponse
import app.duss.easyproject.domain.entity.Project
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

class SampleClient(
    private val httpClient: HttpClient
) {

    suspend fun uploadFile(
        file: MPFile<Any>
    ): ServerResponse<List<Project>> {
        return handleErrors {

            httpClient.post("http://localhost:8080/upload") {
                setBody(
                    MultiPartFormDataContent(
                    formData {
                        append("description", "Ktor logo")
                        append("image", "", Headers.build {
                            append(HttpHeaders.ContentType, "image/png")
                            append(HttpHeaders.ContentDisposition, "filename=\"ktor_logo.png\"")
                        })
                    },
                    boundary = "WebAppBoundary"
                )
                )
                onUpload { bytesSentTotal, contentLength ->
                    println("Sent $bytesSentTotal bytes from $contentLength")
                }
            }
        }
    }
}