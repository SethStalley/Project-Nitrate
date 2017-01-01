package view;

import java.io.File;

import javax.swing.filechooser.FileFilter;

class NitratoFileType extends FileFilter {

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) return true;
        if (file.getAbsolutePath().endsWith(".txt")) return true;
        return false;
    }

    @Override
    public String getDescription() {
        return "Documentos de texto (*.txt)";
    }
}