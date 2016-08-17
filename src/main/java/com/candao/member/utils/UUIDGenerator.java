package com.candao.member.utils;

import java.io.Serializable;
import java.util.UUID;

public class UUIDGenerator implements Generator {

	@Override
	public Serializable generate() {
		return UUID.randomUUID();
	}
}
