/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package player;
import java.io.File;
import java.io.*;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import javax.swing.UIManager;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import javazoom.jl.player.Player;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFileChooser;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;





/**
 *  22050974048_Nova Septian Alfado
 *  22050974054_Asataurani Samara
 */
public class desain_aplikasi extends javax.swing.JFrame {
    private Player player;
    private boolean isPaused;
    private Clip wavClip;
    private Player wavPlayer;
    private Player flacPlayer;

    
    Playlist pl = new Playlist();
    
    ArrayList updateList = new ArrayList();
    

    File simpan;
    private InputStream fis;

    /**
     * Creates new form desain_aplikasi
     */
    public desain_aplikasi() {
        initComponents();
    }
void updateList() {
    updateList = pl.getListSong();
    DefaultListModel model =  new DefaultListModel();
    for (int i = 0; i < updateList.size(); i++) {
        int j = i + 1;
        model.add(i, j + " | " + ((File) updateList.get(i)).getName());
    }
    jPlaylist.setModel(model);
}

//panel kontrol

void addfldr() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int result = chooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFolder = chooser.getSelectedFile();
        addFolder(selectedFolder); // Memanggil fungsi addFolder dengan folder yang dipilih
    }
}

void addFolder(File folder) {
    if (folder.isDirectory()) {
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".mp3") || name.endsWith(".wav");
            }
        });

        for (File file : files) {
            if (file.isDirectory()) {
                addFolder(file); // Rekursif untuk folder dalam folder
            } else {
                pl.add(file);
            }
        }
        updateList(); // Memanggil fungsi updateList() setelah menambahkan lagu dari folder
    }
}

void add(){
    pl.add(this);
    updateList();
    
}


void remove(){
    try{
        int akandihapus = jPlaylist.getLeadSelectionIndex();
        pl.ls.remove(akandihapus);
        updateList();
    }catch(Exception e){
    }
}





void playWav(File file) {
    try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace();
    }
}







File play1;
static int a = 0;

void play() {
    if (a == 0) {
        try {
            int p1 = jPlaylist.getSelectedIndex();
            play1 = (File) this.updateList.get(p1);
            String fileName = play1.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (extension.equalsIgnoreCase("mp3")) {
                FileInputStream fis = new FileInputStream(play1);
                BufferedInputStream bis = new BufferedInputStream(fis);
                player = new javazoom.jl.player.Player(bis);
            } else if (extension.equalsIgnoreCase("wav")) {
                playWav(play1);
            }
            a = 1;
        } catch (Exception e) {
            System.out.println("Problem playing file");
            System.out.println(e);
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    if (player != null) {
                        player.play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    } else {
        if (player != null) {
            player.close();
        }
                a = 0;
        play();
    }
}


File sa;
void next() {
    if (a == 0) {
        try {
            int s1 = jPlaylist.getSelectedIndex() + 1;
            sa = (File) this.pl.ls.get(s1);
            String fileName = sa.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (extension.equalsIgnoreCase("mp3")) {
                if (player != null) {
                    player.close();
                }
            } else if (extension.equalsIgnoreCase("wav")) {
                playWav(sa); 
            }
            a = 1;
            jPlaylist.setSelectedIndex(s1);
        } catch (Exception e) {
            System.out.println("Problem playing file");
            System.out.println(e);
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    if (player != null) {
                        player.play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    } else {
        if (player != null) {
            player.close();
        }
        a = 0;
        next();
    }
}


void previous() {
    if (a == 0) {
        try {
            int s1 = jPlaylist.getSelectedIndex() + 1;
            sa = (File) this.pl.ls.get(s1);
            String fileName = sa.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (extension.equalsIgnoreCase("mp3")) {
                if (player != null) {
                    player.close();
                }
            } else if (extension.equalsIgnoreCase("wav")) {
                playWav(sa); 
            }
            a = 1;
            jPlaylist.setSelectedIndex(s1);
        } catch (Exception e) {
            System.out.println("Problem playing file");
            System.out.println(e);
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    if (player != null) {
                        player.play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    } else {
        if (player != null) {
            player.close();
        }
        a = 0;
        previous();
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPlaylist = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        addFolder = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnPlay = new javax.swing.JButton();
        btnPrevious = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(102, 204, 255));

        jPanel2.setBackground(new java.awt.Color(153, 255, 255));

        jPlaylist.setFont(new java.awt.Font("Bookman Old Style", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(jPlaylist);

        jPanel3.setBackground(new java.awt.Color(102, 102, 255));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        btnAdd.setBackground(new java.awt.Color(153, 255, 255));
        btnAdd.setFont(new java.awt.Font("Rockwell Condensed", 1, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add (1).png"))); // NOI18N
        btnAdd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnAddStateChanged(evt);
            }
        });
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Swis721 BlkCn BT", 0, 18)); // NOI18N
        jLabel1.setText("Audio Player");

        addFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFolderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(196, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(addFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(addFolder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6))
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 255));

        btnPlay.setBackground(new java.awt.Color(102, 102, 255));
        btnPlay.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnPlay.setForeground(new java.awt.Color(51, 51, 255));
        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/play (1) (1) (1) (1).png"))); // NOI18N
        btnPlay.setBorder(null);
        btnPlay.setBorderPainted(false);
        btnPlay.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPlay.setMaximumSize(new java.awt.Dimension(56, 56));
        btnPlay.setMinimumSize(new java.awt.Dimension(56, 56));
        btnPlay.setPreferredSize(new java.awt.Dimension(56, 56));
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        btnPrevious.setBackground(new java.awt.Color(102, 102, 255));
        btnPrevious.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/previous (2).png"))); // NOI18N
        btnPrevious.setBorder(null);
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnStop.setBackground(new java.awt.Color(102, 102, 255));
        btnStop.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/stop-button (1).png"))); // NOI18N
        btnStop.setBorder(null);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(102, 102, 255));
        btnNext.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/next-button (2).png"))); // NOI18N
        btnNext.setBorder(null);
        btnNext.setBorderPainted(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnRemove.setBackground(new java.awt.Color(102, 102, 255));
        btnRemove.setFont(new java.awt.Font("Rockwell Condensed", 1, 12)); // NOI18N
        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/trash (1).png"))); // NOI18N
        btnRemove.setBorder(null);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRemove))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnStop)
                                    .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(4, 4, 4)))
                .addGap(4, 4, 4))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logoo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        add();;// TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAddStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnAddStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddStateChanged

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();// TODO add your handling code here:
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        player.close();// TODO add your handling code here:
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        play();// TODO add your handling code here:
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        remove();// TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        previous();// TODO add your handling code here:
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void addFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFolderActionPerformed
        addfldr();// TODO add your handling code here:
    }//GEN-LAST:event_addFolderActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(desain_aplikasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(desain_aplikasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(desain_aplikasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(desain_aplikasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new desain_aplikasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFolder;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnStop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JList<String> jPlaylist;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

   
}
