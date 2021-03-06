package mplanet.explorer;

import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;
import javax.swing.*;
import mplanet.sounds.*;

public class Explorer extends javax.swing.JFrame {
    
    /** This is mother of all codes*/
    public Explorer(String i,int ep,int sp,boolean sound) {
		isSoundOn = sound;
        searchPort = sp;
        explorerPort = ep;
        ip = i;
        initComponents();
        myInit();
        show();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        remoteFileSystem = new javax.swing.JPanel();
        localFileSystem = new javax.swing.JPanel();
        copyProgressDialog = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        progress = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        rightPanel = new javax.swing.JPanel();
        localFileSystemPane = new javax.swing.JScrollPane();
        localToolBar = new javax.swing.JToolBar();
        lUp = new javax.swing.JButton();
        lPaste = new javax.swing.JButton();
        localAddBar = new javax.swing.JTextField();
        jSplitPane2 = new javax.swing.JSplitPane();
        searchPanel = new javax.swing.JPanel();
        searchField = new javax.swing.JTextField();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        hideButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        resultsLabel = new javax.swing.JLabel();
        leftPanel = new javax.swing.JPanel();
        remoteAddBar = new javax.swing.JTextField();
        remoteFileSystemPane = new javax.swing.JScrollPane();
        remoteToolBar = new javax.swing.JToolBar();
        rUp = new javax.swing.JButton();
        rCopy = new javax.swing.JButton();
        rSearch = new javax.swing.JButton();

        remoteFileSystem.setLayout(new java.awt.GridLayout(10, 2));

        remoteFileSystem.setAutoscrolls(true);
        localFileSystem.setLayout(new java.awt.GridLayout(10, 2));

        localFileSystem.setAutoscrolls(true);
        copyProgressDialog.getContentPane().setLayout(new java.awt.GridLayout(3, 1));

        copyProgressDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        copyProgressDialog.setModal(true);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Copying...");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        copyProgressDialog.getContentPane().add(jLabel1);

        copyProgressDialog.getContentPane().add(progress);

        jButton1.setText("Cancel");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteTheCopiedFile(evt);
            }
        });

        copyProgressDialog.getContentPane().add(jButton1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("MPlaNet - Explorer");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setOneTouchExpandable(true);
        rightPanel.setLayout(new java.awt.BorderLayout());

        rightPanel.add(localFileSystemPane, java.awt.BorderLayout.CENTER);

        lUp.setText("up");
        lUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lUpClicked(evt);
            }
        });

        localToolBar.add(lUp);

        lPaste.setText("Paste");
        lPaste.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lPasteClicked(evt);
            }
        });

        localToolBar.add(lPaste);

        rightPanel.add(localToolBar, java.awt.BorderLayout.NORTH);

        localAddBar.setEditable(false);
        rightPanel.add(localAddBar, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(rightPanel);

        jSplitPane2.setDividerLocation(0);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setContinuousLayout(true);
        jSplitPane2.setOneTouchExpandable(true);
        searchPanel.setLayout(new java.awt.GridBagLayout());

        searchField.setColumns(25);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        searchPanel.add(searchField, gridBagConstraints);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startSearch(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        searchPanel.add(startButton, gridBagConstraints);

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        searchPanel.add(stopButton, gridBagConstraints);

        hideButton.setText("Hide");
        hideButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        searchPanel.add(hideButton, gridBagConstraints);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File Name", "Path"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        searchPanel.add(jScrollPane1, gridBagConstraints);

        resultsLabel.setText("Search Results");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        searchPanel.add(resultsLabel, gridBagConstraints);

        jSplitPane2.setLeftComponent(searchPanel);

        leftPanel.setLayout(new java.awt.BorderLayout());

        remoteAddBar.setEditable(false);
        leftPanel.add(remoteAddBar, java.awt.BorderLayout.SOUTH);

        leftPanel.add(remoteFileSystemPane, java.awt.BorderLayout.CENTER);

        rUp.setText("up");
        rUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rUpClicked(evt);
            }
        });

        remoteToolBar.add(rUp);

        rCopy.setText("copy");
        rCopy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rCopyClicked(evt);
            }
        });

        remoteToolBar.add(rCopy);

        rSearch.setText("Search");
        rSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSearchActionPerformed(evt);
            }
        });

        remoteToolBar.add(rSearch);

        leftPanel.add(remoteToolBar, java.awt.BorderLayout.NORTH);

        jSplitPane2.setRightComponent(leftPanel);

        jSplitPane1.setLeftComponent(jSplitPane2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents
/*
    private void lPasteClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lPasteClicked
        // Add your handling code here:
    }//GEN-LAST:event_lPasteClicked
 */   
    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        searchThread.runThread = false;
        try{
            searchThread.s.close();
            //searchThread.t.destroy();
        }catch(Exception e){}
    }//GEN-LAST:event_stopButtonActionPerformed
    
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        
    }//GEN-LAST:event_startButtonActionPerformed
    
    private void hideButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideButtonActionPerformed
        jSplitPane2.setDividerLocation(0.0);
    }//GEN-LAST:event_hideButtonActionPerformed
    
    private void rSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSearchActionPerformed
        jSplitPane2.setDividerLocation(0.25);
    }//GEN-LAST:event_rSearchActionPerformed
    
    private void startSearch(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startSearch
        String req = new String("F");
        try {
            os.writeObject(req);
            os.flush();
            System.out.println("Sent SEARCH req");
            searchThread = new SearchThread(ip,searchPort, searchField, jTable2, tableModel);
            searchThread.t.start();
        }catch(Exception e){System.out.println("  kud NOT Send SEARCH req");}
    }//GEN-LAST:event_startSearch
    
    
    private void myInit(){
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        copyProgressDialog.setBounds(100,100,270,170);        
        jSplitPane1.setDividerLocation((int)d.getWidth()/2);        
        setSize(d);
        
        localFileSystemPane.setViewportView(localFileSystem);
        remoteFileSystemPane.setViewportView(remoteFileSystem);
        //start the remote file system
        
        try {
            sk = new Socket(ip, explorerPort);
            System.out.println("created socket");
            os = new ObjectOutputStream(new BufferedOutputStream(sk.getOutputStream()));
            os.flush();
            is = new ObjectInputStream(new BufferedInputStream(sk.getInputStream()));
            System.out.println("created streams");
        } catch (Exception e) {
            System.out.println("NO sock in Explorer");
        }
        
        listTheRemoteFiles();
        refreshRemote();
        try{
            
        }catch(Exception e){}
        //remoteAddBar.setText();
        
        //start the local file system
        listTheLocalFiles();
        refreshLocal();
        localAddBar.setText(localCurDir.getAbsolutePath());
    }
    
    private void deleteTheCopiedFile(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteTheCopiedFile
        
    }//GEN-LAST:event_deleteTheCopiedFile
    
    private void rCopyClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rCopyClicked
        if(selRemItem instanceof DirIcon)
            System.out.println("Cannot copy dirs");
        else{
            copiedItem = selRemItem;
            System.out.println("Copied"+((FileIcon)copiedItem).name.toString());
            
        }
    }//GEN-LAST:event_rCopyClicked
    
    
    //r ->remote
    //l ->local
    
    
    private void rUpClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rUpClicked
        //P stands for parent
        String req = new String("P");
        try {
            os.writeObject(req);
            os.flush();
        } catch (Exception e) {
        }
        listTheRemoteFiles();
        refreshRemote();
    }//GEN-LAST:event_rUpClicked
    
    private void lPasteClicked(java.awt.event.MouseEvent evt) {
        String req = new String("C");
        if(copiedItem == null){
			if(isSoundOn){
				new PlaySound("mplanet/sounds/selectafile.mp3");
		    }
            new MPError("Please select a file");
            return;
        }
        try{
            os.writeObject(req);
            os.flush();
            os.writeObject(((FileIcon)copiedItem).name);
            os.flush();
        }catch(Exception e){
        }
        new PasteThread().t.start();
        copyProgressDialog.setVisible(true);
        
    }
    
    private void lUpClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lUpClicked
        File temp = lfs.getParentDirectory(localCurDir);
        if(temp != null){
            localCurDir = temp;
        }
        listTheLocalFiles();
        refreshLocal();
    }//GEN-LAST:event_lUpClicked
    
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
		if(isSoundOn){
			new PlaySound("mplanet/sounds/usemainwindow.mp3");
	    }
        new MPError("Please use the stop button on the main MPlaNet window");
    }//GEN-LAST:event_exitForm
    
    public void listTheRemoteFiles() {
        try {
            System.out.println("Trying to read");
            remoteAddBar.setText("Loading...");
            remoteFileSystem.removeAll();
            remoteFileSystem.repaint();            
            remoteDirs = null;
            remoteDirs = (Vector) is.readObject();
            remoteFiles = null;
            remoteFiles = (Vector) is.readObject();
            /*
            for(int i=0;i<remoteFiles.size();i++){
                System.out.println(((RemoteFile)remoteFiles.elementAt(i)).file.toString());
            }*/
            
        } catch (Exception e) {
            System.out.println("but cant read");
        }
    }
    
    private void refreshRemote() {
        System.out.println("Refreshing remote");
        for (int i = 0; i < remoteDirs.size(); i++) {
            remoteFileSystem.add(new DirIcon((RemoteFile) remoteDirs.elementAt(i)));
            //System.out.println(((RemoteFile)remoteDirs.elementAt(i)).file.toString());
        }
        for (int i = 0; i < remoteFiles.size(); i++) {
            remoteFileSystem.add(new FileIcon((RemoteFile) remoteFiles.elementAt(i)));
            //            System.out.println(((RemoteFile)remoteDirs.elementAt(i)).file.toString());
        }
        String path = null;
        
        try{
            path = ((RemoteFile)remoteDirs.elementAt(0)).absolutePath;
        }catch(Exception e){}
        
        if(path==null){
            try{
                path = ((RemoteFile)remoteFiles.elementAt(0)).absolutePath;
            }catch(Exception e){}
        }
        remoteFileSystem.revalidate();
        remoteFileSystem.repaint();
        try{
            remoteAddBar.setText(path);
        }catch(Exception e){
            remoteAddBar.setText("Empty directory");
        }
    }
    
    private void listTheLocalFiles(){
        System.out.println(localCurDir.toString());
        localFileSystem.removeAll();
        localFileSystem.repaint();
        localAddBar.setText("Loading...");
        localDirs.removeAllElements();
        localFiles.removeAllElements();
        File[] all = localCurDir.listFiles();
        for(int i=0;i<all.length;i++){
            if(all[i].isDirectory()){
                localDirs.add(all[i]);
            }
            else{
                localFiles.add(all[i]);
            }
        }
    }
    
    private void refreshLocal(){
        localFileSystem.removeAll();
        for(int i=0;i<localDirs.size();i++){
            localFileSystem.add(new DirIcon((File)localDirs.elementAt(i)));
        }
        for(int i=0;i<localFiles.size();i++){
            localFileSystem.add(new FileIcon((File)localFiles.elementAt(i)));
        }
        localFileSystem.revalidate();
        localFileSystem.repaint();
        localAddBar.setText(localCurDir.getAbsolutePath());
    }
    
    
    public static void main(String args[]) {
        new Explorer("127.0.0.1", 56555, 57555,false).show();
    }
    
    // class represents the directory icons on the explorer
    class DirIcon extends JLabel{
        public File name = null;
        
        public DirIcon(RemoteFile d){
            name = d.file;
            //setBackground(new java.awt.Color(255, 255, 255));
            setFont(new java.awt.Font("Arial", 0, 12));
            setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/dir.gif")));
            String n = d.sysName;
            setToolTipText(n);
            if(n.length() > 10)
                setText(n.substring(0,7)+"...");
            else
                setText(n);
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if(evt.getClickCount() == 1){
                        selectTheIcon(evt);
                    }
                    if(evt.getClickCount() > 1){
                        //System.out.println(getParent().toString()+" "localFileSystem.toString());
                        if(getParent().equals(localFileSystem))
                            localDirIconClicked(name);
                        else
                            remoteDirIconClicked(name);
                    }
                }
            });
        }
        
        public DirIcon(File d){
            name = d;
            //setBackground(new java.awt.Color(255, 255, 255));
            setFont(new java.awt.Font("Arial", 0, 12));
            setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/dir.gif")));
            String n = lfs.getSystemDisplayName(name);
            setToolTipText(n);
            if(n.length() > 10)
                setText(n.substring(0,7)+"...");
            else
                setText(n);
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if(evt.getClickCount() == 1){
                        selectTheIcon(evt);
                    }
                    if(evt.getClickCount() > 1){
                        //System.out.println(getParent().toString()+" "localFileSystem.toString());
                        if(getParent().equals(localFileSystem))
                            localDirIconClicked(name);
                        else
                            remoteDirIconClicked(name);
                    }
                }
            });
        }
    }
    
    // class represents the file icons on the explorer
    class FileIcon extends JLabel{
        public File name = null;
        

        public FileIcon(RemoteFile f){
            name = f.file;
            final File tempFile = f.file;           
            //setBackground(new java.awt.Color(255, 255, 255));
            setFont(new java.awt.Font("Arial", 0, 12));
            setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/file.gif")));
            String n = f.sysName;
            setToolTipText(n);
            if(n.length() > 10)
                setText(n.substring(0,7)+"...");
            else
                setText(n);
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if(evt.getClickCount() == 1){
                        selectTheIcon(evt);
                    }
					else if(evt.getClickCount() >1){
                        selectTheIcon(evt);
						new MPlanetQuickView(tempFile, os, is).show();
                    }

                }
            });
        }
        
        public FileIcon(File f){
            name = f;
            final File tempFile = f;
            //setBackground(new java.awt.Color(255, 255, 255));
            setFont(new java.awt.Font("Arial", 0, 12));
            setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/file.gif")));
            String n = lfs.getSystemDisplayName(name);
            setToolTipText(n);
            if(n.length() > 10)
                setText(n.substring(0,7)+"...");
            else
                setText(n);
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if(evt.getClickCount() == 1){
                        selectTheIcon(evt);
                    }
                    else if(evt.getClickCount() > 1){
						selectTheIcon(evt);
                        new MPlanetQuickView(tempFile.toString()).show();
                    }
                }
            });
        }
    }
    
    public void localDirIconClicked(File c){
        localCurDir = c;
        listTheLocalFiles();
        refreshLocal();
    }
    
    public void remoteDirIconClicked(File c) {
        String req = new String("D");
        try {
            os.writeObject(req);
            os.flush();
            os.writeObject(c);
            os.flush();
        } catch (Exception e) {
        }
        listTheRemoteFiles();
        refreshRemote();
    }
    
    
    private void selectTheIcon(java.awt.event.MouseEvent evt){
        if(evt.getClickCount() == 1){
            // if the sel item is in the local file system
            if(evt.getComponent().getParent().equals(localFileSystem)){
                // check if no items selected and if selected, deselect it
                if(selLocItem != null){
                    // check the type of selected icon
                    if(selLocItem instanceof FileIcon){
                        selLocItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/file.gif")));
                    }
                    else{ // (selLocItem instanceof DirIcon)
                        selLocItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/dir.gif")));
                    }
                }
                // set the icon of the selected item
                if(evt.getComponent() instanceof FileIcon){
                    ((JLabel)evt.getComponent()).setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/sfile.gif")));
                }
                else{// evt.getComponent() instanceof DirIcon
                    ((JLabel)evt.getComponent()).setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/sdir.gif")));
                }
                selLocItem = (JLabel)evt.getComponent();
            }
            //  if the sel item is in the remote file system
            else{
                // check if no items selected and if selected, deselect it
                if(selRemItem != null){
                    // check the type of selected icon
                    if(selRemItem instanceof FileIcon){
                        selRemItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/file.gif")));
                    }
                    else{ // (selLocItem instanceof DirIcon)
                        selRemItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/dir.gif")));
                    }
                    
                }
                // set the icon of the selected item
                if(evt.getComponent() instanceof FileIcon){
                    ((JLabel)evt.getComponent()).setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/sfile.gif")));
                }
                else{// evt.getComponent() instanceof DirIcon
                    ((JLabel)evt.getComponent()).setIcon(new javax.swing.ImageIcon(getClass().getResource("/mplanet/images/sdir.gif")));
                }
                selRemItem = (JLabel)evt.getComponent();
            }
        }
    }
    
    
    
    public void setTheRow(String name,String path){
        Object arr[] = {name,path};
        tableModel.addRow(arr);
    }
    
    
    private static void runGC() throws Exception {
        for (int i = 0; i < 4; i++)
            _runGC();
    }
    
    private static void _runGC() throws Exception {
        for (int i = 0; i < 5; i++) {
            rTime.runFinalization();
            rTime.gc();
        }
    }
    
    
    
    public SearchThread searchThread = null;
    private JLabel selRemItem = null;
    private JLabel selLocItem = null;
    private JLabel copiedItem = null;
    
    private Vector remoteFiles = new Vector();
    private Vector remoteDirs = new Vector();
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    
    private FileSystemView lfs = FileSystemView.getFileSystemView();
    private File localCurDir = new File(System.getProperty("user.dir"));
    private Vector localFiles = new Vector();
    private Vector localDirs = new Vector();
    

	public boolean isSoundOn = false;	   

    private final int maxSize = 1024*1024;
    static private final Runtime rTime = Runtime.getRuntime();
    
    private static String ip = "127.0.0.1";
    int explorerPort = 0;
    int searchPort = 0;
    public Socket sk = null;
    
    public DefaultTableModel tableModel = new DefaultTableModel();
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel leftPanel;
    private javax.swing.JTextField localAddBar;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JButton rSearch;
    private javax.swing.JScrollPane remoteFileSystemPane;
    private javax.swing.JToolBar remoteToolBar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton startButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchField;
    private javax.swing.JPanel remoteFileSystem;
    private javax.swing.JToolBar localToolBar;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton rUp;
    private javax.swing.JProgressBar progress;
    private javax.swing.JDialog copyProgressDialog;
    private javax.swing.JButton lUp;
    private javax.swing.JLabel resultsLabel;
    private javax.swing.JScrollPane localFileSystemPane;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField remoteAddBar;
    private javax.swing.JPanel localFileSystem;
    private javax.swing.JButton hideButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JButton rCopy;
    private javax.swing.JButton lPaste;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    
    class PasteThread implements Runnable{
        Thread t = null;
        PasteThread(){
            t = new Thread(this,"PasteThread");
        }
        public void run(){
            try {
                String name = ((FileIcon)copiedItem).name.getName();
                File f = new File(localCurDir,name);
                BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(f), maxSize);
                int bytesRead = -1;
                byte [] arr = new byte[maxSize];
                int gcCount = 0;
                long fileSize = is.readLong();
                System.out.println((long)maxSize);
                long progressValue = 0;
                long incValue = 0;
                System.out.println(incValue);
                while(true){
                    progress.setValue((int)progressValue);
                    System.out.println((int)progressValue);
                    is.readFully(arr);
                    bytesRead = is.readInt();
                    System.out.println(bytesRead);
                    if(bytesRead != -1){
                        incValue+=maxSize;
                        fos.write(arr,0,bytesRead);
                        progressValue = incValue * 100 / fileSize;
                    }
                    else{
                        System.out.println("Breaking");
                        break;
                    }
                    if(gcCount == 20){
                        runGC();
                        gcCount = 0;
                    }
                }
                fos.close();
                copyProgressDialog.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
}




