package app.duss.easyproject.data.network.client

import app.duss.easyproject.data.network.NetworkConstants
import app.duss.easyproject.data.network.dto.ServerResponse
import app.duss.easyproject.data.network.helper.handleErrors
import app.duss.easyproject.domain.entity.FileAttachment
import app.duss.easyproject.domain.params.FileAttachmentUpdateRequest
import com.mohamedrejeb.calf.io.name
import com.mohamedrejeb.calf.io.path
import com.mohamedrejeb.calf.io.readByteArray
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class AttachmentClient(
    private val httpClient: HttpClient
) {
    suspend fun upload(
        param: FileAttachmentUpdateRequest,
    ): ServerResponse<List<FileAttachment>> {
        return handleErrors {
            httpClient.post(NetworkConstants.Attachment.upload) {
                setBody(
                    MultiPartFormDataContent(
                        formData {

                            param.projectId?.let { append("projectId", it.toString()) }
                            param.customerEnquiryId?.let { append("customerEnquiryId", it.toString()) }
                            param.customerEnquiryItemId?.let { append("customerEnquiryItemId", it.toString()) }
                            param.supplierEnquiryId?.let { append("supplierEnquiryId", it.toString()) }
                            param.quotationId?.let { append("quotationId", it.toString()) }
                            param.quotationItemId?.let { append("quotationItemId", it.toString()) }
                            param.proformaInvoiceId?.let { append("proformaInvoiceId", it.toString()) }
                            param.purchaseOrderId?.let { append("purchaseOrderId", it.toString()) }
                            param.shippingId?.let { append("shippingId", it.toString()) }
                            param.invoiceId?.let { append("invoiceId", it.toString()) }
                            param.bafaId?.let { append("bafaId", it.toString()) }
                            param.paymentId?.let { append("paymentId", it.toString()) }
                            param.files.forEach {
                                val key = it.path!!
                                append(key, it.readByteArray(), Headers.build {
//                                    append(HttpHeaders.ContentType, it.contentType)
                                    append(HttpHeaders.ContentDisposition, "filename=\"${it.name}\"")
                                })
                            }
                        },
                        boundary = "WebAppBoundary"
                    )
                )
                onUpload { bytesSentTotal, contentLength ->
                    println("Sent $bytesSentTotal bytes from $contentLength")
                }

                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun delete(
        id: Long,
    ): ServerResponse<Boolean> {
        return handleErrors {
            httpClient.delete(NetworkConstants.Attachment.deleteById) {
                parameter("id", id)
                contentType(ContentType.Application.Json)
            }
        }
    }
}