package ovr.it.filter.io;

import ovr.it.filter.api.impl.*;

public interface FilterProcessor<R> {

    default R process(AndFilter filter) {
        throw new UnsupportedOperationException("Not supported");
    }

    default R process(BooleanFilter filter) {
        throw new UnsupportedOperationException("Not supported");
    }

    default R process(PropertiesEqualFilter filter) {
        throw new UnsupportedOperationException("Not supported");
    }

    default R process(ValueEqualFilter filter) {
        throw new UnsupportedOperationException("Not supported");
    }

    default R process(ValueGreaterEqualFilter filter) {
        throw new UnsupportedOperationException("Not supported");
    }

    default R process(ValueGreaterFilter filter) {
        throw new UnsupportedOperationException("Not supported");
    }

    default R process(ValueLessEqualFilter filter) {
        throw new UnsupportedOperationException("Not supported");
    }

    default R process(ValueLessFilter filter) {
        throw new UnsupportedOperationException("Not supported");
    }

    default R process(NotFilter filter) {
        throw new UnsupportedOperationException("Not supported");
    }

    default R process(OrFilter filter) {
        throw new UnsupportedOperationException("Not supported");
    }

    default R process(PropertyExistFilter filter) {
        throw new UnsupportedOperationException("Not supported");
    }
}
