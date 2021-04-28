package bbr.common.factories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyMapImpl;
import org.hibernate.property.access.spi.Setter;

public class LowerCaseResultTransformer implements ResultTransformer {

	private final Class resultClass;

	private boolean isInitialized;

	private String[] aliases;

	private Setter[] setters;

	public LowerCaseResultTransformer(Class resultClass) {
		if (resultClass == null) {
			throw new IllegalArgumentException("resultClass cannot be null");
		}
		isInitialized = false;
		this.resultClass = resultClass;
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {

		Object result;

		try {
			if (!isInitialized) {
				initialize(aliases);
			} else {
				check(aliases);
			}

			result = resultClass.newInstance();

			for (int i = 0; i < aliases.length; i++) {
				if (setters[i] != null) {

					if (tuple[i] == null) {
						setters[i].set(result, null, null);
					} else if (tuple[i] instanceof BigInteger) {
						Long longvalue = new Long(((BigInteger) tuple[i]).longValue());
						setters[i].set(result, longvalue, null);
					} else if (tuple[i] instanceof BigDecimal) {
						Double doublevalue = new Double(((BigDecimal) tuple[i]).doubleValue());
						setters[i].set(result, doublevalue, null);
					} else if (tuple[i] instanceof Short) {
						Boolean boolvalue = new Boolean((((Short) tuple[i]).shortValue()) == 1);
						setters[i].set(result, boolvalue, null);
					} else if (tuple[i] instanceof Byte) {
						Boolean boolvalue = new Boolean((((Byte) tuple[i]).shortValue()) == 1);
						setters[i].set(result, boolvalue, null);
					} else if (tuple[i] instanceof Clob) {
						InputStream stream = ((Clob) tuple[i]).getAsciiStream();
						String stringvalue = convertStreamToString(stream);
						setters[i].set(result, stringvalue, null);
					} else
						setters[i].set(result, tuple[i], null);
				}
			}

		} catch (SQLException e) {
			throw new HibernateException(e);
		} catch (IOException e) {
			throw new HibernateException(e);
		} catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		} catch (IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		}

		return result;
	}

	private void initialize(String[] aliases) {
		PropertyAccessStrategyChainedImpl propertyAccessStrategy = new PropertyAccessStrategyChainedImpl(PropertyAccessStrategyBasicImpl.INSTANCE, PropertyAccessStrategyFieldImpl.INSTANCE, PropertyAccessStrategyMapImpl.INSTANCE);
		this.aliases = new String[aliases.length];
		setters = new Setter[aliases.length];
		for (int i = 0; i < aliases.length; i++) {
			String alias = aliases[i];
			if (alias != null && !alias.equalsIgnoreCase("ROWNUMBER_")) {
				this.aliases[i] = alias;
				setters[i] = propertyAccessStrategy.buildPropertyAccess(resultClass, alias).getSetter();
			}
		}
		isInitialized = true;
	}

	private void check(String[] aliases) {
		if (!Arrays.equals(aliases, this.aliases)) {
			throw new IllegalStateException("aliases are different from what is cached; aliases=" + Arrays.asList(aliases) + " cached=" + Arrays.asList(this.aliases));
		}
	}

	private String convertStreamToString(InputStream is) throws IOException {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[] buffer) method. We iterate until the
		 * Reader return -1 which means there's no more data to read. We use the StringWriter class to produce the
		 * string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public List transformList(List collection) {
		return collection;
	}
}
