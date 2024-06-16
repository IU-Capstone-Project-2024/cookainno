package com.cookainno.mobile.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.cookainno.mobile.backgroundDark
import com.cookainno.mobile.backgroundLight
import com.cookainno.mobile.errorContainerDark
import com.cookainno.mobile.errorContainerLight
import com.cookainno.mobile.errorDark
import com.cookainno.mobile.errorLight
import com.cookainno.mobile.inverseOnSurfaceDark
import com.cookainno.mobile.inverseOnSurfaceLight
import com.cookainno.mobile.inversePrimaryDark
import com.cookainno.mobile.inversePrimaryLight
import com.cookainno.mobile.inverseSurfaceDark
import com.cookainno.mobile.inverseSurfaceLight
import com.cookainno.mobile.onBackgroundDark
import com.cookainno.mobile.onBackgroundLight
import com.cookainno.mobile.onErrorContainerDark
import com.cookainno.mobile.onErrorContainerLight
import com.cookainno.mobile.onErrorDark
import com.cookainno.mobile.onErrorLight
import com.cookainno.mobile.onPrimaryContainerDark
import com.cookainno.mobile.onPrimaryContainerLight
import com.cookainno.mobile.onPrimaryDark
import com.cookainno.mobile.onPrimaryLight
import com.cookainno.mobile.onSecondaryContainerDark
import com.cookainno.mobile.onSecondaryContainerLight
import com.cookainno.mobile.onSecondaryDark
import com.cookainno.mobile.onSecondaryLight
import com.cookainno.mobile.onSurfaceDark
import com.cookainno.mobile.onSurfaceLight
import com.cookainno.mobile.onSurfaceVariantDark
import com.cookainno.mobile.onSurfaceVariantLight
import com.cookainno.mobile.onTertiaryContainerDark
import com.cookainno.mobile.onTertiaryContainerLight
import com.cookainno.mobile.onTertiaryDark
import com.cookainno.mobile.onTertiaryLight
import com.cookainno.mobile.outlineDark
import com.cookainno.mobile.outlineLight
import com.cookainno.mobile.outlineVariantDark
import com.cookainno.mobile.outlineVariantLight
import com.cookainno.mobile.primaryContainerDark
import com.cookainno.mobile.primaryContainerLight
import com.cookainno.mobile.primaryDark
import com.cookainno.mobile.primaryLight
import com.cookainno.mobile.scrimDark
import com.cookainno.mobile.scrimLight
import com.cookainno.mobile.secondaryContainerDark
import com.cookainno.mobile.secondaryContainerLight
import com.cookainno.mobile.secondaryDark
import com.cookainno.mobile.secondaryLight
import com.cookainno.mobile.surfaceBrightDark
import com.cookainno.mobile.surfaceBrightLight
import com.cookainno.mobile.surfaceContainerDark
import com.cookainno.mobile.surfaceContainerHighDark
import com.cookainno.mobile.surfaceContainerHighLight
import com.cookainno.mobile.surfaceContainerHighestDark
import com.cookainno.mobile.surfaceContainerHighestLight
import com.cookainno.mobile.surfaceContainerLight
import com.cookainno.mobile.surfaceContainerLowDark
import com.cookainno.mobile.surfaceContainerLowLight
import com.cookainno.mobile.surfaceContainerLowestDark
import com.cookainno.mobile.surfaceContainerLowestLight
import com.cookainno.mobile.surfaceDark
import com.cookainno.mobile.surfaceDimDark
import com.cookainno.mobile.surfaceDimLight
import com.cookainno.mobile.surfaceLight
import com.cookainno.mobile.surfaceVariantDark
import com.cookainno.mobile.surfaceVariantLight
import com.cookainno.mobile.tertiaryContainerDark
import com.cookainno.mobile.tertiaryContainerLight
import com.cookainno.mobile.tertiaryDark
import com.cookainno.mobile.tertiaryLight
import com.example.ui.theme.AppTypography

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

@Composable
fun CookainnoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> darkScheme
      else -> lightScheme
  }
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = colorScheme.primary.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}

