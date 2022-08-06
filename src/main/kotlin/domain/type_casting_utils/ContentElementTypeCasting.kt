package domain.type_casting_utils

import domain.factories.ContentElementField

fun isTextElement(contentElementField: ContentElementField) =
    contentElementField.text != null && contentElementField.style != null

fun isImageElement(contentElementField: ContentElementField) =
    contentElementField.imagePath != null && contentElementField.caption != null