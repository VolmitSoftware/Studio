package art.arcane.studio.api.loader;

import art.arcane.studio.api.loader.DirectIO;
import art.arcane.studio.api.loader.StudioReader;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedIO<T> extends DirectIO<T> {
    private final Map<File, T> cache;

    public CachedIO(StudioReader<T> reader, File folder, String extension, boolean textual, String displayName) {
        super(reader, folder, extension, textual, displayName);
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void dump()
    {
        cache.clear();
    }

    public T load(File file) throws IOException {
        try
        {
            return cache.computeIfAbsent(file, (f) -> {
                try {
                    return super.load(f);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        catch(Throwable e)
        {
            throw new IOException(e);
        }
    }
}
