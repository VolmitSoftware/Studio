package art.arcane.studio.api.loader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Repository
{
    private final Map<String, StudioIO<?>> sectors;
    private final File folder;

    public Repository(File folder)
    {
        this.folder = folder;
        sectors = new ConcurrentHashMap<>();
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

    public void registerIOSector(StudioIO<?> io)
    {
        sectors.put(io.getTypeDisplayName(false)
                .toLowerCase(Locale.ROOT).trim().replaceAll("\\Q \\E", "-"), io);
    }

    public File getFolder()
    {
        return folder;
    }
}
