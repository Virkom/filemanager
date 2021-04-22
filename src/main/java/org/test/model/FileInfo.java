package org.test.model;

import java.util.Objects;

/**
 * Object to store information about the uploaded file
 */
public class FileInfo {
    private final String hash;
    private final String ref;
    private final String name;
    private final long size;

    public FileInfo(String hash, String ref, String name, long size) {
        this.hash = hash;
        this.ref = ref;
        this.name = name;
        this.size = size;
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getRef() {
        return ref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileInfo fileInfo = (FileInfo) o;
        return size == fileInfo.size &&
                Objects.equals(hash, fileInfo.hash) &&
                Objects.equals(ref, fileInfo.ref) &&
                Objects.equals(name, fileInfo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, ref, name, size);
    }
}
