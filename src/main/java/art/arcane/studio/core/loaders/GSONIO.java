package art.arcane.studio.core.loaders;

import art.arcane.studio.api.loader.CachedIO;
import art.arcane.studio.api.loader.StudioReader;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GSONIO<T> extends CachedIO<T> {
    private static final Gson gson = new Gson();

    public GSONIO(Class<T> type, File folder, String displayName, String displayNamePlural) {
        super(new GSONReader<>(type), folder, "json", true, displayName, displayNamePlural);
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
