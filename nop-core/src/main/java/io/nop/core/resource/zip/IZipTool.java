/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.core.resource.zip;

import io.nop.api.core.exceptions.NopException;
import io.nop.commons.util.IoHelper;
import io.nop.core.resource.IFile;
import io.nop.core.resource.IResource;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;

import static io.nop.core.CoreErrors.ARG_DIR;
import static io.nop.core.CoreErrors.ARG_TARGET_FILE;
import static io.nop.core.CoreErrors.ERR_RESOURCE_UNZIP_TO_DIR_FAIL;
import static io.nop.core.CoreErrors.ERR_RESOURCE_ZIP_DIR_TO_FILE_FAIL;

public interface IZipTool {
    IZipOutput newZipOutput(OutputStream os, ZipOptions options);

    default IZipOutput getZipOutput(IResource resource, ZipOptions options) {
        OutputStream os = resource.getOutputStream();
        try {
            return newZipOutput(os, options);
        } catch (Exception e) {
            IoHelper.safeClose(os);
            throw NopException.adapt(e);
        }
    }

    IZipInput newZipInput(InputStream is, ZipOptions options);

    default IZipInput getZipInput(IResource resource, ZipOptions options) {
        InputStream is = resource.getInputStream();
        try {
            return newZipInput(is, options);
        } catch (Exception e) {
            IoHelper.safeClose(is);
            throw NopException.adapt(e);
        }
    }

    default void unzipToDir(IResource zipFile, IFile dir) {
        unzipToDir(zipFile, dir, null, null);
    }

    default void zipDirTo(IFile dir, IResource zipFile) {
        zipDirTo(dir, zipFile, null, null);
    }

    default void unzipToDir(IResource zipFile, IFile dir, ZipOptions options, Predicate<ZipEntry> filter) {
        IZipInput in = getZipInput(zipFile, options);
        try {
            in.unzipToDir(dir, filter);
        } catch (Exception e) {
            throw new NopException(ERR_RESOURCE_UNZIP_TO_DIR_FAIL, e).param(ARG_DIR, dir);
        } finally {
            IoHelper.safeClose(in);
        }
    }

    default void zipDirTo(IFile dir, IResource zipFile, ZipOptions options, Predicate<IFile> filter) {
        IZipOutput out = getZipOutput(zipFile, options);
        try {
            out.addDir("", dir, true, filter);
        } catch (Exception e) {
            throw new NopException(ERR_RESOURCE_ZIP_DIR_TO_FILE_FAIL, e).param(ARG_TARGET_FILE, zipFile).param(ARG_DIR,
                    dir);
        } finally {
            IoHelper.safeClose(out);
        }
    }
}