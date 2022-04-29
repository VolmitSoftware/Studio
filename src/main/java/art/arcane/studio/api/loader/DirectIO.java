package art.arcane.studio.api.loader;

import java.io.File;

public class DirectIO<T> implements StudioIO<T> {
    private final String extension;
    private final StudioReader<T> reader;
    private final File folder;
    private final String displayName;
    private final boolean textual;

    public DirectIO(StudioReader<T> reader, File folder, String extension, boolean textual, String displayName)
    {
        this.extension = extension;
        this.reader = reader;
        this.folder = folder;
        this.textual = textual;
        this.displayName = displayName;
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
