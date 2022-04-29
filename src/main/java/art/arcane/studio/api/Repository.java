package art.arcane.studio.api;

import art.arcane.studio.api.loader.StudioIO;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Repository
{
    private final Map<String, StudioIO<?>> sectors;
    private final File folder;
    private boolean registriesChanged;

    public Repository(File folder)
    {
        this.folder = folder;
        sectors = new ConcurrentHashMap<>();
        registriesChanged = false;
    }

    public StudioIO<?> getSector(Class<?> type)
    {
        for(StudioIO<?> i : getSectors())
        {
            if(i.getLoadedClass().equals(type))
            {
                return i;
            }
        }

        return null;
    }

    public Collection<StudioIO<?>> getSectors()
    {
        return sectors.values();
    }

    public void dump()
    {
        sectors.values().forEach(StudioIO::dump);
    }

    public List<String> getPossibilities(String sector)
    {
        StudioIO<?> io = sectors.get(sector);

        if(io == null)
        {
            return List.of();
        }

        return io.getLoadPossibilities();
    }

    public <T> T load(String sector, String object)
    {
        StudioIO<T> io = (StudioIO<T>) sectors.get(sector);

        if(io == null)
        {
            return null;
        }

        try {
            return io.load(object);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean popRegistriesChangedState()
    {
        return registriesChanged = false;
    }

    public void registerIOSector(StudioIO<?> io)
    {
        sectors.put(io.getTypeName(), io);
        registriesChanged = true;
    }

    public File getFolder()
    {
        return folder;
    }
}
