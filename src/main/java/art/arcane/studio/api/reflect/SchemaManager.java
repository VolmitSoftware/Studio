package art.arcane.studio.api.reflect;

import art.arcane.studio.api.StudioEngine;
import art.arcane.studio.api.loader.StudioIO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SchemaManager {
    private StudioEngine engine;
    private Map<Class<?>, StudioObject> objects;

    public SchemaManager(StudioEngine engine)
    {
        this.engine = engine;
        this.objects = new ConcurrentHashMap<>();
    }

    public void buildSchema()
    {
        if(engine.getRepository().popRegistriesChangedState())
        {
            updateObjectRegistry();
        }
    }

    private void updateObjectRegistry() {
        engine.getRepository().getSectors().stream().filter(StudioIO::isJson)
                .forEach(i -> rippleRegister(i.getLoadedClass()));
    }

    private void rippleRegister(Class<?> c)
    {
        if(c == null || objects.containsKey(c))
        {
            return;
        }

        StudioObject so = new StudioObject(c);
        objects.put(c, so);

        for(StudioProperty i : so.getProperties())
        {
            rippleRegister(i.getType());
            rippleRegister(i.getListType());
        }
    }
}
