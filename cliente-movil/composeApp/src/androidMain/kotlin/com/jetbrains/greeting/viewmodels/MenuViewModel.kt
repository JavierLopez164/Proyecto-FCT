package com.jetbrains.greeting.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.greeting.data.entitys.MenuItemEntity
import com.jetbrains.greeting.data.repositories.CartRepository
import com.jetbrains.greeting.data.repositories.CommentRepository
import com.jetbrains.greeting.data.repositories.MenuRepository
import io.ktor.http.ContentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MenuViewModel(
    private val menuRepository: MenuRepository,
    private val commentRepository: CommentRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _menuItems = MutableStateFlow<List<MenuItemEntity>>(emptyList())
    val menuItems: StateFlow<List<MenuItemEntity>> = _menuItems.asStateFlow()

    private val _selectedItem = MutableStateFlow<MenuItemEntity?>(null)
    val selectedItem: StateFlow<MenuItemEntity?> = _selectedItem.asStateFlow()

    private val _restaurants = MutableStateFlow<List<String>>(emptyList())
    val restaurants: StateFlow<List<String>> = _restaurants.asStateFlow()

    private val _selectedRestaurant = MutableStateFlow<String?>(null)
    val selectedRestaurant: StateFlow<String?> = _selectedRestaurant.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        println("🚀 [MenuViewModel] Inicializando ViewModel")
        loadRestaurants()
        syncWithBackend()
    }

    private fun loadRestaurants() {
        viewModelScope.launch {
            println("🏪 [MenuViewModel] Cargando lista de restaurantes")
            _restaurants.value = menuRepository.getAllRestaurants()
            println("✅ [MenuViewModel] Restaurantes cargados: ${_restaurants.value.size}")
        }
    }

    private fun syncWithBackend() {
        viewModelScope.launch {
            println("🔄 [MenuViewModel] Iniciando sincronización con backend")
            _isLoading.value = true
            try {
                menuRepository.syncWithBackend()
                loadMenuItems()
                println("✅ [MenuViewModel] Sincronización completada exitosamente")
            } catch (e: Exception) {
                println("❌ [MenuViewModel] Error en sincronización: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadMenuItems() {
        viewModelScope.launch {
            println("📋 [MenuViewModel] Cargando items del menú")
            menuRepository.getAllMenuItems().collect { items ->
                _menuItems.value = items
                println("✅ [MenuViewModel] Items cargados: ${items.size}")
            }
        }
    }

    fun selectRestaurant(restaurantId: String?) {
        println("🏪 [MenuViewModel] Seleccionando restaurante: $restaurantId")
        _selectedRestaurant.value = restaurantId
        if (restaurantId != null) {
            viewModelScope.launch {
                menuRepository.getMenuItemsByRestaurant(restaurantId).collect { items ->
                    _menuItems.value = items
                    println("✅ [MenuViewModel] Items del restaurante cargados: ${items.size}")
                }
            }
        } else {
            loadMenuItems()
        }
    }

    fun updateMenuItemsRating() {
        viewModelScope.launch {
            println("⭐ [MenuViewModel] Actualizando calificaciones de items")
            val updatedItems = _menuItems.value.map { item ->
                val averageRating = item.id?.let { commentRepository.getAverageRating(it).firstOrNull() }
                    ?: 0f
                item.copy(rating = averageRating.toInt())
            }
            _menuItems.value = updatedItems
            println("✅ [MenuViewModel] Calificaciones actualizadas")
        }
    }

    fun getMenuItemById(itemId: Long) {
        viewModelScope.launch {
            println("🔍 [MenuViewModel] Buscando item con ID: $itemId")
            val item = menuRepository.getMenuItemById(itemId).first()
            _selectedItem.value = item
            println("✅ [MenuViewModel] Item encontrado: ${item?.name}")
        }
    }

    fun fetchMenuItemById(itemId: Long): Flow<MenuItemEntity?> {
        println("🔍 [MenuViewModel] Obteniendo flujo de item con ID: $itemId")
        return menuRepository.getMenuItemById(itemId)
    }

    fun addMenuItem(item: MenuItemEntity) {
        viewModelScope.launch {
            println("📝 [MenuViewModel] Añadiendo nuevo item: ${item.name}")
            try {
                menuRepository.insertMenuItem(item)
                loadMenuItems()
                println("✅ [MenuViewModel] Item añadido exitosamente")
            } catch (e: Exception) {
                println("❌ [MenuViewModel] Error al añadir item: ${e.message}")
            }
        }
    }

    fun deleteMenuItem(itemId: Long) {
        viewModelScope.launch {
            println("🗑️ [MenuViewModel] Eliminando item con ID: $itemId")
            try {
                menuRepository.deleteMenuItem(itemId)
                loadMenuItems()
                println("✅ [MenuViewModel] Item eliminado exitosamente")
            } catch (e: Exception) {
                println("❌ [MenuViewModel] Error al eliminar item: ${e.message}")
            }
        }
    }

    fun clearSelectedItem() {
        println("🧹 [MenuViewModel] Limpiando item seleccionado")
        _selectedItem.value = null
    }

    suspend fun hasActiveCartForRestaurant(userEmail: String, restaurantId: String): Boolean {
        println("🛒 [MenuViewModel] Verificando carrito activo para usuario: $userEmail en restaurante: $restaurantId")
        return cartRepository.hasActiveCartForRestaurant(userEmail, restaurantId)
    }

    suspend fun subirFotoComida(
        imageBytes: ByteArray,
        comida: String,
        restaurante: String
    ): String? {
        println("📸 [MenuViewModel] Subiendo foto para comida: $comida en restaurante: $restaurante")
        return try {
            val url = menuRepository.subirFotoComida(imageBytes, comida, restaurante)
            if (url != null) {
                println("✅ [MenuViewModel] Foto subida exitosamente: $url")
                url
            } else {
                println("❌ [MenuViewModel] Error al subir foto: URL nula")
                null
            }
        } catch (e: Exception) {
            println("❌ [MenuViewModel] Error al subir foto: ${e.message}")
            null
        }
    }
}
