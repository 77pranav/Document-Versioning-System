package ui_main;
import database.Mdb;
import database.VersionFileOperations;
import org.bson.Document;
import services.VersionServices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VersionSection extends JPanel{
    private Color bgColor;
    private Color eleBgColor;
    private Color hoverColor;
    private Color textColor;
    private Color sepColor;
    private JScrollPane scrollPane;
    private JPanel versionListPanel;
    private VersionServices versionServices;
    private TextSection textSection;
    private FileNameRibbon fileNameRibbon;
    private Object openedId;
    private VersionFileOperations versionFileOperations;
    public VersionSection(TextSection textSection, FileNameRibbon fileNameRibbon, Mdb mdb){
        bgColor=new Color(170, 179, 220);
        eleBgColor = new Color(64, 80, 158);
        hoverColor = new Color(136, 175, 194);
        textColor = Color.WHITE;
        sepColor = new Color(238, 238, 238);
        versionServices=new VersionServices(mdb);
        versionFileOperations=new VersionFileOperations(mdb);
        this.fileNameRibbon = fileNameRibbon;
        this.textSection=textSection;
        this.openedId=null;

        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(bgColor);
        setPreferredSize(new Dimension(350,0));

        add(showHeading(),BorderLayout.NORTH);

        versionListPanel=new JPanel();
        versionListPanel.setLayout(new BoxLayout(versionListPanel,BoxLayout.Y_AXIS));
        versionListPanel.setBackground(bgColor);

        scrollPane=new JScrollPane(versionListPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane,BorderLayout.CENTER);
    }
    public JPanel giveVersionListPanel(){
        return versionListPanel;
    }
    public JScrollPane giveScrollPane(){
        return scrollPane;
    }
    public JPanel showHeading(){
        JPanel heading=new JPanel();
        heading.setBackground(bgColor);
        heading.setOpaque(true);
        JLabel history=new JLabel("Version History");
        history.setFont(new Font("Arial",Font.BOLD,20));
        heading.setBorder(BorderFactory.createMatteBorder(0,0,5,0,sepColor));
        heading.add(history);
        return heading;
    }
    public JPanel showVersionDocument(Object id,String title,String createdAt){

        JPanel docPane=new JPanel();
        if(id.equals(openedId)){
            docPane.setBackground(bgColor.brighter());
        } else {
            docPane.setBackground(bgColor);
        }
        docPane.setLayout(new BoxLayout(docPane,BoxLayout.Y_AXIS));
        docPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        docPane.setPreferredSize(new Dimension(0, 100));
        docPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel docName=new JLabel(title+" "+createdAt);
        docName.setAlignmentX(Component.LEFT_ALIGNMENT);
        docName.setFont(new Font("Arial", Font.PLAIN, 15));

        JPanel buttonPane=new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.X_AXIS));
        if(id.equals(openedId)){
            buttonPane.setBackground(bgColor.brighter());
        } else {
            buttonPane.setBackground(bgColor);
        }

        buttonPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        buttonPane.setPreferredSize(new Dimension(0, 50));
        buttonPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton openDoc=getButtons("Open",e-> {
            versionServices.openVersionDoc(id, textSection);
            this.openedId=id;
            refreshVersionList(fileNameRibbon);
        });
        JButton downloadDoc=getButtons("Download",e->versionServices.downloadVersionDoc(id));
        JButton deleteDoc=getButtons("Delete",e-> versionServices.deleteVersionDoc(id,textSection,this,fileNameRibbon));

        buttonPane.add(openDoc);
        buttonPane.add(Box.createHorizontalStrut(20));
        buttonPane.add(downloadDoc);
        buttonPane.add(Box.createHorizontalStrut(20));
        buttonPane.add(deleteDoc);

        docPane.add(Box.createVerticalStrut(10));
        docPane.add(docName);
        docPane.add(Box.createVerticalStrut(10));
        docPane.add(buttonPane);

        return docPane;
    }
    public JButton getButtons(String text,ActionListener action){
        JButton btn=new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        btn.setAlignmentY(Component.CENTER_ALIGNMENT);
        btn.setOpaque(true);
        btn.setBackground(eleBgColor);
        btn.setForeground(textColor);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(eleBgColor);
            }
        });
        btn.addActionListener(action);
        return btn;
    }
    public void refreshVersionList(FileNameRibbon fileNameRibbon) {

        JPanel panel = giveVersionListPanel();

        panel.removeAll();
        ArrayList<Document> versionDocs=versionFileOperations.getAllVersionFiles(fileNameRibbon.getFileName());
        for(int i = 0; i < versionDocs.size(); i++){
            Object Id = versionDocs.get(i).getObjectId("_id");
            String title = versionDocs.get(i).getString("Title");
            String createdAt = versionDocs.get(i).getString("Created At");

            panel.add(showVersionDocument(Id, title, createdAt));
        }

        panel.revalidate();
        panel.repaint();
    }
}
