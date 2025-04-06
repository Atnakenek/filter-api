package ovr.it.filter.api.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ovr.it.filter.api.Filter;
import ovr.it.filter.io.FilterProcessor;
import ovr.it.filter.io.config.FilterConfig;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class PropertyExistFilter implements Filter {

    private final String property;

    @Override
    public boolean matches(Map<String, String> asset) {
        return asset != null && asset.containsKey(property);
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
