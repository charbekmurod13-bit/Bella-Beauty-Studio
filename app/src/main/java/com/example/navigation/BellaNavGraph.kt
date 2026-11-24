package com.example.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data.models.ServiceCategory
import com.example.ui.components.BellaBottomNavigation
import com.example.ui.screens.AppointmentsScreen
import com.example.ui.screens.BookingConfirmationScreen
import com.example.ui.screens.BookingScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.GalleryPreviewDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ServiceDetailsScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.screens.SpecialistDetailsScreen
import com.example.ui.screens.SpecialistsScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.BellaIvory
import com.example.viewmodel.BellaViewModel

private val bottomNavRoutes = setOf(
    BellaRoutes.HOME,
    BellaRoutes.SERVICES,
    BellaRoutes.BOOKING,
    BellaRoutes.FAVORITES,
    BellaRoutes.PROFILE
)

@Composable
fun BellaNavGraph(
    viewModel: BellaViewModel = viewModel()
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in bottomNavRoutes

    fun navigateTopLevel(route: String) {
        navController.navigate(route) {
            popUpTo(BellaRoutes.HOME) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        containerColor = BellaIvory,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                BellaBottomNavigation(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        if (route == BellaRoutes.BOOKING) {
                            viewModel.startBooking()
                        }
                        navigateTopLevel(route)
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = BellaRoutes.WELCOME,
                enterTransition = {
                    fadeIn(animationSpec = tween(220))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(180))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(220))
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(180))
                }
            ) {
                composable(BellaRoutes.WELCOME) {
                    WelcomeScreen(
                        onGetStartedClick = {
                            navController.navigate(BellaRoutes.HOME) {
                                popUpTo(BellaRoutes.WELCOME) { inclusive = true }
                            }
                        }
                    )
                }

                composable(BellaRoutes.HOME) {
                    HomeScreen(
                        uiState = uiState,
                        onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                        onSearchSubmit = {
                            viewModel.selectCategory(ServiceCategory.ALL)
                            navigateTopLevel(BellaRoutes.SERVICES)
                        },
                        onOfferClick = {
                            viewModel.startBooking(applyWelcomePromo = true)
                            navigateTopLevel(BellaRoutes.BOOKING)
                        },
                        onCategoryClick = { category ->
                            viewModel.selectCategory(category)
                            navigateTopLevel(BellaRoutes.SERVICES)
                        },
                        onSeeAllServicesClick = {
                            viewModel.selectCategory(ServiceCategory.ALL)
                            navigateTopLevel(BellaRoutes.SERVICES)
                        },
                        onServiceClick = { serviceId ->
                            navController.navigate(BellaRoutes.serviceDetails(serviceId))
                        },
                        onToggleFavorite = { serviceId ->
                            viewModel.toggleFavorite(serviceId)
                        },
                        onProfileClick = {
                            navigateTopLevel(BellaRoutes.PROFILE)
                        },
                        onSeeAllSpecialistsClick = {
                            navController.navigate(BellaRoutes.SPECIALISTS)
                        },
                        onSpecialistClick = { specialistId ->
                            navController.navigate(BellaRoutes.specialistDetails(specialistId))
                        },
                        onBookSpecialistClick = { specialistId ->
                            viewModel.startBooking(specialistId = specialistId)
                            navigateTopLevel(BellaRoutes.BOOKING)
                        },
                        onGalleryItemClick = { item ->
                            viewModel.openGalleryPreview(item)
                        }
                    )
                }

                composable(BellaRoutes.SERVICES) {
                    ServicesScreen(
                        uiState = uiState,
                        onSelectCategory = { viewModel.selectCategory(it) },
                        onResetFilters = {
                            viewModel.selectCategory(ServiceCategory.ALL)
                            viewModel.updateSearchQuery("")
                        },
                        onServiceClick = { serviceId ->
                            navController.navigate(BellaRoutes.serviceDetails(serviceId))
                        },
                        onBookServiceClick = { serviceId ->
                            viewModel.startBooking(serviceId = serviceId)
                            navigateTopLevel(BellaRoutes.BOOKING)
                        }
                    )
                }

                composable(
                    route = BellaRoutes.SERVICE_DETAILS,
                    arguments = listOf(navArgument("serviceId") { type = NavType.StringType }),
                    enterTransition = {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(260)) + fadeIn(tween(220))
                    },
                    popExitTransition = {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(240)) + fadeOut(tween(180))
                    }
                ) { backStackEntry ->
                    val serviceId = backStackEntry.arguments?.getString("serviceId")
                    val service = uiState.services.firstOrNull { it.id == serviceId }
                    val serviceReviews = uiState.reviews.filter { it.serviceId == serviceId }
                    ServiceDetailsScreen(
                        service = service,
                        reviews = serviceReviews,
                        isFavorite = serviceId != null && uiState.favoriteServiceIds.contains(serviceId),
                        onBackClick = { navController.popBackStack() },
                        onToggleFavorite = {
                            if (serviceId != null) viewModel.toggleFavorite(serviceId)
                        },
                        onBookNowClick = {
                            viewModel.startBooking(serviceId = serviceId)
                            navigateTopLevel(BellaRoutes.BOOKING)
                        }
                    )
                }

                composable(BellaRoutes.BOOKING) {
                    BookingScreen(
                        uiState = uiState,
                        onBackClick = {
                            if (!navController.popBackStack()) {
                                navigateTopLevel(BellaRoutes.HOME)
                            }
                        },
                        onSelectService = { viewModel.selectBookingService(it) },
                        onSelectSpecialist = { viewModel.selectBookingSpecialist(it) },
                        onSelectDay = { viewModel.selectBookingDay(it) },
                        onSelectTime = { viewModel.selectBookingTime(it) },
                        onTogglePromo = { viewModel.togglePromoCode(it) },
                        onCustomerNameChange = { viewModel.updateCustomerName(it) },
                        onCustomerPhoneChange = { viewModel.updateCustomerPhone(it) },
                        onCustomerEmailChange = { viewModel.updateCustomerEmail(it) },
                        onConfirmBooking = {
                            viewModel.confirmBooking {
                                navController.navigate(BellaRoutes.BOOKING_CONFIRMATION) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }

                composable(BellaRoutes.BOOKING_CONFIRMATION) {
                    BookingConfirmationScreen(
                        appointment = uiState.lastConfirmedAppointment ?: uiState.nextUpcomingAppointment,
                        onViewAppointmentsClick = {
                            navController.navigate(BellaRoutes.APPOINTMENTS) {
                                popUpTo(BellaRoutes.HOME)
                            }
                        },
                        onReturnHomeClick = {
                            navController.navigate(BellaRoutes.HOME) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable(BellaRoutes.FAVORITES) {
                    FavoritesScreen(
                        uiState = uiState,
                        onServiceClick = { serviceId ->
                            navController.navigate(BellaRoutes.serviceDetails(serviceId))
                        },
                        onRemoveFavorite = { serviceId ->
                            viewModel.toggleFavorite(serviceId)
                        },
                        onBookServiceClick = { serviceId ->
                            viewModel.startBooking(serviceId = serviceId)
                            navigateTopLevel(BellaRoutes.BOOKING)
                        },
                        onBrowseServicesClick = {
                            viewModel.selectCategory(ServiceCategory.ALL)
                            navigateTopLevel(BellaRoutes.SERVICES)
                        }
                    )
                }

                composable(BellaRoutes.PROFILE) {
                    ProfileScreen(
                        uiState = uiState,
                        onViewAppointmentsClick = {
                            navController.navigate(BellaRoutes.APPOINTMENTS)
                        },
                        onOpenFavoritesClick = {
                            navigateTopLevel(BellaRoutes.FAVORITES)
                        },
                        onUpdateProfile = { name, email, phone ->
                            viewModel.updateUserProfile(name, email, phone)
                        },
                        onUpdateNotifications = { push, reminder ->
                            viewModel.updateNotifications(push, reminder)
                        },
                        onLogoutClick = {
                            viewModel.logout {
                                navController.navigate(BellaRoutes.WELCOME) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        }
                    )
                }

                composable(
                    route = BellaRoutes.APPOINTMENTS,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(260)) + fadeIn(tween(220))
                    },
                    popExitTransition = {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(240)) + fadeOut(tween(180))
                    }
                ) {
                    AppointmentsScreen(
                        uiState = uiState,
                        onBackClick = { navController.popBackStack() },
                        onCancelAppointment = { viewModel.cancelAppointment(it) },
                        onBookNewClick = {
                            viewModel.startBooking()
                            navigateTopLevel(BellaRoutes.BOOKING)
                        }
                    )
                }

                composable(BellaRoutes.SPECIALISTS) {
                    SpecialistsScreen(
                        uiState = uiState,
                        onBackClick = { navController.popBackStack() },
                        onSpecialistClick = { specialistId ->
                            navController.navigate(BellaRoutes.specialistDetails(specialistId))
                        },
                        onBookSpecialistClick = { specialistId ->
                            viewModel.startBooking(specialistId = specialistId)
                            navigateTopLevel(BellaRoutes.BOOKING)
                        }
                    )
                }

                composable(
                    route = BellaRoutes.SPECIALIST_DETAILS,
                    arguments = listOf(navArgument("specialistId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val specialistId = backStackEntry.arguments?.getString("specialistId")
                    val specialist = uiState.specialists.firstOrNull { it.id == specialistId }
                    val services = if (specialist != null) {
                        uiState.services.filter { specialist.availableServiceIds.contains(it.id) }
                    } else {
                        emptyList()
                    }
                    SpecialistDetailsScreen(
                        specialist = specialist,
                        availableServices = services,
                        onBackClick = { navController.popBackStack() },
                        onServiceClick = { serviceId ->
                            navController.navigate(BellaRoutes.serviceDetails(serviceId))
                        },
                        onBookServiceWithSpecialist = { serviceId, specId ->
                            viewModel.startBooking(serviceId = serviceId, specialistId = specId)
                            navigateTopLevel(BellaRoutes.BOOKING)
                        },
                        onBookSpecialistClick = { specId ->
                            viewModel.startBooking(specialistId = specId)
                            navigateTopLevel(BellaRoutes.BOOKING)
                        }
                    )
                }
            }
        }
    }

    GalleryPreviewDialog(
        item = uiState.selectedGalleryItem,
        onDismiss = { viewModel.closeGalleryPreview() }
    )
}
