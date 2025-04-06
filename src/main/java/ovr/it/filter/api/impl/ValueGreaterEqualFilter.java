package ovr.it.filter.api.impl;

import ovr.it.filter.api.NumberComparatorFilter;
import ovr.it.filter.io.FilterProcessor;
import ovr.it.filter.io.config.FilterConfig;

public class ValueGreaterEqualFilter extends NumberComparatorFilter {

    public ValueGreaterEqualFilter(String property, String value) {
        super(property, value);
    }

    @Override
    public boolean compare(double val, double checkValue) {
        return val >= checkValue;
    }

    @Override
    public <R> R processWith(FilterProcessor<R> processor) {
        return processor.process(this);
    }

    @Override
    public String toString() {
        return this.processWith(FilterConfig.getProcessor());
    }
}
