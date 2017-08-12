package jp.co.waja.config;

import jp.co.waja.app.util.WorkTimes;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.*;

public class ThymeleafObjectDialect implements IExpressionObjectDialect {

	private static final Set<String> EXPRESSION_OBJECT_NAMES;

	private static final Map<String, Object> EXPRESSION_OBJECTS;

	static {
		Map<String, Object> objects = new HashMap<>();
		objects.put("workTimes", new WorkTimes());
		EXPRESSION_OBJECTS = Collections.unmodifiableMap(objects);

		Set<String> names = new HashSet<>();
		names.addAll(objects.keySet());
		EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(names);
	}

	@Override
	public IExpressionObjectFactory getExpressionObjectFactory() {
		return new IExpressionObjectFactory() {
			@Override
			public Set<String> getAllExpressionObjectNames() {
				return EXPRESSION_OBJECT_NAMES;
			}

			@Override
			public Object buildObject(IExpressionContext context, String expressionObjectName) {
				return EXPRESSION_OBJECTS.get(expressionObjectName);
			}

			@Override
			public boolean isCacheable(String expressionObjectName) {
				return false;
			}
		};
	}

	@Override
	public String getName() {
		return "ThymeleafDialect";
	}
}
