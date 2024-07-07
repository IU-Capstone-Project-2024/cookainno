package com.example.compose
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.cookainno.mobile.ui.theme.backgroundDark
import com.cookainno.mobile.ui.theme.backgroundLight
import com.cookainno.mobile.ui.theme.errorContainerDark
import com.cookainno.mobile.ui.theme.errorContainerLight
import com.cookainno.mobile.ui.theme.errorDark
import com.cookainno.mobile.ui.theme.errorLight
import com.cookainno.mobile.ui.theme.inverseOnSurfaceDark
import com.cookainno.mobile.ui.theme.inverseOnSurfaceLight
import com.cookainno.mobile.ui.theme.inversePrimaryDark
import com.cookainno.mobile.ui.theme.inversePrimaryLight
import com.cookainno.mobile.ui.theme.inverseSurfaceDark
import com.cookainno.mobile.ui.theme.inverseSurfaceLight
import com.cookainno.mobile.ui.theme.onBackgroundDark
import com.cookainno.mobile.ui.theme.onBackgroundLight
import com.cookainno.mobile.ui.theme.onErrorContainerDark
import com.cookainno.mobile.ui.theme.onErrorContainerLight
import com.cookainno.mobile.ui.theme.onErrorDark
import com.cookainno.mobile.ui.theme.onErrorLight
import com.cookainno.mobile.ui.theme.onPrimaryContainerDark
import com.cookainno.mobile.ui.theme.onPrimaryContainerLight
import com.cookainno.mobile.ui.theme.onPrimaryDark
import com.cookainno.mobile.ui.theme.onPrimaryLight
import com.cookainno.mobile.ui.theme.onSecondaryContainerDark
import com.cookainno.mobile.ui.theme.onSecondaryContainerLight
import com.cookainno.mobile.ui.theme.onSecondaryDark
import com.cookainno.mobile.ui.theme.onSecondaryLight
import com.cookainno.mobile.ui.theme.onSurfaceDark
import com.cookainno.mobile.ui.theme.onSurfaceLight
import com.cookainno.mobile.ui.theme.onSurfaceVariantDark
import com.cookainno.mobile.ui.theme.onSurfaceVariantLight
import com.cookainno.mobile.ui.theme.onTertiaryContainerDark
import com.cookainno.mobile.ui.theme.onTertiaryContainerLight
import com.cookainno.mobile.ui.theme.onTertiaryDark
import com.cookainno.mobile.ui.theme.onTertiaryLight
import com.cookainno.mobile.ui.theme.outlineDark
import com.cookainno.mobile.ui.theme.outlineLight
import com.cookainno.mobile.ui.theme.outlineVariantDark
import com.cookainno.mobile.ui.theme.outlineVariantLight
import com.cookainno.mobile.ui.theme.primaryContainerDark
import com.cookainno.mobile.ui.theme.primaryContainerLight
import com.cookainno.mobile.ui.theme.primaryDark
import com.cookainno.mobile.ui.theme.primaryLight
import com.cookainno.mobile.ui.theme.scrimDark
import com.cookainno.mobile.ui.theme.scrimLight
import com.cookainno.mobile.ui.theme.secondaryContainerDark
import com.cookainno.mobile.ui.theme.secondaryContainerLight
import com.cookainno.mobile.ui.theme.secondaryDark
import com.cookainno.mobile.ui.theme.secondaryLight
import com.cookainno.mobile.ui.theme.surfaceBrightDark
import com.cookainno.mobile.ui.theme.surfaceBrightLight
import com.cookainno.mobile.ui.theme.surfaceContainerDark
import com.cookainno.mobile.ui.theme.surfaceContainerHighDark
import com.cookainno.mobile.ui.theme.surfaceContainerHighLight
import com.cookainno.mobile.ui.theme.surfaceContainerHighestDark
import com.cookainno.mobile.ui.theme.surfaceContainerHighestLight
import com.cookainno.mobile.ui.theme.surfaceContainerLight
import com.cookainno.mobile.ui.theme.surfaceContainerLowDark
import com.cookainno.mobile.ui.theme.surfaceContainerLowLight
import com.cookainno.mobile.ui.theme.surfaceContainerLowestDark
import com.cookainno.mobile.ui.theme.surfaceContainerLowestLight
import com.cookainno.mobile.ui.theme.surfaceDark
import com.cookainno.mobile.ui.theme.surfaceDimDark
import com.cookainno.mobile.ui.theme.surfaceDimLight
import com.cookainno.mobile.ui.theme.surfaceLight
import com.cookainno.mobile.ui.theme.surfaceVariantDark
import com.cookainno.mobile.ui.theme.surfaceVariantLight
import com.cookainno.mobile.ui.theme.tertiaryContainerDark
import com.cookainno.mobile.ui.theme.tertiaryContainerLight
import com.cookainno.mobile.ui.theme.tertiaryDark
import com.cookainno.mobile.ui.theme.tertiaryLight
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

