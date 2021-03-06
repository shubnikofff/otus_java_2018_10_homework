package ru.otus.messageSystem;

import java.util.Objects;

public final class Address {
	private final String id;

	public Address(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Address address = (Address) o;

		return Objects.equals(id, address.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return id;
	}
}
