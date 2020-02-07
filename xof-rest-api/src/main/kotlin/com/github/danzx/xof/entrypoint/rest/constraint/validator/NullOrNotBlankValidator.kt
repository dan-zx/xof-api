package com.github.danzx.xof.entrypoint.rest.constraint.validator

import com.github.danzx.xof.entrypoint.rest.constraint.NullOrNotBlank

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NullOrNotBlankValidator : ConstraintValidator<NullOrNotBlank, String?> {
    override fun isValid(value: String?, context: ConstraintValidatorContext) = value == null || value.isNotBlank()
}
