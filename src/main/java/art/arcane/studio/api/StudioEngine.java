package art.arcane.studio.api;

import art.arcane.studio.api.annotation.Studio;
import art.arcane.studio.api.io.FolderWatcher;
import art.arcane.studio.api.io.JarScanner;
import art.arcane.studio.api.loader.StudioIO;
import art.arcane.studio.api.reflect.SchemaManager;
import art.arcane.studio.core.loaders.GSONIO;
import lombok.Data;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Data
public class StudioEngine
{
    private final Repository repository;
    private final FolderWatcher watcher;
    private final SchemaManager schema;
    private final File folder;

    public StudioEngine(File folder)
    {
        this.folder = folder;
        this.repository = new Repository(folder);
        this.watcher = new FolderWatcher(folder);
        this.schema = new SchemaManager(this);
        update(true);
    }

    public void update()
    {
        update(false);
    }

    public void update(boolean force)
    {
        if(force)
        {
            repository.dump();
            schema.buildSchema();
        }

        else {
            boolean needsRefresh = false;
            watcher.checkModified();
            Set<StudioIO<?>> modifiedSectors = new HashSet<>();
            Set<File> changes = new HashSet<>();
            changes.addAll(watcher.getChanged());
            changes.addAll(watcher.getDeleted());
            changes.addAll(watcher.getCreated());

            for(File i : changes)
            {
                if(isHotFile(i))
                {
                    needsRefresh = true;
                    modifiedSectors.add(getIOSector(i));
                }
            }

            if(needsRefresh)
            {
                for(StudioIO<?> i : modifiedSectors)
                {
                    i.dump();
                }

                schema.buildSchema();
            }
        }

        watcher.checkModified();
    }

    public boolean isHotFile(File file)
    {
        if(file.isDirectory())
        {
            for(File i : file.listFiles())
            {
                if(isHotFile(i))
                {
                    return true;
                }
            }
        }

        else {
            for(StudioIO<?> i : repository.getSectors())
            {
                if(i.isValidFile(file))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public StudioIO<?> getIOSector(File file)
    {
        if(file.isDirectory())
        {
            for(File i : file.listFiles())
            {
                if(isHotFile(i))
                {
                    return getIOSector(i);
                }
            }
        }

        else {
            for(StudioIO<?> i : repository.getSectors())
            {
                if(i.isValidFile(file))
                {
                    return i;
                }
            }
        }

        return null;
    }

    public void registerJsonSectors(File jar)
    {
        JarScanner j = new JarScanner(jar, "");
        j.getClasses().parallelStream().filter(i -> i.isAnnotationPresent(Studio.Object.class)).forEach((i)
                -> registerSector(new GSONIO<>(i, folder, Studio.get(i, Studio.Object.class).map(ix -> ix.value())
                .orElse(i.getSimpleName()))));
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
