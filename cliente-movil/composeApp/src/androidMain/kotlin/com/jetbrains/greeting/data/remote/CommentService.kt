package com.jetbrains.greeting.data.remote

import com.jetbrains.greeting.data.entitys.ComentarioDTO
import com.jetbrains.greeting.data.entitys.ComentarioResponseDTO
import com.jetbrains.greeting.data.entitys.CommentEntity
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class CommentService(private val client: HttpClient, private val baseUrl: String) {



    suspend fun crearComentario(
        comentario: CommentEntity,
        token: String,
        comida: String,
        restaurante: String
    ): Boolean? {
        return try {
            val response = client.post("$baseUrl/api/comentarios/crear") {
                contentType(ContentType.Application.Json)
                setBody(ComentarioDTO(comentario.text, comentario.rating, comentario.destacado))
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                url {
                    parameters.append("comida", comida)
                    parameters.append("restaurante", restaurante)
                }
            }

            if (response.status == HttpStatusCode.OK) {
                val dto = Json.decodeFromString<ComentarioResponseDTO>(response.bodyAsText())
                println("✅ Backend devolvió ID=${dto.id} texto='${dto.contenido}' valoracion=${dto.valoracion} destacado=${dto.destacado}")

                // Construir un CommentEntity con los datos del backend
                val createdComment = comentario.copy(
                    id = dto.id,
                    text = dto.contenido,
                    rating = dto.valoracion,
                    destacado = dto.destacado
                )
                true
            } else {
                println("❌ Error HTTP ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("❌ Excepción: ${e.message}")
            null
        }
    }




    suspend fun obtenerComentariosPorComida(comida: String, restaurante: String, id: Long): List<CommentEntity> {
        println("📡 [Service] Solicitando comentarios para comida: $comida")

        val response = client.get("$baseUrl/api/comentarios/lista") {
            url {
                parameters.append("comida", comida)
                parameters.append("restaurante", restaurante)
            }
        }

        println("📥 [Service] Status: ${response.status}")
        val responseBody = response.bodyAsText()
        println("📥 [Service] Body: $responseBody")

        return if (response.status == HttpStatusCode.OK) {
            val dtoList = Json.decodeFromString<List<ComentarioResponseDTO>>(responseBody)
            val commentEntities = dtoList.map {
                CommentEntity(
                    id = it.id,
                    text = it.contenido,
                    rating = it.valoracion,
                    user = it.clienteEmail,
                    menuItemId = id,
                    date = System.currentTimeMillis().toString(),
                    destacado = it.destacado
                )
            }
            commentEntities
        } else {
            throw Exception("Error al obtener comentarios: ${response.status}")
        }
    }


    suspend fun eliminarComentario(token: String, id: Long): Boolean {
        println("🗑️ [Service] Eliminando comentario ID=$id")

        val response = try {
            client.delete("$baseUrl/api/comentarios/eliminar") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
                url {
                    parameters.append("id", id.toString())
                }
            }
        } catch (e: Exception) {
            println("❌ [Service] Error al eliminar comentario: ${e.message}")
            return false
        }

        println("✅ [Service] Resultado eliminación: ${response.status}")
        return response.status == HttpStatusCode.OK
    }

    suspend fun obtenerPromedioValoracion(comida: String,  restaurante: String): Int {
        println("📡 [Service] Solicitando promedio de valoraciones para: $comida")

        val response = client.get("$baseUrl/api/comentarios/promedio") {
            url {
                parameters.append("comida", comida)
                parameters.append("restaurante", restaurante)
            }
        }

        println("📥 [Service] Status: ${response.status}")
        
        return if (response.status == HttpStatusCode.OK) {
            response.body()
        } else {
            throw Exception("Error al obtener promedio: ${response.status}")
        }
    }
}

