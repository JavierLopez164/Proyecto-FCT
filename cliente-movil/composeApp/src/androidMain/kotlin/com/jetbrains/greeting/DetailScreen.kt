package com.jetbrains.greeting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import com.jetbrains.greeting.components.CommentSection
import com.jetbrains.greeting.components.LoadingIndicator
import com.jetbrains.greeting.data.entitys.UserType
import com.jetbrains.greeting.viewmodels.CartViewModel
import com.jetbrains.greeting.viewmodels.CommentViewModel
import com.jetbrains.greeting.viewmodels.MenuViewModel
import com.jetbrains.greeting.viewmodels.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import java.util.Date
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun DetailScreen(
    itemId: Long,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit,
    menuViewModel: MenuViewModel = koinViewModel(),
    cartViewModel: CartViewModel = koinViewModel(),
    commentViewModel: CommentViewModel = koinViewModel(),
    userViewModel: UserViewModel = koinViewModel()
) {
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val comments by commentViewModel.comments.collectAsState()
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val item by menuViewModel.selectedItem.collectAsState()
    val averageRatingFlow = commentViewModel.getAverageRatingFlow(itemId)
    val averageRating by averageRatingFlow.collectAsState(initial = 0f)
    val roundedRating = averageRating?.roundToInt()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }
    var showOrderAlert by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val currentUser by userViewModel.currentUser.collectAsState()
    val activeCart by cartViewModel.activeCart.collectAsState()
    val hasActiveCart by cartViewModel.hasActiveCart.collectAsState()

    LaunchedEffect(itemId) {
        try {
            commentViewModel.clearComments(itemId)
            
            menuViewModel.getMenuItemById(itemId)
            commentViewModel.loadComments(itemId)

            if (currentUser?.rol == UserType.ROLE_ADMIN.toString()) {
                commentViewModel.addDefaultAdminComment(itemId)
            }

            delay(1500)
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
        }
    }

    LaunchedEffect(currentUser?.email) {
        currentUser?.email?.let { email ->
            cartViewModel.loadActiveCartForUser(email)
            cartViewModel.loadUserCarts(email)
        }
    }

    LaunchedEffect(Unit) {
        try {
            commentViewModel.loadComments(itemId)
        } catch (e: Exception) {
            println("❌ [DetailScreen] Error al recargar comentarios: ${e.message}")
        }
    }

    // Limpiar el item seleccionado cuando se sale de la pantalla
    DisposableEffect(Unit) {
        onDispose {
            menuViewModel.clearSelectedItem()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = item?.name ?: "Detalles",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToCart) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Ir al carrito"
                        )
                    }
                    if (currentUser?.rol == UserType.ROLE_ADMIN.toString()) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = Color.Red
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading || item == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator(
                        size = 48,
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4f
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Imagen del plato
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        AsyncImage(
                            model = item?.imageUrl,
                            contentDescription = "Imagen del plato",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Información del plato
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = item?.name ?: "",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        // Rating
                        if (roundedRating != null) {
                            RatingStars(rating = roundedRating)
                        }

                        Text(
                            text = item?.description ?: "",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        // Características
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FeatureChip(
                                icon = Icons.Default.DateRange,
                                text = "${item?.preparationTime ?: 0} min",
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                            if(item?.isSpicy == true) {
                                FeatureChip(
                                    icon = Icons.Default.Face,
                                    text = "Picante",
                                    color = MaterialTheme.colorScheme.errorContainer
                                )
                            }
                            if(item?.isVegetarian == true) {
                                FeatureChip(
                                    icon = Icons.Default.Face,
                                    text = "Vegetariano",
                                    color = MaterialTheme.colorScheme.tertiaryContainer
                                )
                            }
                            if(item?.isGlutenFree == true) {
                                FeatureChip(
                                    icon = Icons.Default.Info,
                                    text = "Sin Gluten",
                                    color = MaterialTheme.colorScheme.primaryContainer
                                )
                            }
                            if(item?.isDairyFree == true) {
                                FeatureChip(
                                    icon = Icons.Default.Info,
                                    text = "Sin Lácteos",
                                    color = MaterialTheme.colorScheme.secondaryContainer
                                )
                            }
                            if(item?.isNutFree == true) {
                                FeatureChip(
                                    icon = Icons.Default.Info,
                                    text = "Sin Frutos Secos",
                                    color = MaterialTheme.colorScheme.tertiaryContainer
                                )
                            }
                        }

                        Text(
                            text = "Características:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = item?.features?.joinToString(", ") ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "$${item?.price ?: 0}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Botón de añadir al carrito
                    Button(
                        onClick = {
                            if (!hasActiveCart) {
                                showOrderAlert = true
                            } else {
                                item?.let { menuItem ->
                                    menuItem.id?.let { cartViewModel.addItemToCart(it) }
                                    showSnackbar = true
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Añadir al carrito",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Añadir al carrito")
                    }

                    // Sección de comentarios
                    CommentSection(
                        comments = comments,
                        onAddComment = { text, rating, isSpecial ->
                            scope.launch {
                                if (currentUser?.rol != UserType.INVITADO.toString()) {
                                    currentUser?.let {
                                        commentViewModel.addComment(
                                            itemId = itemId,
                                            text = text,
                                            rating = rating,
                                            user = it,
                                            date = Date().toString(),
                                            destacado = isSpecial
                                        )
                                    }
                                }
                            }
                        },
                        onDeleteComment = { commentId ->
                            if (currentUser?.rol == UserType.ROLE_ADMIN.toString()) {
                                scope.launch {
                                    currentUser?.token?.let { token ->
                                        commentViewModel.deleteComment(commentId, token)
                                    }
                                }
                            }
                        },
                        canDeleteComments = currentUser?.rol == UserType.ROLE_ADMIN.toString(),
                        canAddSpecialComments = currentUser?.rol == UserType.ROLE_ADMIN.toString(),
                        canAddComments = currentUser?.rol != UserType.INVITADO.toString()
                    )
                }
            }
        }

        if (showSnackbar) {
            LaunchedEffect(Unit) {
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Producto añadido al carrito",
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.Dismissed) {
                        showSnackbar = false
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                Button(onClick = {
                    menuViewModel.deleteMenuItem(itemId)
                    showDeleteDialog = false
                    onNavigateBack()
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar este elemento del menú?") }
        )
    }

    if (showOrderAlert) {
        AlertDialog(
            onDismissRequest = { showOrderAlert = false },
            title = { Text("No hay pedido activo") },
            text = { Text("Debes crear un pedido desde tu perfil antes de añadir items al carrito.") },
            confirmButton = {
                TextButton(
                    onClick = { 
                        showOrderAlert = false
                        onNavigateBack()
                    }
                ) {
                    Text("Ir a Perfil")
                }
            },
            dismissButton = {
                TextButton(onClick = { showOrderAlert = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun FeatureChip(
    icon: ImageVector,
    text: String,
    color: Color
) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
        color = color
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
