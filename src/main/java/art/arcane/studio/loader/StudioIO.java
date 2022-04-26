package art.arcane.studio.loader;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public interface StudioIO<T> {
    static void addFiles(StudioIO<?> io, File at, List<File> list)
    {
        if(at.isDirectory())
        {
            for(File i : at.listFiles())
            {
                addFiles(io, i, list);
            }
        }

        else if(io.isValidFile(at))
        {
            list.add(at);
        }
    }

    default List<String> getLoadPossibilities()
    {
        URI p = getLoadFolder().toURI();
        return getValidFiles().stream().map((e) -> p.relativize(e.toURI()).getPath()).toList();
    }

    default List<File> getValidFiles()
    {
        List<File> f = new ArrayList<>();
        addFiles(this, getLoadFolder(), f);
        return f;
    }

    String getExtension();

    default boolean isValidFile(File f)
    {
        return f.getName().toLowerCase(Locale.ROOT).endsWith("." + getExtension().toLowerCase());
    }

    StudioReader<T> getReader();

    default T load(File file) throws IOException {
        return getReader().read(file);
    }

    default T load(String key) throws IOException {
        return load(new File(getLoadFolder(), key));
    }

    File getLoadFolder();

    String getTypeDisplayName(boolean plural);

    String getFolderName();

    boolean isText();
}
