package com.eevajonna.claudetestapp.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.text
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.accessibleClickable(
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed {
    val interactionSource = remember { MutableInteractionSource() }
    this
        .semantics {
            if (onClickLabel != null) {
                contentDescription = onClickLabel
            }
            if (role != null) {
                this.role = role
            }
        }
        .combinedClickable(
            interactionSource = interactionSource,
            indication = ripple(),
            onClick = onClick
        )
}

// Ensure minimum touch target size for accessibility (48dp minimum)
fun Modifier.accessibleTouchTarget(
    minSize: Dp = 48.dp
) = this.defaultMinSize(minWidth = minSize, minHeight = minSize)

// Support for large text scaling based on system settings
fun Modifier.supportLargeText() = composed {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    // Get system font scale
    val fontScale = configuration.fontScale

    // Apply additional scaling for large text mode
    if (fontScale >= 1.3f) {
        // Add extra padding for larger text to prevent clipping
        this.defaultMinSize(minHeight = (48 * fontScale).dp)
    } else {
        this
    }
}

// Enhanced text styles that respond to accessibility settings
@Composable
fun getAccessibleTextStyle(
    baseStyle: TextStyle,
    fontScale: Float = LocalConfiguration.current.fontScale,
    isLargeTextEnabled: Boolean = false
): TextStyle {
    val scaleFactor = when {
        isLargeTextEnabled -> maxOf(fontScale, 1.5f)
        fontScale >= 1.3f -> fontScale * 1.1f // Additional boost for system large text
        else -> fontScale
    }

    return baseStyle.copy(
        fontSize = baseStyle.fontSize * scaleFactor,
        lineHeight = baseStyle.lineHeight * scaleFactor
    )
}

// High contrast color support
@Composable
fun getAccessibleColor(
    normalColor: Color,
    isHighContrastEnabled: Boolean = false
): Color {
    return if (isHighContrastEnabled) {
        // Return high contrast version of the color
        when (normalColor) {
            MaterialTheme.colorScheme.primary -> Color.Black
            MaterialTheme.colorScheme.onSurface -> Color.Black
            MaterialTheme.colorScheme.onSurfaceVariant -> Color(0xFF444444)
            else -> normalColor
        }
    } else {
        normalColor
    }
}

// Semantic helpers for common UI patterns
fun Modifier.accessibleButton(
    label: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) = this
    .accessibleTouchTarget()
    .semantics {
        contentDescription = label
        role = Role.Button
        if (!isEnabled) {
            disabled()
        }
    }

fun Modifier.accessibleTextField(
    label: String,
    value: String,
    isRequired: Boolean = false
) = this.semantics {
    contentDescription = if (isRequired) "$label, required field" else label
    text = AnnotatedString(value)
    // role = Role.TextField <-- This one doesn't exist
}

fun Modifier.accessibleDropdown(
    label: String,
    selectedValue: String,
    isExpanded: Boolean = false
) = this.semantics {
    contentDescription = "$label, current selection: $selectedValue"
    role = Role.DropdownList
    stateDescription = if (isExpanded) "Expanded" else "Collapsed"
}

// Screen reader announcements
object AccessibilityAnnouncements {
    const val ITEM_ADDED = "Item added to collection"
    const val ITEM_DELETED = "Item removed from collection"
    const val ITEM_UPDATED = "Item details updated"
    const val NAVIGATION_CHANGE = "Screen changed"
    const val SEARCH_RESULTS = "Search results updated"
    const val FORM_ERROR = "Form has errors, please review"
    const val FORM_SAVED = "Information saved successfully"
}

// Focus management utilities
object FocusUtils {
    /**
     * Determines the next focusable element in a form
     */
    fun getNextFocusDirection(currentFieldIndex: Int, totalFields: Int): FocusDirection {
        return when {
            currentFieldIndex < totalFields - 1 -> FocusDirection.Down
            else -> FocusDirection.Exit
        }
    }

    /**
     * Creates a content description for form fields with validation state
     */
    fun createFieldDescription(
        label: String,
        isRequired: Boolean = false,
        hasError: Boolean = false,
        errorMessage: String? = null
    ): String {
        return buildString {
            append(label)
            if (isRequired) append(", required field")
            if (hasError && errorMessage != null) append(", error: $errorMessage")
        }
    }

    /**
     * Creates accessible descriptions for list items
     */
    fun createListItemDescription(
        itemName: String,
        itemDetails: String,
        position: Int,
        totalItems: Int
    ): String {
        return "$itemName, $itemDetails. Item $position of $totalItems. Tap for details."
    }
}

// Keyboard navigation helpers
object KeyboardNavigationUtils {
    /**
     * Standard IME actions for form navigation
     */
    fun getImeAction(isLastField: Boolean): androidx.compose.ui.text.input.ImeAction {
        return if (isLastField) {
            androidx.compose.ui.text.input.ImeAction.Done
        } else {
            androidx.compose.ui.text.input.ImeAction.Next
        }
    }
}

// Switch control and external keyboard support
fun Modifier.switchControlSupport() = this.semantics {
    // Enable switch control scanning
    traversalIndex = 0f
}

// Screen orientation change announcements
@Composable
fun getOrientationDescription(): String {
    val configuration = LocalConfiguration.current
    return if (configuration.screenWidthDp > configuration.screenHeightDp) {
        "Landscape orientation"
    } else {
        "Portrait orientation"
    }
}

// Voice control command helpers
object VoiceControlUtils {
    const val ADD_YARN_VOICE_COMMAND = "Add yarn"
    const val ADD_NEEDLE_VOICE_COMMAND = "Add needle"
    const val SEARCH_VOICE_COMMAND = "Search"
    const val GO_BACK_VOICE_COMMAND = "Go back"
    const val SAVE_VOICE_COMMAND = "Save"
    const val CANCEL_VOICE_COMMAND = "Cancel"
}
