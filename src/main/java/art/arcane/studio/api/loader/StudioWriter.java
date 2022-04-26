package art.arcane.studio.api.loader;

import java.io.File;
import java.io.IOException;

@FunctionalInterface
public interface StudioWriter<T> {
    void write(File file, T t) throws IOException;
}
