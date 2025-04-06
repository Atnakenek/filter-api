package ovr.it.filter.builder.matcher;

import lombok.experimental.UtilityClass;
import ovr.it.filter.api.Filter;
import ovr.it.filter.api.impl.*;

import java.util.Arrays;

@UtilityClass
public class Matchers {

    public static Filter eq(String prop, String value) {
        return new ValueEqualFilter(prop, value);
    }

    public static Filter gt(String prop, String value) {
        return new ValueGreaterFilter(prop, value);
    }

    public static Filter gte(String prop, String value) {
        return new ValueGreaterEqualFilter(prop, value);
    }

    public static Filter lt(String prop, String value) {
        return new ValueLessFilter(prop, value);
    }

    public static Filter lte(String prop, String value) {
        return new ValueLessEqualFilter(prop, value);
    }

    public static Filter eqProp(String propA, String propB) {
        return new PropertiesEqualFilter(propA, propB);
    }

    public static Filter exists(String prop) {
        return new PropertyExistFilter(prop);
    }

    public static Filter isTrue() {
        return new BooleanFilter(true);
    }

    public static Filter isFalse() {
        return new BooleanFilter(false);
    }

    // for nested AND
    public static Filter and(Filter... filters) {
        return Arrays.stream(filters)
                .reduce(AndFilter::new)
                .orElseThrow(() -> new IllegalArgumentException("Missing filters on AND input"));
    }

    // for nested OR
    public static Filter or(Filter... filters) {
        return Arrays.stream(filters)
                .reduce(OrFilter::new)
                .orElseThrow(() -> new IllegalArgumentException("Missing filters on OR input"));
    }

    public static Filter not(Filter filter) {
        return new NotFilter(filter);
    }

}
