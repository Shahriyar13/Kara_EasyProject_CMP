package app.duss.easyproject.presentation.components.formbuilder.form_builder

fun String.isNumeric(): Boolean {
    return this.toIntOrNull()?.let { true } ?: false
}