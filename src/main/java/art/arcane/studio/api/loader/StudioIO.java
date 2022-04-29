package art.arcane.studio.api.loader;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public interface StudioIO<T> {
    void dump();

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

    Class<?> getLoadedClass();

    default List<String> getLoadPossibilities()
    {
        URI p = getLoadFolder().toURI();
        String tn = getTypeName();
        return getValidFiles().stream().map((e) -> tn + ":" + p.relativize(e.toURI()).getPath()).toList();
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
        return f.getAbsolutePath().startsWith(getLoadFolder().getAbsolutePath()) && f.getName().toLowerCase(Locale.ROOT).endsWith("." + getExtension().toLowerCase());
    }

    StudioReader<T> getReader();

    default T load(File file) throws IOException {
        return getReader().read(file);
    }

    default T load(String key) throws IOException {
        if(key.contains(":"))
        {
            key = key.split("\\Q:\\E")[1];
        }

        return load(new File(getLoadFolder(), key));
    }

    File getLoadFolder();

    String getTypeDisplayName();

    default String getTypeName()
    {
        return getTypeDisplayName().toLowerCase(Locale.ROOT).trim().replaceAll("\\Q \\E", "-");
    }

    boolean isText();
    default boolean isJson()
    {
        return false;
    }
}
