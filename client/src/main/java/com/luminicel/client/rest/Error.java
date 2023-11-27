package com.luminicel.client.rest;

public final class Error<T> implements JSend<T>{

	private final String status = "error";
	private final int code;
	private final String message;
	private final T data;

	public Error(final int code, final String message){
		this.data = null;
		this.message = message;
		this.code = code;
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
	public String getMessage() {
		return message;
	}

	@Override
	public T getData() {
		return data;
	}
}