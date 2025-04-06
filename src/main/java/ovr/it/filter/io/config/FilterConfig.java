package ovr.it.filter.io.config;

import lombok.Getter;
import lombok.Setter;
import ovr.it.filter.io.FilterProcessor;
import ovr.it.filter.io.serialization.FilterSerializer;

public class FilterConfig {
    @Getter
    @Setter
    private static FilterProcessor<String> processor = new FilterSerializer();

}