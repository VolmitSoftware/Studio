package art.arcane.studio.api.reflect;

import art.arcane.studio.api.StudioEngine;
import art.arcane.studio.api.annotation.Studio;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.List;

@Data
public class StudioProperty {
    private String tooltip;
    private Double maxValue;
    private Double minValue;
    private Integer maxLength;
    private Integer minLength;
    private Class<?> listType;
    private boolean required;
    private String deprecated;
    private Field field;
    private String name;
    private Class<?> type;
    private List<String> possibilities;

    public StudioProperty(Field field, StudioEngine engine)
    {
        this.field = field;
        this.name = field.getName();
        this.type = field.getType();
        this.tooltip = Studio.get(field, Studio.Tooltip.class).map(i -> i.value()).orElse("No Description");
        this.maxValue = Studio.get(field, Studio.MaxValue.class).map(i -> i.value()).orElse(null);
        this.minValue = Studio.get(field, Studio.MinValue.class).map(i -> i.value()).orElse(null);
        this.maxLength = Studio.get(field, Studio.MaxLength.class).map(i -> i.value()).orElse(null);
        this.minLength = Studio.get(field, Studio.MinLength.class).map(i -> i.value()).orElse(null);
        this.listType = Studio.get(field, Studio.ListType.class).map(i -> i.value()).orElse(null);
        this.required = Studio.get(field, Studio.Required.class).map(i -> true).orElse(false);
        this.deprecated = Studio.get(field, Studio.Deprecated.class).map(i -> i.value()).orElse(null);
        this.possibilities = Studio.get(field, Studio.AutoCompleteSector.class)
                .map(i -> engine.getRepository().getSector(i.value()).getLoadPossibilities()).orElse(null);
    }
}
