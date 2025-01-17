package io.nop.tool.refactor;

import io.nop.commons.util.StringHelper;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FileExtFilter implements Predicate<File> {
    private final List<String> fileExts;

    public FileExtFilter(List<String> fileExts) {
        this.fileExts = fileExts;
    }

    public static FileExtFilter forFileExt(String... fileExts) {
        return new FileExtFilter(Arrays.asList(fileExts));
    }

    @Override
    public boolean test(File file) {
        String fileExt = StringHelper.fileExt(file.getName());
        return fileExts.contains(fileExt);
    }
}
