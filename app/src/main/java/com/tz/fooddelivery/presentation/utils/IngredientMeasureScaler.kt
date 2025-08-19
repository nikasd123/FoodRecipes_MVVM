package com.tz.fooddelivery.presentation.utils

import kotlin.math.abs

object IngredientMeasureScaler {

    fun scale(measure: String, servings: Int): String {
        if (servings == 1) return measure

        val pattern = Regex("""^(\d+\.?\d*|\d*\.?\d+|\d+\s*/\s*\d+)""")
        val match = pattern.find(measure)

        if (match != null) {
            val numberPart = match.value
            val restOfString = measure.substring(match.range.last + 1).trim()

            return try {
                if (numberPart.contains("/")) {
                    scaleFraction(numberPart, servings, restOfString)
                } else {
                    scaleDecimal(numberPart, servings, restOfString)
                }
            } catch (e: Exception) {
                measure
            }
        }
        return measure
    }

    private fun scaleFraction(fraction: String, servings: Int, unit: String): String {
        val fractionParts = fraction.split("/").map { it.trim().toDouble() }
        if (fractionParts.size != 2 || fractionParts[1] == 0.0) {
            return "$fraction $unit"
        }

        val scaledValue = (fractionParts[0] * servings) / fractionParts[1]
        return formatFraction(scaledValue) + " $unit"
    }

    private fun scaleDecimal(number: String, servings: Int, unit: String): String {
        val value = number.replace(",", ".").toDouble()
        val scaledValue = value * servings

        return if (scaledValue % 1 == 0.0) {
            "${scaledValue.toInt()} $unit"
        } else {
            "%.1f $unit".format(scaledValue).replace(".0", "")
        }
    }

    private fun formatFraction(value: Double): String {
        return when {
            value % 1 == 0.0 -> value.toInt().toString()
            value < 1 -> formatSimpleFraction(value)
            else -> {
                val whole = value.toInt()
                val fraction = value - whole
                "$whole ${formatSimpleFraction(fraction)}"
            }
        }
    }

    private fun formatSimpleFraction(value: Double): String {
        return when {
            value < 0.01 -> "0"
            abs(value - 0.125) < 0.01 -> "1/8"
            abs(value - 0.25) < 0.01 -> "1/4"
            abs(value - 0.33) < 0.01 -> "1/3"
            abs(value - 0.375) < 0.01 -> "3/8"
            abs(value - 0.5) < 0.01 -> "1/2"
            abs(value - 0.625) < 0.01 -> "5/8"
            abs(value - 0.66) < 0.01 -> "2/3"
            abs(value - 0.75) < 0.01 -> "3/4"
            abs(value - 0.875) < 0.01 -> "7/8"
            else -> "%.2f".format(value).replace(".00", "").replace("0.", ".")
        }
    }
}