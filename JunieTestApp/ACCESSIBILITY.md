# Accessibility in Yarn Stash Application

This document summarizes how accessibility was taken into account in the Yarn Stash application.

## Content Descriptions

The application extensively uses content descriptions for all interactive UI elements to ensure compatibility with screen readers:

- **Navigation Icons**: All navigation icons (back, edit, delete, etc.) have proper content descriptions set using the `semantics` modifier with `contentDescription` property.
- **Action Buttons**: Buttons for actions like "Add Yarn", "View Needles", etc. have descriptive content descriptions.
- **Images**: Images, such as yarn photos, include content descriptions that describe what the image represents.

Example from YarnDetailScreen.kt:
```kotlin
val backDescription = stringResource(id = R.string.back)
IconButton(
    onClick = onBackClick,
    modifier = Modifier.semantics {
        contentDescription = backDescription
    }
) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = null  // Content description set on parent
    )
}
```

## Avoiding Duplicate Announcements

The application follows the best practice of setting content descriptions on parent components while setting `contentDescription = null` on child components to avoid duplicate screen reader announcements:

- Parent components (like IconButton) have semantic content descriptions
- Child components (like Icon) have null content descriptions

## String Resources

All text in the application, including accessibility descriptions, is defined in string resources (strings.xml):

- Organized by categories (Navigation, Common, Home Screen, Yarn, Knitting Needles)
- Enables localization for accessibility in multiple languages
- Provides consistent terminology throughout the application

## Typography and Text Scaling

The application uses Material3 Typography with scalable pixels (sp) for font sizes:

- Allows text to scale based on user's system font size settings
- Supports users with visual impairments who need larger text
- Maintains proper line height and letter spacing for readability

## Material Design Components

The application uses Material3 components which have built-in accessibility features:

- Proper contrast ratios
- Touch target sizes
- Focus indicators
- Support for keyboard navigation

## Semantic Structure

UI elements are organized in a logical semantic structure:

- Proper hierarchy of components
- Logical tab order
- Clear labeling of form fields and controls

## Summary

The Yarn Stash application demonstrates a strong commitment to accessibility through:

1. Comprehensive content descriptions for screen reader support
2. Proper handling of nested content descriptions to avoid duplicate announcements
3. Use of string resources for all text including accessibility labels
4. Scalable typography that respects system font size settings
5. Material Design components with built-in accessibility features
6. Logical semantic structure for navigation

These implementations ensure the application is usable by people with various disabilities, including visual impairments, and follows modern accessibility best practices for Android applications.