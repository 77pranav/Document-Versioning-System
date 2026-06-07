package services;
import ui_main.FileNameRibbon;
import ui_main.TextSection;
import ui_main.VersionSection;

import java.awt.*;

public class NewService {
    public void getNewPage(TextSection textSection, FileNameRibbon fileNameRibbon, VersionSection versionSection){
        textSection.getTextArea().setText("");
        fileNameRibbon.setFileName("untitled");
        versionSection.removeAll();
        versionSection.revalidate();
        versionSection.repaint();
        versionSection.add(versionSection.showHeading(), BorderLayout.NORTH);
        versionSection.add(versionSection.giveScrollPane(),BorderLayout.CENTER);
        versionSection.giveVersionListPanel().removeAll();
        versionSection.giveVersionListPanel().revalidate();
        versionSection.giveVersionListPanel().repaint();
    }
}
