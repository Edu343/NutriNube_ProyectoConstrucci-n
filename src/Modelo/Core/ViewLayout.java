package Core;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public abstract class ViewLayout {
    protected JPanel panel; // Usamos JPanel para que las vistas puedan ser añadidas al MainViewLayout
    private String tag;

    public ViewLayout(String tag) {
        this.setTag(tag);
        this.panel = new JPanel();
        this.panel.setLayout(new BorderLayout());
    }

    public void initialize() {
        createUI();
    }

    protected abstract void createUI();

    public JPanel getPanel() {
        return panel;
    }
    
    
    public void closeWindow() {
    }

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}