package ovr.it.filter.io.serialization;

import ovr.it.filter.api.impl.*;
import ovr.it.filter.io.FilterProcessor;

public class FilterSerializer implements FilterProcessor<String> {

    @Override
    public String process(AndFilter f) {
        return "(" + f.getLeft().processWith(this) + " AND " + f.getRight().processWith(this) + ")";
    }

    @Override
    public String process(OrFilter f) {
        return "(" + f.getLeft().processWith(this) + " OR " + f.getRight().processWith(this) + ")";
    }

    @Override
    public String process(NotFilter f) {
        return "NOT(" + f.getFilter().processWith(this) + ")";
    }

    @Override
    public String process(ValueEqualFilter f) {
        return f.getProperty() + " = \"" + f.getValue() + "\"";
    }

    @Override
    public String process(ValueGreaterFilter f) {
        return f.getProperty() + " > \"" + f.getRightValue() + "\"";
    }

    @Override
    public String process(ValueGreaterEqualFilter f) {
        return f.getProperty() + " >= \"" + f.getRightValue() + "\"";
    }

    @Override
    public String process(ValueLessFilter f) {
        return f.getProperty() + " < \"" + f.getRightValue() + "\"";
    }

    @Override
    public String process(ValueLessEqualFilter f) {
        return f.getProperty() + " <= \"" + f.getRightValue() + "\"";
    }

    @Override
    public String process(PropertyExistFilter f) {
        return "EXISTS(" + f.getProperty() + ")";
    }

    @Override
    public String process(PropertiesEqualFilter f) {
        return f.getLeftProp() + " = " + f.getRightProp();
    }

    @Override
    public String process(BooleanFilter f) {
        return f.isValue() ? "TRUE" : "FALSE";
    }
}
