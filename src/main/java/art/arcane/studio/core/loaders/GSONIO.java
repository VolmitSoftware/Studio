package art.arcane.studio.core.loaders;

import art.arcane.studio.api.loader.CachedIO;
import art.arcane.studio.api.loader.StudioReader;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GSONIO<T> extends CachedIO<T> {
    private static final Gson gson = new Gson();
    private final Class<T> type;

    public GSONIO(Class<T> type, File folder, String displayName) {
        super(new GSONReader<>(type), type, folder, "json", true, displayName);
        this.type = type;
    }

    @Override
    public boolean isText() {
        return true;
    }

    @Override
    public boolean isJson() {
        return true;
    }

    @Override
    public Class<?> getLoadedClass() {
        return type;
    }
    
    private static class GSONReader<T> implements StudioReader<T>{
        private final Class<T> type;

        public GSONReader(Class<T> type)
        {
            this.type = type;
        }

        @Override
        public T read(File file) throws IOException {
            FileReader fr = new FileReader(file);
            T t = gson.fromJson(fr, type);
            fr.close();
            return t;
        }
    }
}
