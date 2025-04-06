package ovr.it.filter.api.impl;

import ovr.it.filter.api.NumberComparatorFilter;
import ovr.it.filter.io.FilterProcessor;
import ovr.it.filter.io.config.FilterConfig;

public class ValueLessFilter extends NumberComparatorFilter {

    public ValueLessFilter(String property, String value) {
        super(property, value);
    }

    @Override
    protected boolean compare(double value, double checkValue) {
        return value < checkValue;
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
