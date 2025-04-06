package ovr.it.filter.api.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ovr.it.filter.api.Filter;
import ovr.it.filter.io.FilterProcessor;
import ovr.it.filter.io.config.FilterConfig;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ValueEqualFilter implements Filter {

    private final String property;
    private final String value;

    @Override
    public boolean matches(Map<String, String> asset) {
        return asset != null && StringUtils.equalsAnyIgnoreCase(asset.get(property), value);
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
