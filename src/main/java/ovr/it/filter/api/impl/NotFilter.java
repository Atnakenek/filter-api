package ovr.it.filter.api.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ovr.it.filter.api.Filter;
import ovr.it.filter.io.FilterProcessor;
import ovr.it.filter.io.config.FilterConfig;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class NotFilter implements Filter {

    @NonNull
    private final Filter filter;

    @Override
    public boolean matches(Map<String, String> asset) {
        return !filter.matches(asset);
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
