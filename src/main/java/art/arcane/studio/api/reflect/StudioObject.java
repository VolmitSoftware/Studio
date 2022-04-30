package art.arcane.studio.api.reflect;

import art.arcane.studio.api.StudioEngine;
import art.arcane.studio.api.annotation.Studio;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class StudioObject {
    private final String tooltip;
    private final List<StudioProperty> properties;

    public StudioObject(Class<?> t, StudioEngine engine)
    {
        this.tooltip = Studio.get(t, Studio.Tooltip.class).map(i -> i.value()).orElse("No Description");
        properties = Arrays.stream(t.getDeclaredFields()).map(i -> new StudioProperty(i, engine)).toList();
    }
}
