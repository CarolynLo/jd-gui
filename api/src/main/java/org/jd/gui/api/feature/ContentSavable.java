/*
 * Copyright (c) 2008-2019 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package org.jd.gui.api.feature;

import org.jd.gui.api.API;

import java.io.OutputStream;

public interface ContentSavable {
    public String getFileName();

    public void save(API api, OutputStream os);
}
