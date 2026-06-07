package ui_main;
import javax.swing.*;
import java.awt.*;
public class FileNameRibbon extends JPanel {
    private JLabel fileName;
    private Color ribbonColor;
    public FileNameRibbon(){
        ribbonColor=new Color(64, 80, 158);
        fileName=new JLabel("Untitled");
        setOpaque(true);
        setBackground(ribbonColor);
        fileName.setForeground(Color.WHITE);
        setPreferredSize(new Dimension(0,22));
        fileName.setFont(new Font("Arial",Font.PLAIN,14));
        setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(53, 102, 152)));
        add(fileName);
    }
    public void setFileName(String FileName){
        fileName.setText(FileName);
    }
    public String getFileName(){
        return fileName.getText();
    }
}
