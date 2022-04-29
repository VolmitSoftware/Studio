package art.arcane.studio.api;

import art.arcane.studio.api.loader.StudioIO;

import java.io.File;

public class StudioEngine
{
    private Repository repository;
    private final File folder;

    public StudioEngine(File folder)
    {
        this.folder = folder;
        this.repository = new Repository(folder);
    }

    public void registerSector(StudioIO<?> s)
    {
        repository.registerIOSector(s);
    }

    public <T> T get(String sector, String key)
    {
        return repository.load(sector, key);
    }

    public <T> T get(String v)
    {
        String[] m = v.split("\\Q:\\E");
        return get(m[0], m[1]);
    }
}
