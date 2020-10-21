package com.census;

public class CSVBuilderFactory<E> {
	public static ICSVBuilder createCSVBuilder() {
		return new OpenCSVBuilder<>();
	}
}