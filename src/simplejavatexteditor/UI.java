/**
 * @name        Simple Java text editor
 * @package     ph.notepad
 * @file        UI.java *
 * @author      Liza Ruskykh
 */

package simplejavatexteditor;

import rtf.reader.RTFReader;
import rtf.writer.RTFWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class UI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final Container container;
	private final JTextPaneDrop textArea;
	private StyledDocument doc;
	private Action undoAction;
	private final JScrollPane pane;
	private final JMenuBar menuBar;
	private final JMenu menuFile, menuEdit;
	private final JMenuItem newFile, openFile, saveFile, close, cut, copy, paste, clearFile,
			 undo, redo, selectAll;
	private final JToolBar mainToolbar;
	JButton newButton, btnRedo, btnUndo, openButton,insertPictureButton, saveButton, clearButton, quickButton, closeButton,
			spaceButton1, spaceButton2;
	private final Action selectAllAction;

	// setup icons - File Menu
	private final ImageIcon newIcon = new ImageIcon("icons/new.png");
	private final ImageIcon insertIcon = new ImageIcon("icons/insert_image.png");
	private final ImageIcon openIcon = new ImageIcon("icons/open.png");
	private final ImageIcon saveIcon = new ImageIcon("icons/save.png");
	private final ImageIcon redoIcon = new ImageIcon("icons/redo-icon.png");
	private final ImageIcon undoIcon = new ImageIcon("icons/undo-icon.png");
	private final ImageIcon closeIcon = new ImageIcon("icons/close.png");

	// setup icons - Edit Menu
	private final ImageIcon clearIcon = new ImageIcon("icons/clear.png");
	private final ImageIcon cutIcon = new ImageIcon("icons/cut.png");
	private final ImageIcon copyIcon = new ImageIcon("icons/copy.png");
	private final ImageIcon pasteIcon = new ImageIcon("icons/paste.png");
	private final ImageIcon selectAllIcon = new ImageIcon("icons/selectall.png");

	// setup icons - Search Menu
	private final ImageIcon searchIcon = new ImageIcon("icons/search.png");

	// setup icons - Help Menu
	private boolean hasListener = false;

	public UI() {
		container = getContentPane();

		// Set the initial size of the window
		setSize(700, 500);

		// Set the title of the window
		setTitle("Untitled | " + SimpleJavaTextEditor.NAME);

		// Set the default close operation (exit when it gets closed)
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Set a default font for the TextArea
		textArea = new JTextPaneDrop();
		textArea.setDragEnabled(true);
		pane = new JScrollPane(textArea);
		StyledDocument doc = textArea.getStyledDocument();
		textArea.setFont(new Font("Century Gothic", Font.BOLD, 12));
		textArea.setFont(new Font("Century Gothic", Font.BOLD, 12));
		// This is why we didn't have to worry about the size of the TextArea!
		getContentPane().setLayout(new BorderLayout()); // the BorderLayout bit makes it fill it automatically
		getContentPane().add(pane);
		// Set the Menus
		menuFile = new JMenu("File");
		menuEdit = new JMenu("Edit");

		// Set the Items Menu
		newFile = new JMenuItem("New", newIcon);
		undo = new JMenuItem("Undo", undoIcon);
		redo = new JMenuItem("Redo", redoIcon);
		openFile = new JMenuItem("Open", openIcon);
		saveFile = new JMenuItem("Save", saveIcon);
		close = new JMenuItem("Quit", closeIcon);
		clearFile = new JMenuItem("Clear", clearIcon);

		// Set the Menu Bar into the our GUI
		menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuEdit);

		this.setJMenuBar(menuBar);

		// Set Actions:
		selectAllAction = new SelectAllAction("Select All", clearIcon, "Select all text", new Integer(KeyEvent.VK_A),
				textArea);

        this.setJMenuBar(menuBar);

		// New File
		newFile.addActionListener(this);  // Adding an action listener (so we know when it's been clicked).
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK)); // Set a keyboard shortcut
		menuFile.add(newFile); // Adding the file menu

		// Open File
		openFile.addActionListener(this);
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		menuFile.add(openFile);



		// Save File
		saveFile.addActionListener(this);
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuFile.add(saveFile);

		// Close File
		/*
		 * Along with our "CTRL+F4" shortcut to close the window, we also have
		 * the default closer, as stated at the beginning of this tutorial. this
		 * means that we actually have TWO shortcuts to close: 
		 * 1) the default close operation (example, Alt+F4 on Windows)
		 * 2) CTRL+F4, which we are
		 * about to define now: (this one will appear in the label).
		 */
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		close.addActionListener(this);
		menuFile.add(close);
		
		// Select All Text
		selectAll = new JMenuItem(selectAllAction);
		selectAll.setText("Select All");
		selectAll.setIcon(selectAllIcon);
		selectAll.setToolTipText("Select All");
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		menuEdit.add(selectAll);

		// Clear File (Code)
		clearFile.addActionListener(this);
		clearFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
		menuEdit.add(clearFile);

		//Undo
		undo.addActionListener(this);
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		menuEdit.add(undo);

		//Redo
		redo.addActionListener(this);
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		menuEdit.add(redo);

		// Cut Text
		cut = new JMenuItem(new DefaultEditorKit.CutAction());
		cut.setText("Cut");
		cut.setIcon(cutIcon);
		cut.setToolTipText("Cut");
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		menuEdit.add(cut);

		// Copy Text
		copy = new JMenuItem(new DefaultEditorKit.CopyAction());
		copy.setText("Copy");
		copy.setIcon(copyIcon);
		copy.setToolTipText("Copy");
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		menuEdit.add(copy);

		// Paste Text
		paste = new JMenuItem(new DefaultEditorKit.PasteAction());
		paste.setText("Paste");
		paste.setIcon(pasteIcon);
		paste.setToolTipText("Paste");
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		menuEdit.add(paste);
		mainToolbar = new JToolBar();
		this.add(mainToolbar, BorderLayout.NORTH);
		// used to create space between button groups
		Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 50);
		newButton = new CreateBtnIcon().factoryMethod(newIcon, "New", mainToolbar, this);
		openButton = new CreateBtnIcon().factoryMethod(openIcon, "Open", mainToolbar, this);
		insertPictureButton = new CreateBtnIcon().factoryMethod(insertIcon, "Insert", mainToolbar, this);
		//Undo
		btnUndo= new CreateBtnIcon().factoryMethod(undoIcon, "Undo", mainToolbar, this);
		btnRedo=new CreateBtnIcon().factoryMethod(redoIcon, "Redo", mainToolbar, this);
		saveButton = new CreateBtnIcon().factoryMethod(saveIcon, "Save", mainToolbar, this);
		clearButton = new CreateBtnIcon().factoryMethod(clearIcon, "Clear", mainToolbar, this);
		closeButton = new CreateBtnIcon().factoryMethod(closeIcon, "Quit", mainToolbar, this);
	}

	// Make the TextArea available to the autocomplete handler
	protected JTextPane getEditor() {
		return textArea;
	}

	public void actionPerformed (ActionEvent e) {
		// If the source of the event was our "close" option
		if (e.getSource() == close || e.getSource() == closeButton)
			this.dispose(); // dispose all resources and close the application

		// If the source was the "new" file option
		else if (e.getSource() == newFile || e.getSource() == newButton) {
			textArea.setText("");
		}
		// If the source was the "open" option
		else if (e.getSource() == openFile || e.getSource() == openButton) {
			JFileChooser open = new JFileChooser(); // open up a file chooser (a dialog for the user to  browse files to open)
			int option = open.showOpenDialog(this); // get the option that the user selected (approve or cancel)
			/*
			 * NOTE: because we are OPENing a file, we call showOpenDialog~ if
			 * the user clicked OK, we have "APPROVE_OPTION" so we want to open
			 * the file
			 */
			if (option == JFileChooser.APPROVE_OPTION) {
				textArea.setText("");
				Document doc = textArea.getDocument();
				RTFReader read = new RTFReader(doc);
				try {
					read.read(open.getSelectedFile().getAbsolutePath(), 0);
					setTitle(open.getSelectedFile().getName());
				}
				catch (BadLocationException ae){
					JOptionPane.showMessageDialog(null, "Cannot open file");

				}
				catch (IOException ae){
					JOptionPane.showMessageDialog(null, "Cannot open file");
				}
			}

		}
		else if (e.getSource() == insertPictureButton){
			JFileChooser fc = new JFileChooser();
			int result = fc.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				MyImage image = new PixelImageProxy(file);
				try {
					image.display(textArea);
				}
				catch (IOException ae){
					JOptionPane.showMessageDialog(null, "Cannot open the image");
				}
			}
		}
		else if (e.getSource() == undo || e.getSource() == btnUndo){
			textArea.undoManager.undo();
		}
		else if (e.getSource() == redo || e.getSource() == btnRedo){
			textArea.undoManager.redo();
		}
		// If the source of the event was the "save" option
		else if (e.getSource() == saveFile || e.getSource() == saveButton) {
			// Open a file chooser
			JFileChooser fileChoose = new JFileChooser();
			// Open the file, only this time we call
			int option = fileChoose.showSaveDialog(this);
			/*
			 * ShowSaveDialog instead of showOpenDialog if the user clicked OK
			 * (and not cancel)
			 */
			if (option == JFileChooser.APPROVE_OPTION) {
				StyledDocument doc = (StyledDocument) textArea.getDocument();
				RTFWriter kit = new RTFWriter(doc);
				try {
					kit.write(fileChoose.getSelectedFile().getAbsolutePath());
				} catch (IOException ae) {
					JOptionPane.showMessageDialog(null, "Cannot save file");
				}
			}

		}
		// Clear File (Code)
		if (e.getSource() == clearFile || e.getSource() == clearButton) {
			textArea.setText("");
		}

	}

	class SelectAllAction extends AbstractAction {
		/**
		 * Used for Select All function
		 */
		private static final long serialVersionUID = 1L;

		public SelectAllAction(String text, ImageIcon icon, String desc, Integer mnemonic, final JTextPaneDrop textArea) {
			super(text, icon);
			putValue(SHORT_DESCRIPTION, desc);
			putValue(MNEMONIC_KEY, mnemonic);
		}

		public void actionPerformed(ActionEvent e) {
			textArea.selectAll();
		}
	}

}
