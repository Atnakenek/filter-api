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
public class PropertiesEqualFilter implements Filter {

    private final String leftProp;
    private final String rightProp;

    @Override
    public boolean matches(Map<String, String> asset) {
        boolean isMatch = false;
        if (asset != null) {
            String leftValue = asset.get(leftProp);
            String rightValue = asset.get(rightProp);
            isMatch = StringUtils.equalsIgnoreCase(leftValue, rightValue);
        }
        return isMatch;
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
