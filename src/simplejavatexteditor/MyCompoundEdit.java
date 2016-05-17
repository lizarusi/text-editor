package simplejavatexteditor;

import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;

/**
 * Created by lizarusi on 15.05.16.
 */
public class MyCompoundEdit extends CompoundEdit {
    boolean isUnDone=false;
    public int getLength() {
        return edits.size();
    }

    public void undo() throws CannotUndoException {
        super.undo();
        isUnDone=true;
    }
    public void redo() throws CannotUndoException {
        super.redo();
        isUnDone=false;
    }
    public boolean canUndo() {
        return edits.size()>0 && !isUnDone;
    }

    public boolean canRedo() {
        return edits.size()>0 && isUnDone;
    }
}
