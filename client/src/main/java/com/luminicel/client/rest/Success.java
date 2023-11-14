package com.luminicel.client.rest;

import java.util.Collections;
import java.util.List;

public class Success<T> implements JSend<T> {
	private final String status = "ok";
	private final int code = 200;
	private final String message;
	private final T data;

	public static <T> Builder<T> builder() {
		return new Builder<>();
	}

	public static final class Builder<T> {

		private String message;
		private T data;

		private Builder() {
		}

		public Builder<T> message(final String message) {
			this.message = message;
			return this;
		}

		public Builder<T> data(final T data) {
			this.data = data;
			return this;
		}

		public JSend<T> build() {
			return new Success<>(this);
		}
	}

	private Success(final Builder<T> builder) {
		data = builder.data;
		message = builder.message;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public List<String> getStack() {
		return Collections.emptyList();
	}
}
