package art.arcane.studio.api.reflect;

import art.arcane.studio.api.annotation.Studio;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class StudioObject {
    private final String tooltip;
    private final List<StudioProperty> properties;

    public StudioObject(Class<?> t)
    {
        this.tooltip = Studio.get(t, Studio.Tooltip.class).map(i -> ((Studio.Tooltip)i).value()).orElse("No Description");
        properties = Arrays.stream(t.getDeclaredFields()).map(StudioProperty::new).toList();
    }
}
