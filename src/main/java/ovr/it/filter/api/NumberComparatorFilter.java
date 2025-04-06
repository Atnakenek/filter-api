package ovr.it.filter.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public abstract class NumberComparatorFilter implements Filter {

    private final String property;
    private final String rightValue;

    protected abstract boolean compare(double value, double checkValue);

    @Override
    public boolean matches(Map<String, String> asset) {
        boolean isMatch = false;
        if (asset != null) {
            String leftValue = asset.get(property);
            if (NumberUtils.isParsable(leftValue) && NumberUtils.isParsable(rightValue)) {
                isMatch = compare(Double.parseDouble(leftValue), Double.parseDouble(rightValue));
            }
        }
        return isMatch;
    }
}
