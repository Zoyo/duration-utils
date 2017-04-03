package br.com.rsystem.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.rsystem.config.DurationSymbols;

public class DurationUtils {
	public static void ensureGreaterThanZero(int num, String field) throws IllegalArgumentException {
		if(num <= 0) {
			throw new IllegalArgumentException(field + " must be grather than 0");
		}
	}
	
	public static void ensureObjectNotNull(Object obj, String field) throws IllegalArgumentException {
		if(obj == null) {
			throw new IllegalArgumentException(field + " should not be null");
		}
	}

	public static void ensureHasNoInvalidSymbol(String symbol) throws IllegalArgumentException {
		if(symbol == null) {
			throw new IllegalArgumentException("Symbol should not be null");
		}
		
		int symbolSize = symbol.length();
		int symbolSizeWithoutSeparator = symbol.replaceAll(DurationSymbols.SYMBOL_SEPARATOR_PATTERN, "").length();
		
		if(symbolSize != symbolSizeWithoutSeparator) {
			throw new IllegalArgumentException("Hyphen (-), underscore (_), slash (/), comma (,), asterisk (*) and blank space can not be used as a symbol");
		}
	}
	
	public static Properties loadPropertiesFrom(InputStream inputStreamProperties) throws IllegalArgumentException {
		Properties props = new Properties();
		try {
			props.load(inputStreamProperties);
			
		} catch (IOException e) {
			throw new IllegalArgumentException("Error loading propeties", e);
			
		} finally {
			try {
				inputStreamProperties.close();
				
			} catch (IOException e) {
				throw new IllegalArgumentException("Error on close file", e);
			}
		}
		
		return props;
	}
	
}
