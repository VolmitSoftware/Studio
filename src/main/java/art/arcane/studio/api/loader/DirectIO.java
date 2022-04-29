package art.arcane.studio.api.loader;

import java.io.File;
import java.util.Locale;

public class DirectIO<T> implements StudioIO<T> {
    private final String extension;
    private final StudioReader<T> reader;
    private final File folder;
    private final String displayName;
    private final boolean textual;
    private final Class<T> type;

    public DirectIO(StudioReader<T> reader, Class<T> type, File folder, String extension, boolean textual, String displayName)
    {
        this.type = type;
        this.extension = extension;
        this.reader = reader;
        this.folder = new File(folder, displayName
                .toLowerCase(Locale.ROOT).trim().replaceAll("\\Q \\E", "-"));
        this.textual = textual;
        this.displayName = displayName;
        folder.mkdirs();
    }

    @Override
    public Class<?> getLoadedClass() {
        return type;
    }

    @Override
    public void dump() {

    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public StudioReader<T> getReader() {
        return reader;
    }

    @Override
    public File getLoadFolder() {
        return folder;
    }

    @Override
    public String getTypeDisplayName() {
        return displayName;
    }

    @Override
    public boolean isText() {
        return textual;
    }
}
