package art.arcane.studio.api.loader;

import java.io.File;
import java.io.IOException;

@FunctionalInterface
public interface StudioReader<T> {
    T read(File file) throws IOException;
}
