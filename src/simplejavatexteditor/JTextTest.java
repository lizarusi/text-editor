package simplejavatexteditor;

import rtf.AdvancedRTFEditorKit;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;

public class JTextTest extends JTextPane implements DropTargetListener {
    private static final DataFlavor FILE_FLAVOR = DataFlavor.javaFileListFlavor;
    private static final long serialVersionUID = 1L;
    public UndoManager undo UndoManager();
    public JTextTest() {
        new DropTarget(this, this);
        this.setDragEnabled(true);
        setEditorKit(new AdvancedRTFEditorKit());
        getDocument().addUndoableEditListener(undoManager);


    }

    @Override
    public void dragEnter(DropTargetDragEvent arg0) {
    }

    @Override
    public void dragExit(DropTargetEvent arg0) {
    }

    @Override
    public void dragOver(DropTargetDragEvent arg0) {
    }

    public boolean canImport( DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (FILE_FLAVOR.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }
    private boolean transferFlavor(DataFlavor[] flavors, DataFlavor flavor) {
        boolean found = false;
        for (int i = 0; i < flavors.length && !found; i++) {
            found = flavors[i].equals(flavor);
        }
        return found;
    }
    @Override
    public void drop(DropTargetDropEvent dropTargetDropEvent) {
        Transferable t = dropTargetDropEvent.getTransferable();
        if (canImport(t.getTransferDataFlavors())) {
            if (transferFlavor(t.getTransferDataFlavors(), FILE_FLAVOR)) {
                try {
                    dropTargetDropEvent.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    java.util.List<File> fileList = (java.util.List<File>) t.getTransferData(FILE_FLAVOR);
                    if (fileList.size() == 1) {
                        ImageIcon icon = new ImageIcon(fileList.get(0).getAbsolutePath());
                        this.insertIcon(icon);
                    }
//                    if (fileList != null && fileList.toArray() instanceof File[]) {
//                        File[] files = (File[]) fileList.toArray();
//
//
//                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnsupportedFlavorException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
    @Override
    public void dropActionChanged(DropTargetDragEvent arg0) {
    }
}