package ovr.it.filter.api;

import ovr.it.filter.io.FilterProcessor;

import java.util.Map;

public interface Filter {

    boolean matches(Map<String, String> asset);

    <R> R processWith(FilterProcessor<R> processor);
}
