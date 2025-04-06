package ovr.it.filter.builder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ovr.it.filter.api.Filter;
import ovr.it.filter.api.impl.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterBuilder {

    private Filter filter;

    public static FilterBuilder builder() {
        return new FilterBuilder();
    }

    public static FilterBuilder builder(Filter filter) {
        return new FilterBuilder(filter);
    }

    public FilterBuilder eq(String prop, String value) {
        return add(new ValueEqualFilter(prop, value));
    }

    public FilterBuilder gt(String prop, String value) {
        return add(new ValueGreaterFilter(prop, value));
    }

    public FilterBuilder gte(String prop, String value) {
        return add(new ValueGreaterEqualFilter(prop, value));
    }

    public FilterBuilder lt(String prop, String value) {
        return add(new ValueLessFilter(prop, value));
    }

    public FilterBuilder lte(String prop, String value) {
        return add(new ValueLessEqualFilter(prop, value));
    }

    public FilterBuilder exists(String prop) {
        return add(new PropertyExistFilter(prop));
    }

    public FilterBuilder eqProp(String left, String right) {
        return add(new PropertiesEqualFilter(left, right));
    }

    public FilterBuilder isTrue() {
        return add(new BooleanFilter(true));
    }

    public FilterBuilder isFalse() {
        return add(new BooleanFilter(false));
    }

    public FilterBuilder and(Filter left, Filter right) {
        return add(new AndFilter(left, right));
    }

    public FilterBuilder or(Filter left, Filter right) {
        return add(new OrFilter(left, right));
    }

    public FilterBuilder not(Filter filter) {
        return add(new NotFilter(filter));
    }

    public Filter build() {
        if (filter == null) {
            throw new IllegalStateException("No filter specified.");
        }
        return filter;
    }

    private FilterBuilder add(Filter next) {
        this.filter = next;
        return this;
    }
}
