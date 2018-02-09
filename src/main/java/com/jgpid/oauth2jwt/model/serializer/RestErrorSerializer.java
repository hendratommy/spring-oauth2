package com.jgpid.oauth2jwt.model.serializer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.jgpid.oauth2jwt.model.form.RestError;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class RestErrorSerializer extends StdSerializer<RestError> {
	private static final long serialVersionUID = 1L;

	public RestErrorSerializer() {
		this(null);
	}

	public RestErrorSerializer(Class<RestError> t) {
		super(t);
	}

	@Override
	public void serialize(RestError value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeStringField("status", value.getStatus().name());
		gen.writeObjectFieldStart("errors");
		if (value.getErrors() != null && !value.getErrors().isEmpty()) {
			for (Map.Entry<String, Object> entry : value.getErrors().entrySet()) {
				if (entry.getValue() != null) {
					gen.writeObjectField(entry.getKey(), entry.getValue());
				}
			}
		}
		if (value.getBindingResult() != null && value.getBindingResult().hasFieldErrors()) {
			List<FieldError> fieldErrors = value.getBindingResult().getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				if (fieldError != null && fieldError.getDefaultMessage() != null
						&& !fieldError.getDefaultMessage().isEmpty()) {
					gen.writeStringField(fieldError.getField(), fieldError.getDefaultMessage());
				}
			}
		}
		gen.writeEndObject();
		gen.writeEndObject();
	}
}
