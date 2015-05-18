/*
 * Copyright (C) 2004 - 2013 by Marcel Schoen and Andre Bossert
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.johnny.yin.cygwinplugin.model;

import java.io.File;

public class Resource {

    private File file = null;
    private String projectName = null;

    public Resource(Resource myRes) {
        file = myRes.getFile();
        projectName = myRes.getProjectName();
    }

    public Resource(File myFile, String myProjectName) {
        file = myFile;
        projectName = myProjectName;
    }

    public File getFile() {
        return file;
    }

    public String getProjectName() {
        return projectName;
    }

}
