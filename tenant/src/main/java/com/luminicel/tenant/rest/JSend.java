package com.luminicel.tenant.rest;

import java.util.List;

public interface JSend<T> {
    String getStatus();
	int getCode();
	String getMessage();
	T getData();
	List<String> getStack();
}
